package application.controller.web;


import application.data.model.Contact;
import application.data.model.User;
import application.data.service.ContactService;
import application.data.service.UserService;
import application.model.dto.ContactDTO;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.contact.ContactDetailVM;
import application.model.viewmodel.contact.ContactVM;
import application.model.viewmodel.home.HomeLandingVM;
import application.model.viewmodel.user.UserVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@Controller
public class ContactController extends BaseController{

    private static final Logger logger = LogManager.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @GetMapping(value = "/contact")
    public String contactForm (Model model,
                               @Valid @ModelAttribute("productname") ProductVM productName,
                               HttpServletResponse response,
                               HttpServletRequest request,
                               final Principal principal){

        ContactDetailVM vm = new ContactDetailVM();

        vm.setLayoutHeaderVM(this.getLayoutHeaderVM(response, request, principal));
        model.addAttribute("vm",vm);
        model.addAttribute("contact", new Contact());

        return "/contact";
    }

    @PostMapping(value = "/contact")
    public String contact (Model model,
                           @Valid @ModelAttribute("productname") ProductVM productName,
                           @Valid Contact contact,
                           HttpServletResponse response,
                           HttpServletRequest request, final Principal principal){

        try {
            ContactDetailVM vm = new ContactDetailVM();

            contact.setCreatedDate(new Date());
            contactService.addNewContact(contact);

            vm.setLayoutHeaderVM(this.getLayoutHeaderVM(response, request, principal));
            model.addAttribute("vm", vm);

            return "redirect:/contact?sendSuccess";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "redirect:/contact?sendFail";
    }
}
