package antidimon.web.tasktrackeremails.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("antidimon1930@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        log.debug("Created message to sent to {}", to);

        mailSender.send(message);
    }
}
