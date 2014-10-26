package com.blogspot.direinem.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Represents an abstraction of a repository that provides direct access to the {@link EntityManager}.
 *
 * @author Dirk Reinemann
 */
public abstract class AbstractRepository {

	@PersistenceContext
	protected EntityManager entityManager;
}