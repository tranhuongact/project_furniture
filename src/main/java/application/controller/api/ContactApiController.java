package application.controller.api;

import application.data.model.Contact;
import application.data.service.ContactService;
import application.model.api.DataApiResult;
import application.model.dto.ContactDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/contact")
public class ContactApiController {

    private static final Logger logger = LogManager.getLogger(ContactApiController.class);

    @Autowired
    private ContactService contactService;

    @GetMapping("/detail/{contactId}")
    public DataApiResult getDetailContact(@PathVariable int contactId) {
        DataApiResult result = new DataApiResult();

        try {
            Contact contact = contactService.findOne(contactId);
            if (contact == null) {
                result.setSuccess(false);
                result.setMessage("Can't find this contact");
            } else {
                ContactDTO dto = new ContactDTO();
                dto.setId(contact.getId());
                dto.setFullName(contact.getFullName());
                dto.setEmail(contact.getEmail());
                dto.setPhoneNumber(contact.getPhoneNumber());
                dto.setMessage(contact.getMessage());
                dto.setCreatedDate(contact.getCreatedDate());

                result.setSuccess(true);
                result.setMessage("Get detail contact successfully!");
                result.setData(dto);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return result;
    }
}
