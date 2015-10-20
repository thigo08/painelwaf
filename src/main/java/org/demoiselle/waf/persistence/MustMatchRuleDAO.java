package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.MustMatchRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class MustMatchRuleDAO extends JPACrud<MustMatchRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}