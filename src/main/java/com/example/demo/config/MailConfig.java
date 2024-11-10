package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSenderImpl getJavaMailSender() { // Thay đổi kiểu trả về thành JavaMailSenderImpl
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("tkhoa243@gmail.com");
        mailSender.setPassword("vxmolgkxsaekdfjr"); // App password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Đảm bảo dòng này có mặt
        props.put("mail.debug", "true");

        return mailSender;
    }
}
