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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.Logger;
import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.demoiselle.waf.internal.InterceptingHTTPServletResponse;

/**
 * This is the Rule subclass executed for &lt;dynamic-insertion&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */
@Entity 
public class ReplaceContentRule extends Rule {
	
	private static final long serialVersionUID = 1L;

	@OneToOne
	private UrlPath pattern;
	
	private String replacement;
	
	@OneToOne
	private UrlPath contentType;
	
	@OneToOne
	private UrlPath path;
	
	public ReplaceContentRule(){
		pattern = new UrlPath();
		contentType = new UrlPath();
		path = new UrlPath();
	}
	
	public ReplaceContentRule(String id, String pattern, String replacement, String contentType, String path) {
		this.setPattern(new UrlPath(pattern, true));
		this.setReplacement(replacement);
		this.setContentType(new UrlPath(contentType, true));
		this.setPath(new UrlPath(path, true));
		//setId(id);
	}

	/*
	 * Use regular expressions with capturing parentheses to perform replacement.
	 */

	public Action check(HttpServletRequest request,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {
		
		Pattern patternPath = Pattern.compile(getPath().getUrl());
		Pattern patternContentType = Pattern.compile(getContentType().getUrl());
		Pattern patternPattern = Pattern.compile(getPattern().getUrl());

		/*
		 * First early fail: if the URI doesn't match the paths we're interested in.
		 */
		String uri = request.getRequestURI();
		if ( patternPath != null && ! patternPath.matcher(uri).matches() ) {
			return new DoNothingAction();
		}
		
		/*
		 * Second early fail: if the content type is one we'd like to search for output patterns.
		 */

		if ( patternContentType != null ) {
			if ( response.getContentType() != null && ! patternContentType.matcher(response.getContentType()).matches() ) {
				return new DoNothingAction();
			}
		}

		byte[] bytes = null;

		try {
			bytes = response.getInterceptingServletOutputStream().getResponseBytes();
		} catch (IOException ioe) {
			log(request,"Error matching pattern '" + patternPattern.pattern() + "', IOException encountered (possibly too large?): " + ioe.getMessage() + " (in response to URL: '" + request.getRequestURL() + "')");
			return new DoNothingAction(); // yes this is a fail open!
		}

		
		try {

			String s = new String(bytes,response.getCharacterEncoding());

			Matcher m = patternPattern.matcher(s);
			String canary = m.replaceAll(replacement);
			
			try {
				
				if ( ! s.equals(canary) ) {
					response.getInterceptingServletOutputStream().setResponseBytes(canary.getBytes(response.getCharacterEncoding()));
					logger.debug(Logger.SECURITY_SUCCESS, "Successfully replaced pattern '" + patternPattern.pattern() + "' on response to URL '" + request.getRequestURL() + "'");
				}
				
			} catch (IOException ioe) {
				logger.error(Logger.SECURITY_FAILURE, "Failed to replace pattern '" + patternPattern.pattern() + "' on response to URL '" + request.getRequestURL() + "' due to [" + ioe.getMessage() + "]");
			}

		} catch(UnsupportedEncodingException uee) {
			logger.error(Logger.SECURITY_FAILURE, "Failed to replace pattern '" + patternPattern.pattern() + "' on response to URL '" + request.getRequestURL() + "' due to [" + uee.getMessage() + "]");
		}

		return new DoNothingAction();
	}

	public UrlPath getPattern() {
		return pattern;
	}

	public void setPattern(UrlPath pattern) {
		this.pattern = pattern;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public UrlPath getContentType() {
		return contentType;
	}

	public void setContentType(UrlPath contentType) {
		this.contentType = contentType;
	}

	public UrlPath getPath() {
		return path;
	}

	public void setPath(UrlPath path) {
		this.path = path;
	}
	
}
