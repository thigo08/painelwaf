package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.RestrictContentTypeRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class RestrictContentTypeRuleDAO extends JPACrud<RestrictContentTypeRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}