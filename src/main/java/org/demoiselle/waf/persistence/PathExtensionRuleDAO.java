package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.PathExtensionRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class PathExtensionRuleDAO extends JPACrud<PathExtensionRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}