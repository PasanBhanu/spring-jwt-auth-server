package com.softinklab.notification.service;

import com.softinklab.notification.config.EmailConfig;
import com.softinklab.notification.model.Email;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender javaMailSender;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final EmailConfig emailConfig;

    public NotificationServiceImpl(JavaMailSender javaMailSender, FreeMarkerConfigurer freeMarkerConfigurer, EmailConfig emailConfig) {
        this.javaMailSender = javaMailSender;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
        this.emailConfig = emailConfig;
    }

    public void sendEmail(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setFrom(email.getFrom(), email.getFromName());
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setReplyTo(email.getReplyTo(), email.getReplyToName());
            mimeMessageHelper.setText(geContentFromTemplate(email.getModel()), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public EmailConfig getEmailConfig() {
        return this.emailConfig;
    }

    private String geContentFromTemplate(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(this.freeMarkerConfigurer.getConfiguration().getTemplate("default.ftlh"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
