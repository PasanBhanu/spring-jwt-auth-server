package com.softinklab.authentication.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softinklab.authentication.config.TokenConfig;
import com.softinklab.authentication.model.UserPrincipal;
import com.softinklab.authentication.service.TokenProvider;
import com.softinklab.rest.action.Authentication;
import com.softinklab.rest.response.ErrorResponse;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final TokenConfig tokenConfig;

    public JwtAuthenticationFilter(TokenProvider tokenProvider, TokenConfig tokenConfig) {
        this.tokenProvider = tokenProvider;
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        // No token found. Continue to next filter
        if (header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token
        String token = header.replace("Bearer", "").trim();

        try {
            // Validate token signature
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.tokenConfig.getAuthKey().getBytes()).parseClaimsJws(token);

            // Extract data
            Header headers = Jwts.parser().setSigningKey(this.tokenConfig.getAuthKey().getBytes()).parseClaimsJws(token).getHeader();
            UserPrincipal user = this.tokenProvider.getUserPrincipalFromClaims(claims);

            // Authenticate
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user, headers, user.getPermissions().
                    stream().
                    map(SimpleGrantedAuthority::new).
                    collect(Collectors.toList()));

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ExpiredJwtException ex) {
            setExpiredResponse(response);
            SecurityContextHolder.clearContext();
            return;
        } catch (JwtException ex) {
            setUnauthenticatedResponse(response);
            SecurityContextHolder.clearContext();
            return;
        } catch (Exception ex) {
            log.error("Token filter failed. {}", ex.getMessage());
            setUnauthenticatedResponse(response);
            SecurityContextHolder.clearContext();
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setExpiredResponse(HttpServletResponse response) throws IOException {
        ErrorResponse responseDefault = new ErrorResponse(440, "Your session is expired", Collections.singletonList("Your session is expired. Please login."), Authentication.SESSION_EXPIRED.label());
        response.setStatus(440);
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(responseDefault));
        response.getWriter().flush();
    }

    private void setUnauthenticatedResponse(HttpServletResponse response) throws IOException {
        ErrorResponse responseDefault = new ErrorResponse(401, "JWT token parsing failed", Collections.singletonList("Authentication failed. Please login."), Authentication.AUTHENTICATION_FAILED.label());
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(responseDefault));
        response.getWriter().flush();
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
