package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.AddHeaderRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class AddHeaderRuleDAO extends JPACrud<AddHeaderRule, Long> {
	
	private static final long serialVersionUID = 1L;
}
