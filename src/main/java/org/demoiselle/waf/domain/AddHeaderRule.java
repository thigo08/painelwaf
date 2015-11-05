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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.demoiselle.waf.internal.InterceptingHTTPServletResponse;


/**
 * This is the Rule subclass executed for &lt;add-header&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */
@Entity
public class AddHeaderRule extends Rule {

	private static final long serialVersionUID = 1L;
	
	private String header;
	
	private String value;
	
	@OneToOne
	//@JoinColumn(name = "fk_id_rule") 
	private UrlPath path;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	//@JoinColumn(name = "fk_id_rule")
	private List<UrlPath> exceptions;
			
	public AddHeaderRule(){
		path = new UrlPath();
	}

	public AddHeaderRule(String id, String header, String value, String path, List<UrlPath> exceptions) {
		//setId(id);
		this.setHeader(header);
		this.setValue(value);
		this.setPath(new UrlPath(path, true));
		this.setExceptions(exceptions);
	}

	public Action check(
			HttpServletRequest request, 
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {

		DoNothingAction action = new DoNothingAction();
		Pattern patternPath = Pattern.compile(getPath().getUrl());

		if ( patternPath.matcher(request.getRequestURI()).matches() ) {

			for(int i=0;i<exceptions.size();i++) {

				Object o = exceptions.get(i);

				if ( o instanceof String ) {
					if ( request.getRequestURI().equals((String)o)) {
						action.setFailed(false);
						action.setActionNecessary(false);
						return action;
					}
				} else if ( o instanceof Pattern ) {
					if ( ((Pattern)o).matcher(request.getRequestURI()).matches() ) {
						action.setFailed(false);
						action.setActionNecessary(false);
						return action;					
					}
				}

			}


			action.setFailed(true);
			action.setActionNecessary(false);

			if ( response != null ) {
				response.setHeader(header, value);
			} else {
				httpResponse.setHeader(header, value);
			}

		}

		return action;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

}
