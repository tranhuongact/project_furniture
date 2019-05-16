package application.data.repository;

import application.data.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository <Mail, Integer> {
}
