package com.softinklab.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "authserver")
public class AuthServerConfig {

    private Registration registration;
    private Login login;

    @Data
    public static class Registration {
        private Boolean enable;
        private Boolean verification;
    }

    @Data
    public static class Login {
        private Boolean verification;
        private Boolean twoFactorEnable;
    }
}
