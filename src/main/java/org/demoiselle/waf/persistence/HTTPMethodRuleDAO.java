package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.HTTPMethodRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class HTTPMethodRuleDAO extends JPACrud<HTTPMethodRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}