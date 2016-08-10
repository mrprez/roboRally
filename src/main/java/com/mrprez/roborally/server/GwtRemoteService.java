package com.mrprez.roborally.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GwtRemoteService {

	void init(ServletConfig servletConfig) throws ServletException;

	void doPost(HttpServletRequest request, HttpServletResponse response);

}
