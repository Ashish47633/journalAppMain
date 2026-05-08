package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

   @Test
   public void testSendEmail(){
        emailService.sendEmail("ashishvishwakarma9283@gmail.com",
                "Test Java MailSender",
                "This is a test email sent from JavaMailSender in Spring Boot.");
    }
}
