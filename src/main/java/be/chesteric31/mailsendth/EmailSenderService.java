package be.chesteric31.mailsendth;

import java.nio.charset.StandardCharsets;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    private final static Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailSenderService(  final JavaMailSender emailSender,
                                final SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendHtmlMessage(Email email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        Context context = new Context();
        context.setVariables(email.properties);
        helper.setFrom(email.from);
        helper.setTo(email.to);
        helper.setSubject(email.subject);
        
        String html = templateEngine.process(email.template, context);
        helper.setText(html, true);
        //WARNING addInline after setText else no image shown!
        helper.addInline("logo", new ClassPathResource("templates/images/Tux.png"), "image/png");
        logger.info("Sending email: {} with html body: {}", email, html);
        emailSender.send(message);
    }
}
