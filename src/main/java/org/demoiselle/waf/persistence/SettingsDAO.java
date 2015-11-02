package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.Settings;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class SettingsDAO extends JPACrud<Settings, Long> {
	
	private static final long serialVersionUID = 1L;
}
