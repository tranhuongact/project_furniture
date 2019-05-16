package application.data.model;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "dbo_mail")
public class Mail {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mail_id")
    @Id
    private int id;

//    @Column (name = "from")
//    private String from;

    @Column (name = "to")
    private String to;

    @Column (name = "subject")
    private String subject;

    @Column (name = "content")
    private String content;

    @Column (name = "created_date")
    private Date createdDate;

    public Mail(){
    }

    public Mail(String to, String subject, String content, Date createdDate) {
//        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getFrom() {
//        return from;
//    }
//
//    public void setFrom(String from) {
//        this.from = from;
//    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
//                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
