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
 * This is the Rule subclass executed for &lt;restrict-extension&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */
@Entity
public class PathExtensionRule extends Rule {
	
	private static final long serialVersionUID = 1L;

	@OneToOne
	private UrlPath allow;
	
	@OneToOne
	private UrlPath deny;
	
	public PathExtensionRule (){
		allow = new UrlPath();
		deny = new UrlPath();
	}

	public PathExtensionRule (String allow, String deny) {
		this.setAllow(new UrlPath(allow, true));
		this.setDeny(new UrlPath(deny, true));
	}

	public Action check(HttpServletRequest request,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {

//		if ( allow != null && allow.matcher(request.getRequestURI()).matches() ) {
//			return new DoNothingAction();
//		} else if ( deny != null && deny.matcher(request.getRequestURI()).matches() ) {
//
//			log(request, "Disallowed extension pattern '" + deny.pattern() + "' found on URI '" + request.getRequestURI() + "'");
//
//			return new DefaultAction();
//		}

		return new DoNothingAction();
	}

	public UrlPath getAllow() {
		return allow;
	}

	public void setAllow(UrlPath allow) {
		this.allow = allow;
	}

	public UrlPath getDeny() {
		return deny;
	}

	public void setDeny(UrlPath deny) {
		this.deny = deny;
	}
	
	

}
