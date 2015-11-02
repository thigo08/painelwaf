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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.owasp.esapi.waf.internal.InterceptingHTTPServletResponse;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * This is the Rule subclass executed for &lt;bean-shell-script&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */

@Entity
public class BeanShellRule extends Rule {

	private Interpreter i;
	
	private String script;
	
	@OneToOne
	//@JoinColumn(name = "fk_id_rule") 
	private UrlPath path;
	
	public BeanShellRule(){
		path = new UrlPath();
	}
	
	public BeanShellRule(String fileLocation, String id, String path) throws IOException, EvalError { 
		i = new Interpreter();
		i.set("logger", logger);
		this.script = getFileContents( ESAPI.securityConfiguration().getResourceFile(fileLocation));
		//this.id = id;
		this.setPath(new UrlPath(path, true));
	}
	
	public Action check(HttpServletRequest request,
			InterceptingHTTPServletResponse response, 
			HttpServletResponse httpResponse) {
		
		Pattern patternPath = Pattern.compile(getPath().getUrl());

		/*
		 * Early fail: if the URL doesn't match one we're interested in.
		 */
		
		if ( path != null && ! patternPath.matcher(request.getRequestURI()).matches() ) {
			return new DoNothingAction();
		}
		
		/*
		 * Run the beanshell that we've already parsed
		 * and pre-compiled. Populate the "request"
		 * and "response" objects so the script has
		 * access to the same variables we do here.
		 */
		
		try {
		
			Action a = null;
			
			i.set("action", a);
			i.set("request", request);
			
			if ( response != null ) {
				i.set("response", response);	
			} else {
				i.set("response", httpResponse);
			}

			i.set("session", request.getSession());
			i.eval(script);

			a = (Action)i.get("action");
	
			if ( a != null ) {
				return a;
			}
			
		} catch (EvalError e) {
			log(request,"Error running custom beanshell rule (" + getId() + ") - " + e.getMessage());
		}
	
		return new DoNothingAction();
	}
	
	private String getFileContents(File f) throws IOException {
		
		FileReader fr = new FileReader(f);
		StringBuffer sb = new StringBuffer();
		String line;
		BufferedReader br = new BufferedReader(fr);
		
		while( (line=br.readLine()) != null ) {
			sb.append(line + System.getProperty("line.separator"));
		}
		
		return sb.toString();
	}

	public UrlPath getPath() {
		return path;
	}

	public void setPath(UrlPath path) {
		this.path = path;
	}

}
