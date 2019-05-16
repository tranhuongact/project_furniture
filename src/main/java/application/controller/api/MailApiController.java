package application.controller.api;

import application.data.model.Mail;
import application.data.service.MailService;
import application.model.api.BaseApiResult;
import application.model.dto.MailDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/mail")
public class MailApiController {

    private static final Logger logger = LogManager.getLogger(MailApiController.class);

    @Autowired
    private MailService mailService;

    @PostMapping("/create")
    public BaseApiResult createMail(@RequestBody MailDTO dto){
        BaseApiResult result = new BaseApiResult();

        try {
            Mail mailEntity = new Mail();
            mailEntity.setTo(dto.getTo());
            mailEntity.setSubject(dto.getSubject());
            mailEntity.setContent(dto.getContent());
            mailEntity.setCreatedDate(new Date());

            mailService.addNewMail(mailEntity);
            result.setSuccess(true);
            result.setMessage("Create mail successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return result;
    }
}
