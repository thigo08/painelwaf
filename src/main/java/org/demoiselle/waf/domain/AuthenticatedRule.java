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
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DefaultAction;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.demoiselle.waf.internal.InterceptingHTTPServletResponse;

/**
 * This is the Rule subclass executed for &lt;authentication-rules&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */
@Entity
public class AuthenticatedRule extends Rule {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionAttribute;
	
	@OneToOne
	@JoinColumn(name = "ID_RULE") 
	private UrlPath path;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "fk_id_rule")
	private List<UrlPath> exceptions;
	
	public AuthenticatedRule(){
		path = new UrlPath();
		
	}

	public AuthenticatedRule(String id, String sessionAttribute, String path, List<UrlPath> exceptions) {
		this.setSessionAttribute(sessionAttribute);
		this.setPath(new UrlPath(path, true));
		this.setExceptions(exceptions);
		//setId(id);
	}

	public Action check(HttpServletRequest request,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {

		HttpSession session = request.getSession();
		String uri = request.getRequestURI();

		Pattern patternPath = Pattern.compile(getPath().getUrl());
		if ( getPath() != null && ! patternPath.matcher(uri).matches() ) {
			return new DoNothingAction();
		}

		if ( session != null && session.getAttribute(getSessionAttribute()) != null ) {

			return new DoNothingAction();

		} else { /* check if it's one of the exceptions */
			
			for (UrlPath path : getExceptions()) {			
				
				if (path.matches(uri))
					return new DoNothingAction();
			}
		}

		//log(request, "User requested unauthenticated access to URI '" + request.getRequestURI() + "' [querystring="+request.getQueryString()+"]");

		return new DefaultAction();
	}

	public String getSessionAttribute() {
		return sessionAttribute;
	}

	public void setSessionAttribute(String sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
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
	
//	public void addException(UrlPath urlPath){
//		urlPath.setRule(this);
//		this.getExceptions().add(urlPath);
//	}

	public void setExceptions(List<UrlPath> exceptions) {
		this.exceptions = exceptions;
	}
}