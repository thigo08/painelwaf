package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.AppGuardianConfiguration;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class AppGuardianConfigurationDAO extends JPACrud<AppGuardianConfiguration, Long> {
	
	private static final long serialVersionUID = 1L;
}
