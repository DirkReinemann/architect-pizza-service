package com.blogspot.direinem.infrastructure.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * Represents a factory for the @FacesContext.
 *
 * @author Dirk Reinemann
 */
public class FacesContextProducer {

	/**
	 * Returns the current @FacesContext for injection.
	 *
	 * @return the current @FacesContext
	 */
	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
}
