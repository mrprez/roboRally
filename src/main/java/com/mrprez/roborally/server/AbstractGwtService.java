package com.mrprez.roborally.server;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mrprez.roborally.shared.AuthenticationException;
import com.mrprez.roborally.shared.UserGwt;

public class AbstractGwtService extends RemoteServiceServlet {
	private static final long serialVersionUID = 1L;
	

	@Override
	public String processCall(RPCRequest rpcRequest) throws SerializationException {
		if(securityCheck(rpcRequest)){
			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
			applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this, Autowire.BY_TYPE.value(), true);
			return super.processCall(rpcRequest);
		}else{
			rpcRequest.getSerializationPolicy().validateSerialize(AuthenticationException.class);
			return RPC.encodeResponseForFailedRequest(rpcRequest, new AuthenticationException());
		}
	}
	
	
	private boolean securityCheck(RPCRequest rpcRequest){
		if(rpcRequest.getMethod().getDeclaringClass().getAnnotation(PermitAll.class)!=null){
			return true;
		}
		if(rpcRequest.getMethod().getAnnotation(PermitAll.class)!=null){
			return true;
		}
		RolesAllowed rolesAllowed = rpcRequest.getMethod().getAnnotation(RolesAllowed.class);
		if(rolesAllowed==null){
			rolesAllowed = rpcRequest.getMethod().getDeclaringClass().getAnnotation(RolesAllowed.class);
		}
		if(rolesAllowed==null){
			return false;
		}
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		if(user==null){
			return false;
		}
		for(String allowedRole : rolesAllowed.value()){
			if(user.getRoles().contains(allowedRole)){
				return true;
			}
		}
		return false;
	}
	
	

}
