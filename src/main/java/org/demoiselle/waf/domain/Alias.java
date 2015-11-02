package org.demoiselle.waf.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Alias{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String name;
	
	@OneToOne
	//@JoinColumn(name = "ID_RULE") 
	private UrlPath path;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	//@JoinColumn(name = "fk_id_rule")
	private List<UrlPath> exceptions;
	
	public Alias(){
		path = new UrlPath();
	}
	
	public Alias(String name, String path, List<UrlPath> exceptions) {
		this.setName(name);
		this.setPath(new UrlPath(path, true));
		this.setExceptions(exceptions);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UrlPath getPath() {
		return path;
	}

	public void setPath(UrlPath path) {
		this.path = path;
	}

	public List<UrlPath> getExceptions() {
		if (exceptions == null)
			exceptions = new ArrayList<UrlPath>();
		return exceptions;
	}

	public void setExceptions(List<UrlPath> exceptions) {
		this.exceptions = exceptions;
	}
	
}
