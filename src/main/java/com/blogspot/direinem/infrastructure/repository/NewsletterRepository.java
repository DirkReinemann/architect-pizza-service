package com.blogspot.direinem.infrastructure.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import com.blogspot.direinem.domain.model.Newsletter;
import com.blogspot.direinem.domain.model.Newsletter.NewsletterState;

/**
 * Represents a repository to access and manipulate the newsletter objects.
 *
 * @author Dirk Reinemann
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NewsletterRepository extends AbstractRepository {

	/**
	 * Loads all newsletter with the given email address from the database. If
	 * the newsletter exists, it is updated and otherwise it is created in
	 * the database.
	 *
	 * @param email the email address of the user
	 * @param newsletterState the state of the newsletter
	 */
	@SuppressWarnings("unchecked")
	public void saveNewsletter(String email, NewsletterState newsletterState) {
		Query query = entityManager.createQuery("SELECT n FROM Newsletter n WHERE n.email=:email", Newsletter.class);
		query.setParameter("email", email);

		List<Newsletter> newsletters = query.getResultList();

		Newsletter newsletter;
		if (newsletters.isEmpty()) {
			newsletter = new Newsletter(email, newsletterState);
			entityManager.persist(newsletter);
		}
		else {
			newsletter = newsletters.get(0);
			newsletter.setNewsletterState(newsletterState);
			entityManager.merge(newsletter);
		}
	}
}
