package com.mrprez.roborally.dao;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailResourceImpl implements MailResource {
	private static final String DEFAULT_FROM_ADDRESS = "mrprez@no-reply.fr";
	private Session senderSession;

	
	public MailResourceImpl(Object senderSession) {
		super();
		this.senderSession = (Session)senderSession;
	}
	
	@Override
	public void send(String toAdress, String subject, String text) throws AddressException, Exception{
		send(new InternetAddress(toAdress), new InternetAddress(DEFAULT_FROM_ADDRESS), subject, text);
	}
	
	
	private void send(InternetAddress toAdress, InternetAddress fromAdress, String subject, String text) throws Exception{
		Transport transport = senderSession.getTransport("smtp");
		try{
			MimeMessage message = new MimeMessage(senderSession);
			message.setFrom(fromAdress);
			message.setRecipient(Message.RecipientType.TO, toAdress);
			message.setSubject(subject);
			message.setText(text);
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
		}finally{
			transport.close();
		}
	}
}
