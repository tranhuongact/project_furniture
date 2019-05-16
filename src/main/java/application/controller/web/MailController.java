package application.controller.web;

import application.data.model.Mail;
import application.data.service.MailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
public class MailController {

    private static final Logger logger = LogManager.getLogger(MailController.class);

    @Autowired
    private MailService mailService;

//    @RequestMapping("/writeMail")
//    public String writeMail (Model model) throws MessagingException {
//        Mail mail = new Mail();
//        model.addAttribute("mail", mail);
//        return "/writeEmail";
//    }

    @PostMapping(value = "/sendMail")
    public String sendEmail(Model model,
                            @Valid @ModelAttribute("mail")Mail recipientEmail){
        try {
            String emailAddress = recipientEmail.getTo();
            mailService.sendEmail(emailAddress);

        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "redirect:/";
    }

}
