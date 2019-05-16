package application.data.service;

import application.data.model.Mail;
import application.data.repository.MailRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class MailService {

    private static final Logger logger = LogManager.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private MailRepository mailRepository;

//    public void sendMail(String to, String subject, String content){
//        try {
//            Locale locale = LocaleContextHolder.getLocale();
//
//            // Prepare the evaluation context
//            Context context = new Context(locale);
//            context.setVariable("content",content);
//
//            // Prepare message using a Spring helper
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
//                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                    StandardCharsets.UTF_8.name());
//            mimeMessageHelper.setTo("huongttd18@gmail.com");
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setFrom("diemhuong10897@gmail.com");
//
//            // Create the HTML body using Thymeleaf
//            String htmlContent = templateEngine.process("sendMail", context);
//            mimeMessageHelper.setText(htmlContent, true);
//
//            // Send email
//            mailSender.send(mimeMessage);
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }

    public void addNewMail(Mail mail) {
        try {
            mailRepository.save(mail);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendEmail(String emailAddress){
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("diemhuong10897@gmail.com");
            helper.setTo(emailAddress);
            helper.setSubject("Hi");
            helper.setText("How are you?");
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

