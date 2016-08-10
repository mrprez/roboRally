package com.mrprez.roborally.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class GwtServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		int startIndex = url.lastIndexOf("/")+1;
		int endIndex = url.indexOf("?") >= 0 ? url.indexOf("?") : url.length();
		String beanRef = url.substring(startIndex, endIndex);
		LoggerFactory.getLogger(getClass().getSimpleName()).trace("URL requested "+url+" => GWT Service: "+beanRef);
				
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		GwtRemoteService remoteService = (GwtRemoteService) applicationContext.getBean(beanRef);
		remoteService.init(this);
		remoteService.doPost(request, response);
	}
	
	
	

	

}
