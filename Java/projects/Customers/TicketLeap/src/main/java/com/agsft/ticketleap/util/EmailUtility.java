package com.agsft.ticketleap.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.agsft.ticketleap.constants.Constants;;

/**
 * @author shekhar
 *
 */
@Component
@PropertySource("classpath:mail.properties")
public class EmailUtility {

	/** The mail from. */
	@Value("${mail.from}")
	private String mailFrom;

	@Value("${email.title}")
	private String emailTitle;

	/** The java mail sender. */
	@Autowired
	private final JavaMailSender javaMailSender;

	/**
	 * Instantiates a new user controller.
	 *
	 * @param javaMailSender
	 *            the java mail sender
	 */
	@Autowired
	EmailUtility(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * Send mail.
	 *
	 * @param emailAddress
	 *            the email address
	 * @param title
	 *            the title
	 * @param link
	 *            the link
	 * @param javaMailSender
	 *            the java mail sender
	 */
	@Async
	public void sendMail(String emailAddress, String token, String role) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

			helper.setTo(emailAddress);
			helper.setFrom(mailFrom);
			helper.setSubject(emailTitle);
			String link = Constants.webUrl+token+"/"+emailAddress;
			String emailBody = "<p>Please click on the link below to verify your TicketLeap App account </p> "
					+ "<a href='" + link + "'>Register</a>";

			mimeMessage.setContent(emailBody, "text/html");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaMailSender.send(mimeMessage);
	}

}
