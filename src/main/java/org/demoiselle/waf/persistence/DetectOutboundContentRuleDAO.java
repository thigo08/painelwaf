package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.DetectOutboundContentRule;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class DetectOutboundContentRuleDAO extends JPACrud<DetectOutboundContentRule, Long> {
	
	private static final long serialVersionUID = 1L;
	
}