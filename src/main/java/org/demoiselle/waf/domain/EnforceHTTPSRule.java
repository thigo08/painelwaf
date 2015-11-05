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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DefaultAction;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.owasp.esapi.waf.actions.RedirectAction;
import org.demoiselle.waf.internal.InterceptingHTTPServletResponse;

/**
 * This is the Rule subclass executed for &lt;enforce-https&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */

@Entity
public class EnforceHTTPSRule extends Rule {
	
	private static final long serialVersionUID = 1L;

	@OneToOne
	private UrlPath path;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private List<UrlPath> exceptions;
	
	private String action;

	/*
	 * action = [ redirect | block ] [=default (redirect will redirect to error page]
	 */
	
	public EnforceHTTPSRule(){
		path = new UrlPath();
	}

	public EnforceHTTPSRule(String id, String path, List<UrlPath> exceptions, String action) {
		this.setPath(new UrlPath(path, true));
		this.setExceptions(exceptions);
		this.setAction(action);
		//setId(id);
	}

	public Action check(HttpServletRequest request,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {

		if ( ! request.isSecure() ) {
			
			Pattern patternPath = Pattern.compile(getPath().getUrl());

			if ( patternPath.matcher(request.getRequestURI()).matches() ) {

				Iterator<UrlPath> it = exceptions.iterator();

				while(it.hasNext()){

					Object o = it.next();

					if ( o instanceof String ) {
						if ( ((String)o).equalsIgnoreCase(request.getRequestURI()) ) {
							return new DoNothingAction();
						}
					} else if ( o instanceof Pattern ) {
						if ( ((Pattern)o).matcher(request.getRequestURI()).matches() ) {
							return new DoNothingAction();
						}
					}

				}

				log(request,"Insecure request to resource detected in URL: '" + request.getRequestURL() + "'");

				if ( "redirect".equals(action) ) {
					RedirectAction ra = new RedirectAction();
					ra.setRedirectURL(request.getRequestURL().toString().replaceFirst("http", "https"));
					return ra;
				}

				return new DefaultAction();

			}
		}

		return new DoNothingAction();

	}

	public UrlPath getPath() {
		return path;
	}

	public void setPath(UrlPath path) {
		this.path = path;
	}

	public List<UrlPath> getExceptions() {
		if (exceptions == null)
			exceptions = new ArrayList<UrlPath>();
		return exceptions;
	}

	public void setExceptions(List<UrlPath> exceptions) {
		this.exceptions = exceptions;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
