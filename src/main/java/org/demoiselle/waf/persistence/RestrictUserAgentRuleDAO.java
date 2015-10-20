package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.RestrictUserAgentRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class RestrictUserAgentRuleDAO extends JPACrud<RestrictUserAgentRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}	