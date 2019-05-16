package application.data.repository;

import application.data.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository <Contact, Integer> {

    @Query("SELECT c FROM dbo_contact c " +
            "WHERE (:name IS NULL OR UPPER(c.fullName) LIKE CONCAT('%',UPPER(:name),'%'))")
    List<Contact> getListContactByNameContaining(@Param("name") String name);

}
