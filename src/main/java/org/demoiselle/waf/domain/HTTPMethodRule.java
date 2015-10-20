/**
 * OWASP Enterprise Security API (ESAPI)
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * Enterprise Security API (ESAPI) project. For details, please see
 * <a href="http://www.owasp.org/index.php/ESAPI">http://www.owasp.org/index.php/ESAPI</a>.
 *
 * Copyright (c) 2009 - The OWASP Foundation
 * 
 * The ESAPI is published by OWASP under the BSD license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Arshan Dabirsiaghi <a href="http://www.aspectsecurity.com">Aspect Security</a>
 * @created 2009
 */
package org.demoiselle.waf.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.owasp.esapi.waf.internal.InterceptingHTTPServletResponse;


/**
 * This is the Rule subclass executed for &lt;restrict-method&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */
@Entity
public class HTTPMethodRule extends Rule {
	
	private static final long serialVersionUID = 1L;

	@OneToOne
	private UrlPath allowedMethods;
	
	@OneToOne
	private UrlPath deniedMethods;
	
	@OneToOne
	private UrlPath path;
	
	public HTTPMethodRule (){
		allowedMethods = new UrlPath();
		deniedMethods = new UrlPath();
		path = new UrlPath();
	}

	public HTTPMethodRule(String allowedMethods, String deniedMethods, String path) {
		this.setAllowedMethods(new UrlPath(allowedMethods, true));
		this.setDeniedMethods(new UrlPath(deniedMethods, true));
		this.setPath(new UrlPath(path, true));
	}

	public Action check(HttpServletRequest request,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {

		/*
		 * If no path is specified, apply rule globally.
		 */
		String uri = request.getRequestURI();
		String method = request.getMethod();
//
//		if ( path == null || path.matcher(uri).matches() ) {
//			/*
//			 *	Order allow, deny.
//			 */
//
//			if ( allowedMethods != null && allowedMethods.matcher(method).matches() ) {
//				return new DoNothingAction();
//			} else if ( allowedMethods != null ) {
//				log(request,"Disallowed HTTP method '" + request.getMethod() + "' found for URL: " + request.getRequestURL());
//				return new DefaultAction();
//			}
//
//			if ( deniedMethods != null && deniedMethods.matcher(method).matches() ) {
//				log(request,"Disallowed HTTP method '" + request.getMethod() + "' found for URL: " + request.getRequestURL());
//				return new DefaultAction();
//			}
//
//		}

		return new DoNothingAction();
	}

	public UrlPath getAllowedMethods() {
		return allowedMethods;
	}

	public void setAllowedMethods(UrlPath allowedMethods) {
		this.allowedMethods = allowedMethods;
	}

	public UrlPath getDeniedMethods() {
		return deniedMethods;
	}

	public void setDeniedMethods(UrlPath deniedMethods) {
		this.deniedMethods = deniedMethods;
	}

	public UrlPath getPath() {
		return path;
	}

	public void setPath(UrlPath path) {
		this.path = path;
	}

}
