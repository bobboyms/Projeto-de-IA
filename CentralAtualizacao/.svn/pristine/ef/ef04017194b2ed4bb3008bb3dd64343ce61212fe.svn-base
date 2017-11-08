package br.com.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Persistence;

public class Main {

	public static void main(String... args) {

		System.out.println("XXXXXXXXXX");
		
		Persistence.createEntityManagerFactory("AlgaWorksPU");
		
		System.out.println("RRRRRRR");
		
		if (true) return;
		
		File file = new File("D:/backup_windows10/gravadoras.txt");
	   
		System.out.println(file.exists());
		
		List<String> emails = new ArrayList();
		
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				System.out.println(line);
				
				emails.add(line.trim());
				
			}
			br.close();
			fr.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
//		if (true) {
//			return;
//		}
//		
		
		Properties props = new Properties();
		/** ParÃ¢metros de conexÃ£o com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("bobboyms@gmail.com", "bob250387");
					}
				});

		/** Ativa Debug para sessÃ£o */
		session.setDebug(true);

		try {

			for (String email : emails) {
			
				Message message = new MimeMessage(session);
				
				message.setFrom(new InternetAddress("bobboyms@gmail.com")); // Remetente
	
				Address[] toUser = InternetAddress // DestinatÃ¡rio(s)
						.parse(email);
				
				message.setRecipients(Message.RecipientType.TO, toUser);
				message.setSubject("new track for evaluation");// Assunto
	//			message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
				
				message.setContent("Hello! I've been through that email leaves my new track. </br>"
						+ "My name is Thiago Rodrigues, have a electronic music project called DillRox ."
						+ " I live in Brazil. I follow the work of the label for some time , it will be a great pleasure working together."
						+ "</br></br> private link sound cloud </br> "
						+ "<a href='https://soundcloud.com/dillrox/dillrox-the-one-price-original-mix/s-MRCdC'>"
						+ "https://soundcloud.com/dillrox/dillrox-the-one-price-original-mix/s-MRCdC</a>".toString(), "text/html");
	
				/** MÃ©todo para enviar a mensagem criada */
				Transport.send(message);
	
				System.out.println("Enviado para " + email);
				
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		System.out.println("XXXXXXXXXXXXXXX --- FIM --- XXXXXXXXXXXXXXXX");
	}

	
}
