package org.demoiselle.waf.persistence;

import org.demoiselle.waf.domain.UrlPath;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class UrlPathDAO extends JPACrud<UrlPath, Long> {

	private static final long serialVersionUID = 1L;

}
