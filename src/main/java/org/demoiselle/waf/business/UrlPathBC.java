package org.demoiselle.waf.business;


import org.demoiselle.waf.domain.UrlPath;
import org.demoiselle.waf.persistence.UrlPathDAO;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class UrlPathBC extends DelegateCrud<UrlPath, Long, UrlPathDAO> {

	private static final long serialVersionUID = 1L;


}
