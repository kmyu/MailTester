/*	
 * file 		 : MailSender.java
 * created by    : Yu Kwangmin Min
 * creation-date : 2017. 6. 26.
 */

package net.smartworks.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class MailSender_smartworks {
	
	/*public static void main(String[] args) {
		mailSend("kmyu@smartworks.net","hello km");
	}*/
	
	public static void mailSend(String to, String message) {
		
		String mailServerName = "smtp.worksmobile.com";
		String mailServerPort = "465";
		String id = "info@smartworks.net";
		String pass = "";
		String subject = "test mail for smartworks";
		String from = "info@smartworks.co.kr";
		
		try {
			sendMail(mailServerName, mailServerPort, id, pass, subject, to, from, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void sendMail(String mailServerName, String mailServerPort, String id, String pass, String subject, String to, 
			String from, String messageText) throws AddressException, MessagingException, UnsupportedEncodingException {

		if(mailServerName == null || id == null || pass == null) 
			return;
		
		Authenticator auth = new PassAuthenticator(id, pass);
		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.host", mailServerName);
		mailProps.put("mail.smtp.auth", "true");
		if (mailServerPort == null) {
			mailProps.put("mail.smtp.port", "465");
		} else {
			mailProps.put("mail.smtp.port", mailServerPort);
		}
		
		mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		Session mailSession = Session.getInstance(mailProps, auth);

		InternetAddress toAddrs = new InternetAddress(to);
		InternetAddress fromAddr = new InternetAddress(id, "SmartWorks.net, Inc.");

		Message message = new MimeMessage(mailSession);
		message.setFrom(fromAddr);
		message.setRecipient(Message.RecipientType.TO, toAddrs);
		message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
		message.setContent(messageText.toString(), "text/html; charset=utf-8");
		
		try {
			Transport.send(message);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new MessagingException(ex.getMessage());
		}
	}
}
class PassAuthenticator extends Authenticator {
	private String id;
	private String pass;
	public PassAuthenticator(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}
	public javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication(this.id, this.pass);
	}
}

