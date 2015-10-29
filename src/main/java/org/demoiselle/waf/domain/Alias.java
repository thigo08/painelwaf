package org.demoiselle.waf.domain;

import javax.persistence.Entity;
import javax.persistence.Column;

@Entity
public class Alias extends UrlPath {
	
	@Column(unique = true)
	private String name;
	
	public Alias(){
		
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
}
