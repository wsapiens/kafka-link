package com.all4canid.link.domain.job;

import java.io.Serializable;

/**
 * Textura Job info domain object
 * Textura eBIS service will return this job info, once receive import or export request
 * to allow to retrieve import audit or export data later
 * @author spark
 *
 */
public class LinkJob implements Serializable {

	private static final long serialVersionUID = -4419550021896368614L;

	private String uri;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public String toString() {
		return String.join("", "Job [ uri=", this.uri, " ]");
	}
}
