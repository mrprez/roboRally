package com.mrprez.roborally.server;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AbstractGwtService extends RemoteServiceServlet {
	private static final long serialVersionUID = 1L;
	
	
	public AbstractGwtService(){
		super();
		ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
		applicationContext.getAutowireCapableBeanFactory()
				.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
	}
	

}
