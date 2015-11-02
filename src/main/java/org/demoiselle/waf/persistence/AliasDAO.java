package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.Alias;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class AliasDAO extends JPACrud<Alias, Long> {
	
	private static final long serialVersionUID = 1L;
}
