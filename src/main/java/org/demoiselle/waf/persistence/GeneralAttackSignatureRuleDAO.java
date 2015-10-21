package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.GeneralAttackSignatureRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class GeneralAttackSignatureRuleDAO extends JPACrud<GeneralAttackSignatureRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}