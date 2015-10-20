package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.IPRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class IPRuleDAO extends JPACrud<IPRule, String> {
	
	private static final long serialVersionUID = 1L;
	
}