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

import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DefaultAction;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.owasp.esapi.waf.internal.InterceptingHTTPServletRequest;
import org.demoiselle.waf.internal.InterceptingHTTPServletResponse;

/**
 * This is the Rule subclass executed for &lt;virtual-patch&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */
@Entity
public class SimpleVirtualPatchRule extends Rule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String REQUEST_PARAMETERS = "request.parameters.";
	private static final String REQUEST_HEADERS = "request.headers.";

	@OneToOne
	private UrlPath path;
	
	private String variable;
	
	@OneToOne
	private UrlPath valid;
	
	private String message;
	
	public SimpleVirtualPatchRule(){
		path = new UrlPath();
		valid = new UrlPath();
	}

	public SimpleVirtualPatchRule(String path, String variable, String valid, String message) {
		this.setPath(new UrlPath(path, true));
		this.setVariable(variable);
		this.setValid(new UrlPath(valid, true));
		this.setMessage(message);
	}

	public Action check(HttpServletRequest req,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {

		InterceptingHTTPServletRequest request = (InterceptingHTTPServletRequest)req;
		Pattern patternPath = Pattern.compile(getPath().getUrl());
		Pattern patternValid = Pattern.compile(getValid().getUrl());

		String uri = request.getRequestURI();
		if ( ! patternPath.matcher(uri).matches() ) {

			return new DoNothingAction();

		} else {

			/*
			 * Decide which parameters/headers to act on.
			 */
			String target = null;
			Enumeration en = null;
			boolean parameter = true;

			if ( variable.startsWith(REQUEST_PARAMETERS)) {

				target = variable.substring(REQUEST_PARAMETERS.length());
				en = request.getParameterNames();

			} else if ( variable.startsWith(REQUEST_HEADERS) ) {

				parameter = false;
				target = variable.substring(REQUEST_HEADERS.length());
				en = request.getHeaderNames();

			} else {
				log(request, "Patch failed (improperly configured variable '" + variable + "')");
				return new DefaultAction();
			}

			/*
			 * If it contains a regex character, it's a regex. Loop through elements and grab any matches.
			 */
			if ( target.contains("*") || target.contains("?") ) {

				target = target.replaceAll("\\*", ".*");
				Pattern p = Pattern.compile(target);
				while (en.hasMoreElements() ) {
					String s = (String)en.nextElement();
					String value = null;
					if ( p.matcher(s).matches() ) {
						if ( parameter ) {
							value = request.getDictionaryParameter(s);
						} else {
							value = request.getHeader(s);
						}
						if ( value != null && ! patternValid.matcher(value).matches() ) {
							log(request, "Virtual patch tripped on variable '" + variable + "' (specifically '" + s + "'). User input was '" + value + "' and legal pattern was '" + patternValid.pattern() + "': " + message);
							return new DefaultAction();
						}
					}
				}
				
				return new DoNothingAction();

			} else {

				if ( parameter ) {
					String value = request.getDictionaryParameter(target);
					if ( value == null || patternValid.matcher(value).matches() ) {
						return new DoNothingAction();
					} else {
						log(request, "Virtual patch tripped on parameter '" + target + "'. User input was '" + value + "' and legal pattern was '" + patternValid.pattern() + "': " + message);
						return new DefaultAction();
					}
				} else {
					String value = request.getHeader(target);
					if ( value == null || patternValid.matcher(value).matches() ) {
						return new DoNothingAction();
					} else {
						log(request, "Virtual patch tripped on header '" + target + "'. User input was '" + value + "' and legal pattern was '" + patternValid.pattern() + "': " + message);
						return new DefaultAction();
					}
				}
			}

		}

	}

	public UrlPath getPath() {
		return path;
	}

	public void setPath(UrlPath path) {
		this.path = path;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public UrlPath getValid() {
		return valid;
	}

	public void setValid(UrlPath valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
