package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.BeanShellRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class BeanShellRuleDAO extends JPACrud<BeanShellRule, String> {
	
	private static final long serialVersionUID = 1L;
	
}