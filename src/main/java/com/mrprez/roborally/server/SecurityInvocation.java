package com.mrprez.roborally.server;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;

public class SecurityInvocation {
	
	private Set<String> allowedMethods;
	
	public Object afficherTrace(ProceedingJoinPoint joinpoint) throws Throwable{
		System.out.println("Toto: "+joinpoint.getSignature().getName());
		
		
		return joinpoint.proceed();
	}

	public Set<String> getAllowedMethods() {
		return allowedMethods;
	}

	public void setAllowedMethods(Set<String> allowedMethods) {
		this.allowedMethods = allowedMethods;
	}

}
