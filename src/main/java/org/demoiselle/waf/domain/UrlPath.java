package org.demoiselle.waf.domain;

import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class UrlPath {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fk_id_rule")
	private Rule rule;
	
	private String url;	
	private boolean regex;
	
	@Transient
	private Pattern pattern;
	
	public UrlPath (){
		url = "/";
		regex = true;
		
	}
	
	public boolean matches(String uri){
		if (regex){			
			if (pattern == null)
				pattern = Pattern.compile(url);
			
			return pattern.matcher(uri).matches();
						
		} else {
			return this.url.equals(uri);
		}
	}
		
		
	public UrlPath (String path, boolean regex){
		this.url = path;
		this.regex = regex;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isRegex() {
		return regex;
	}

	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

}
