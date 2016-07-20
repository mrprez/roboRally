package com.mrprez.roborally.dao;

import javax.mail.internet.AddressException;

public interface MailResource {

	void send(String toAdress, String subject, String text) throws AddressException, Exception;

}
