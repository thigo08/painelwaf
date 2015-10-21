package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.SimpleVirtualPatchRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class SimpleVirtualPatchRuleDAO extends JPACrud<SimpleVirtualPatchRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}