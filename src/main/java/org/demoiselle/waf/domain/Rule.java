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

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.demoiselle.waf.internal.InterceptingHTTPServletResponse;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.owasp.esapi.waf.actions.Action;

/**
 * This is the base class for the WAF rules.
 * @author Arshan Dabirsiaghi
 *
 */
//@MappedSuperclass
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Rule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Transient
	protected static Logger logger = ESAPI.getLogger(Rule.class);

	public abstract Action check( HttpServletRequest request, InterceptingHTTPServletResponse response, HttpServletResponse httpResponse );

	public void log( HttpServletRequest request, String message ) {
		logger.warning(Logger.SECURITY_FAILURE,"[IP=" + request.getRemoteAddr() +
				",Rule=" + this.getClass().getSimpleName() + ",ID="+getId()+"] " + message);
	}

	public String toString() {
		return "Rule:" + this.getClass().getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
