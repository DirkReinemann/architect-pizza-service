package com.blogspot.direinem.infrastructure.component;

import java.util.List;

/**
 * Represents an interface for an updateable list of objects.
 * 
 * @author Dirk Reinemann
 *
 * @param <T> the type of the objects
 */
public interface UpdateableList<T> {
	
	/**
	 * Returns the objects as list.
	 * 
	 * @return the list of objects
	 */
	List<T> getList();
	
	/**
	 * Sets the object with the given identifier updateable true.
	 * 
	 * @param id the identifier of the object
	 */
	void setUpdateable(Object id);
		
	/**
	 * Sets the object with the given identifier to the given updateable value.
	 * 
	 * @param id the identifier of the object
	 * @param updateable the updateable state
	 */
	void setUpdateable(Object id, boolean updateable);
	
	/**
	 * Returns the updateable state of the object with the given identifier.
	 * 
	 * @param id the identifier of the object
	 * @return updateable state
	 */
	boolean isUpdateable(Object id);
	
	/**
	 * Returns the object with the given identifier.
	 * 
	 * @param id the identifier of the object
	 * @return the object with the given identifier
	 */
	T get(Object id);
	
	/**
	 * Adds an object to the list.
	 * 
	 * @param object the object to add
	 */
	void put(T object);
	
	/**
	 * Adds a list of objects to the list.
	 * 
	 * @param objects the list of objects to add
	 */
	void put(List<T> objects);
	
	/**
	 * Replaces the object with the given identifier by the given object.
	 * 
	 * @param id the identifier of the object to replace
	 * @param object the object for replacement
	 */
	void update(Object id, T object);
	
	/**
	 * Removes the object with the given identifier from the list.
	 * 
	 * @param id the identifier of the object
	 */
	void remove(Object id);
}
