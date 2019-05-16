package application.data.service;

import application.data.model.Contact;
import application.data.repository.ContactRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private static final Logger logger = LogManager.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public void addNewListContacts(List<Contact> contactList) {
        try {
            contactRepository.save(contactList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void addNewContact(Contact contact) {
        try {
            contactRepository.save(contact);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Contact findOne(int contactId) {
        return contactRepository.findOne(contactId);
    }

    public boolean updateContact(Contact contact) {
        try {
            contactRepository.save(contact);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<Contact> getListAllContacts() {
        try {
            return contactRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Contact> getListContactByNameContaining(String name) {
        return contactRepository.getListContactByNameContaining(name);
    }

}
