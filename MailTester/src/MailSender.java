/*	
 * file 		 : MailSender.java
 * created by    : Yu Kwangmin Min
 * creation-date : 2017. 6. 26.
 */



import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

//웹서비스 axis jws 파일로 변환하여 사용 

public class MailSender extends Authenticator {
	
	/*public static void main(String[] args) {
		mailSend("kmyu@smartworks.net","hello km");
	}*/

	public javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication("info@smartworks.net", "20131217swdn");
	}
	
	public String mailSend(String to, String subject, String message) {
		
		String mailServerName = "smtp.worksmobile.com";
		String mailServerPort = "465";
		if (subject == null) {
			subject = "Smartworks.net Information Email.";
		}
		String from = "info@smartworks.co.kr";
		
		try {
			sendMail(mailServerName, mailServerPort, subject, to, from, message);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
	private void sendMail(String mailServerName, String mailServerPort, String subject, String to, 
			String from, String messageText) throws AddressException, MessagingException, UnsupportedEncodingException {

		Message message = null;
		try {
			
			Properties mailProps = new Properties();
			mailProps.put("mail.smtp.host", mailServerName);
			mailProps.put("mail.smtp.auth", "true");
			if (mailServerPort == null) {
				mailProps.put("mail.smtp.port", "465");
			} else {
				mailProps.put("mail.smtp.port", mailServerPort);
			}
			
			mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			
			Session mailSession = Session.getInstance(mailProps, this);
			
			InternetAddress toAddrs = new InternetAddress(to);
			InternetAddress fromAddr = new InternetAddress("info@smartworks.net", "SmartWorks.net, Inc.");
			
			message = new MimeMessage(mailSession);
			message.setFrom(fromAddr);
			message.setRecipient(Message.RecipientType.TO, toAddrs);
			message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
			message.setContent(messageText.toString(), "text/html; charset=utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("sendMail webservice : before sending");
			Transport.send(message);
			System.out.println("sendMail webservice : end");
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new MessagingException(ex.getMessage());
		}
	}
}

