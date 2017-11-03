package com.blogspot.direinem.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents an abstraction of a newsletter. Encapsulates the attributes
 * email and newsletter state.
 *
 * @author Dirk Reinemann
 */
@Entity
@Table(name = "newsletter")
public class Newsletter implements Serializable {

	private static final long serialVersionUID = -6894281253045403755L;

	/**
	 * Represents the action state of the newsletter.
	 *
	 * @author Dirk Reinemann
	 */
	public enum NewsletterState {
		REGISTER("Register"), CANCEL("Cancel");

		private String label;

		/**
		 * Constructs a newsletter state with the given label.
		 *
		 * @param label the label for the newsletter state
		 */
		private NewsletterState(String label) {
			this.label = label;
		}

		/**
		 * Returns the label of the newsletter state.
		 *
		 * @return
		 */
		public String getLabel() {
			return label;
		}
	}

	@Id
	private long id;
	private String email;
	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private NewsletterState newsletterState;

	/**
	 * Required for Java Persistence API.
	 */
	public Newsletter() {
	}

	/**
	 * Constructs a newsletter with the given email address and the newsletter
	 * state.
	 *
	 * @param email the email address of the user
	 * @param newsletterState the newsletter state
	 */
	public Newsletter(String email, NewsletterState newsletterState) {
		this.email = email;
		this.newsletterState = newsletterState;
	}

	/**
	 * Returns the primary key of the newsletter.
	 *
	 * @return the primary key of the newsletter
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the email address of the newsletter.
	 *
	 * @return the email address of the newsletter
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the newsletter.
	 *
	 * @param email the email address of the newsletter
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the state of the newsletter.
	 *
	 * @return the state of the newsletter
	 */
	public NewsletterState getNewsletterState() {
		return newsletterState;
	}

	/**
	 * Sets the state of the newsletter.
	 *
	 * @param newsletterState the state of the newsletter
	 */
	public void setNewsletterState(NewsletterState newsletterState) {
		this.newsletterState = newsletterState;
	}

	/**
	 * Returns all attribute values of the newsletter in human readable format.
     */
	@Override
	public String toString() {
		return "Newsletter(id=" + id + ", email=" + email + ", newsletterState="
				+ newsletterState + ")";
	}
}
