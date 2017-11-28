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

public class MailSender_winpac {
	
	public static void main(String[] args) {
		
		/*String mailServerName = "193.169.13.30";
		String mailServerPort = "25";
		String id = "smartworks";
		String pass = "IlrXnscg";
		String subject = "Test Mail For Smartworks2";
		String to = "shyoun@winpac.co.kr";
		String from = "smartworks";
		String message = "스마트웍스닷넷에서 발송하는 test mail 입니다.";*/
		//String mailServerName = "smtp.worksmobile.com";
		
		String mailServerName = "193.169.13.30";
		String mailServerPort = "25";
		String id = "smartworks@winpac.co.kr";
		String pass = "";
		String subject = "test mail for smartworks";
		String to = "yukwangmin@naver.com";
		String from = "smartworks@winpac.co.kr";
		String message = "스마트웍스닷넷에서 발송하는 test mail 입니다.";
		
		/*if (args != null && args.length != 0) {

			mailServerName = (String)args[0];
			mailServerPort = (String)args[1];
			id = (String)args[2];
			pass = (String)args[3];
			subject = (String)args[4];
			to = (String)args[5];
			from = (String)args[6];
			message = (String)args[7];
		} else {
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Enter mailServerName :");
			mailServerName = sc.nextLine();
			System.out.print("Enter mailServerPort :");
			mailServerPort = sc.nextLine();
			System.out.print("Enter id :");
			id = sc.nextLine();
			System.out.print("Enter pass :");
			pass = sc.nextLine();
			System.out.print("Enter subject :");
			subject = sc.nextLine();
			System.out.print("Enter to :");
			to = sc.nextLine();
			System.out.print("Enter from :");
			from = sc.nextLine();
			System.out.print("Enter message :");
			message = sc.nextLine();
		}*/
		
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
class PassAuthenticator_winpac extends Authenticator {
	private String id;
	private String pass;
	public PassAuthenticator_winpac(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}
	public javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication(this.id, this.pass);
	}
}

