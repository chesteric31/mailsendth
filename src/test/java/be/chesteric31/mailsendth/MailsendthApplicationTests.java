package be.chesteric31.mailsendth;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.mail.MessagingException;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;


@SpringBootTest
class MailsendthApplicationTests {

	@Test
	void contextLoads() {
	}

    @Autowired
    private EmailSenderService emailSenderService;

    @Test
    public void sendHtmlMessageTest() throws MessagingException {
        Email email = new Email();
        email.to = "test@test.com";
        email.from = "eric@test.com";
        email.subject = "Welcome Email from Chesteric31";
        email.template = "welcome-email.html";
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Billy");
        properties.put("subscriptionDate", LocalDate.now().toString());
        properties.put("technologies", Arrays.asList("Cobol", "C", "Java"));
		//properties.put("logo", "templates/images/Tux.png");
        email.properties = properties;

        Assertions.assertDoesNotThrow(() -> emailSenderService.sendHtmlMessage(email));
    }

}
