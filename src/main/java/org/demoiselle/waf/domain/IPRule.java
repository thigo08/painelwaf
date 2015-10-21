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

import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DefaultAction;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.owasp.esapi.waf.internal.InterceptingHTTPServletResponse;

/**
 * This is the Rule subclass executed for &lt;detect-source-ip&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */

@Entity
public class IPRule extends Rule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne
	private UrlPath allowedIP;
	
	private String exactPath;
	
	@OneToOne
	private UrlPath path;
	
	private boolean useExactPath = false;
	
	private String ipHeader;
	
	public IPRule(){
		allowedIP = new UrlPath();
		path = new UrlPath();
	}

	public IPRule(String allowedIP, String path, String ipHeader) {
		this.setAllowedIP(new UrlPath(allowedIP, true));
		this.setPath(new UrlPath(path, true));
		this.useExactPath = false;
		this.setIpHeader(ipHeader);
		//setId(id);
	}

	public IPRule(String allowedIP, String exactPath) {
		this.path = null;
		this.exactPath = exactPath;
		this.useExactPath = true;
		//setId(id);
	}

	public Action check(HttpServletRequest request,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {
		
		Pattern patternPath = Pattern.compile(getPath().getUrl());
		Pattern patternAllowedIP = Pattern.compile(getAllowedIP().getUrl());
		String uri = request.getRequestURI();

		if ( (!useExactPath && patternPath.matcher(uri).matches()) ||
			 ( useExactPath && exactPath.equals(uri)) ) {
			
			String sourceIP = request.getRemoteAddr() + "";
			
			if ( ipHeader != null ) {
				sourceIP = request.getHeader(ipHeader);
			}
			
			if ( ! patternAllowedIP.matcher(sourceIP).matches() ) {
				log(request, "IP not allowed to access URI '" + uri + "'");
				return new DefaultAction();
			}
		}

		return new DoNothingAction();
	}

	public UrlPath getAllowedIP() {
		return allowedIP;
	}

	public void setAllowedIP(UrlPath allowedIP) {
		this.allowedIP = allowedIP;
	}

	public String getExactPath() {
		return exactPath;
	}

	public void setExactPath(String exactPath) {
		this.exactPath = exactPath;
	}

	public UrlPath getPath() {
		return path;
	}

	public void setPath(UrlPath path) {
		this.path = path;
	}

	public boolean isUseExactPath() {
		return useExactPath;
	}

	public void setUseExactPath(boolean useExactPath) {
		this.useExactPath = useExactPath;
	}

	public String getIpHeader() {
		return ipHeader;
	}

	public void setIpHeader(String ipHeader) {
		this.ipHeader = ipHeader;
	}
	
}
