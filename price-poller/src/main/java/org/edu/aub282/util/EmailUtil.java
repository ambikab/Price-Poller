/**
 * 
 */
package org.edu.aub282.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * @author ambika_b
 *
 */
public class EmailUtil {


	public static boolean sendEmail(String userName, String subject, String contents) {
		final String from;
		final String pass;
		try {
			Properties prop = new Properties();
			InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("/credentials.properties");
			prop.load(inputStream);
			from = prop.getProperty("mailId");
			pass = prop.getProperty("password");
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			Session session = Session.getInstance(props, new javax.mail.Authenticator() { 
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(from, pass); 
								} 
							});
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userName));
			message.setSubject(subject);
			message.setContent(contents, "text/html" );
			Transport.send(message);
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println(fileNotFoundException.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
		return false;
	}

}
