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
 * This is the Rule subclass executed for &lt;add-secure-flag&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */

@Entity
public class AddSecureFlagRule extends Rule {
	
	private static final long serialVersionUID = 1L;

	@OneToOne
	//@JoinColumn(name = "fk_id_rule") 
	private UrlPath path;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	//@JoinColumn(name = "fk_id_rule")
	private List<UrlPath> name;
	
	public AddSecureFlagRule(){
		path = new UrlPath();
	}

	public AddSecureFlagRule(String id, List<UrlPath> name) {
		this.setName(name);
		//setId(id);
	}

	public Action check(HttpServletRequest request,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {
		
		DoNothingAction action = new DoNothingAction();

		return action;
	}

	public boolean doesCookieMatch(String cookieName) {

		for(int i=0;i<name.size();i++) {
			Pattern patternPath = Pattern.compile(getPath().getUrl());
			if ( patternPath.matcher(cookieName).matches() ) {
				return true;
			}
		}

		return false;
	}
	
	public UrlPath getPath() {
		return path;
	}

	public void setPath(UrlPath path) {
		this.path = path;
	}

	public List<UrlPath> getName() {
		if (name == null)
			name = new ArrayList<UrlPath>();
		return name;
	}

	public void setName(List<UrlPath> name) {
		this.name = name;
	}

}
