package br.com.etico.arquitetura.formulario.util.criptografia;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.criterion.Restrictions;

import br.com.etico.modelo.beans.Chamado;
import br.com.etico.modelo.beans.ChaveDeAcessoExterno;
import br.com.etico.modelo.beans.Perfil;
import br.com.etico.modelo.beans.Usuario;
import br.com.etico.persistencia.dao.ChaveAcessoExternoDAO;
import br.com.etico.persistencia.jpa.JPAUtil;

public class ControlaChaveAcesso {

	public static String EMAIL_SUPORTE = "etico.suporte@gmail.com";
	public static String SENHA_EMAIL_SUPORTE = "$cpqd118";
	public static String URL = "http://127.0.0.1:8080/CentralAtualizacao";
	public static String CHAVE = "87422988225541121";

	public static void enviaEmailClienteSuporte(Usuario usuario, String chave, String idEnvio, Chamado chamado,
			String tipoMenssagem, List<String> listaEmails) {

		Properties props = new Properties();
		/** ParÃ¢metros de conexÃ£o com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_SUPORTE, SENHA_EMAIL_SUPORTE);
			}
		});

		/** Ativa Debug para sessÃ£o */
		session.setDebug(true);

		try {

			for (String email : listaEmails) {

				Message message = new MimeMessage(session);

				message.setFrom(new InternetAddress(EMAIL_SUPORTE)); // Remetente

				// DESTINATÁRIO
				Address[] toUser = InternetAddress.parse(email);

				message.setRecipients(Message.RecipientType.TO, toUser);
				message.setSubject("Mensagem de suporte da Ético Software".toUpperCase() + " ID : " + idEnvio);// Assunto
				// message.setText("Enviei este email utilizando JavaMail com
				// minha conta GMail!");

				String textoEmail = "";

				if (tipoMenssagem.equals(Perfil.strIndTipoSetor_CLIENTE)) {

					textoEmail = "<h2 style='font-family: Arial, Verdana; font-style: normal; font-variant: normal; line-height: normal;'>suporte p/ cliente NOVA MENSAGEM&nbsp;</h2><p class='MsoNormal' style='font-family: Arial, Verdana; font-style: normal;"
							+ " font-variant: normal; line-height: normal;'>REFERENTE SEU <span style='font-weight: bold;'>CHAMADO Nº "
							+ chamado.getId()
							+ "</span></p><p class='MsoNormal' style='font-family: Arial, Verdana; font-style: normal; font-variant: normal; font-weight: normal; line-height: normal; font-size: 10pt;'>"
							+ "<o:p></o:p></p><p class='MsoNormal' style='font-family: Arial, Verdana; font-style: normal; font-variant: normal; font-weight: normal; line-height: normal;'><span style='font-size: xx-large; color: rgb(255, 0, 0);'>"
							+ "<a href='" + URL + "/paginas/chamado.jsf?key=" + chave + "'>"
							+ "CLIQUE AQUI PARA RESPONDER</a></span></p><p class='MsoNormal'><span style='font-family: Arial, Verdana; font-size: small;'>não responda esse&nbsp;e-mail."
							+ " Qualquer duvida entrar em contato com o suporte por telefone ou email : suporte@eticosoftware.com.br</span></p>";

				} else {

					textoEmail = "<h2 style='font-family: Arial, Verdana; font-style: normal; font-variant: normal; line-height: normal;'>cliente p/suporte NOVA MENSAGEM&nbsp;</h2><p class='MsoNormal' style='font-family: Arial, Verdana; font-style: normal;"
							+ " font-variant: normal; line-height: normal;'>REFERENTE AO <span style='font-weight: bold;'>CHAMADO Nº "
							+ chamado.getId()
							+ "</span></p><p class='MsoNormal' style='font-family: Arial, Verdana; font-style: normal; font-variant: normal; font-weight: normal; line-height: normal; font-size: 10pt;'>"
							+ "<o:p></o:p></p><p class='MsoNormal' style='font-family: Arial, Verdana; font-style: normal; font-variant: normal; font-weight: normal; line-height: normal;'><span style='font-size: xx-large; color: rgb(255, 0, 0);'>"
							+ "<a href='" + URL + "/paginas/chamado.jsf?key=" + chave + "'>"
							+ "CLIQUE AQUI PARA RESPONDER</a></span></p><p class='MsoNormal'><span style='font-family: Arial, Verdana; font-size: small;'>não responda esse&nbsp;e-mail."
							+ " Qualquer duvida entrar em contato com o suporte por telefone ou email : suporte@eticosoftware.com.br</span></p>";

				}

				message.setContent(textoEmail, "text/html");

				/** método para enviar a mensagem criada */
				Transport.send(message);

				System.out.println("Enviado para " + email);
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public static String toHexString(byte[] ba) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < ba.length; i++)
			str.append(String.format("%x", ba[i]));
		return str.toString();
	}

	public static String fromHexString(String hex) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < hex.length(); i += 2) {
			str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
		}
		return str.toString();
	}

	public static String getEnderecoFisicoMAC() {
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			// System.out.println("Current IP address : " +
			// ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			if (network.getHardwareAddress() == null) {
				return CHAVE;
			}
			
			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}

			return sb.toString().replace("-", "");

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}

		return null;
	}

	public static synchronized void enviaChaveDeAcessoCliente(Usuario usuario, Chamado chamado, String tipoMenssagem,
			List<String> listaEmails) {

		EntityManager entityManager = null;
		EntityTransaction tx = null;

		try {

			entityManager = JPAUtil.getEntityManager();
			tx = entityManager.getTransaction();

			tx.begin();

			String chave = (usuario.getLogin() + "|" + usuario.getSenha() + "|" + chamado.getId() + "|"
					+ "5588224412" + "|" + (Math.random() * 125541));
			chave = toHexString(chave.getBytes());

			ChaveDeAcessoExterno acessoExterno = new ChaveDeAcessoExterno();
			acessoExterno.setChave(chave);
			entityManager.persist(acessoExterno);

			tx.commit();

			enviaEmailClienteSuporte(usuario, chave, acessoExterno.getId().toString(), chamado, tipoMenssagem,
					listaEmails);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			entityManager.close();
		}

	}

	public static synchronized String[] informaçoesDecriptografada(String chave) {

		try {

			System.out.println("NO METHODO : " + chave);

			return fromHexString(chave).split("\\|");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static boolean validaChaveDeAcesso(String chave) {

		EntityManager entityManager = null;
		EntityTransaction tx = null;

		try {

			ChaveAcessoExternoDAO dao = new ChaveAcessoExternoDAO();

			ChaveDeAcessoExterno acessoExterno = (ChaveDeAcessoExterno) JPAUtil.getSession()
					.createCriteria(ChaveDeAcessoExterno.class)
					.add(Restrictions.eq(ChaveDeAcessoExterno.strChave, chave)).uniqueResult();

			if (acessoExterno != null) {

				return true;
			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	public static void main(String[] args) {

		System.out.println(fromHexString("636c69656e74657c3132337c347c433833413335433443464144"));

		String[] splited = fromHexString("636c69656e74657c3132337c347c433833413335433443464144").split("\\|");

		for (String dado : splited) {
			System.out.println(dado);
		}

		//

		// StringBuilder cript = criptografa(texto);
		//
		// System.out.println(cript.toString());
		// System.out.println(decriptografa(cript));

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}