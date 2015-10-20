package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.ReplaceContentRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class ReplaceContentRuleDAO extends JPACrud<ReplaceContentRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}