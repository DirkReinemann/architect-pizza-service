package com.blogspot.direinem.infrastructure.component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an implementation of the @UpdateableList interface that provides
 * a cache mechanism for the last used objects. This makes the access to the
 * objects faster because of the unneeded iteration through the lists.
 *
 * @author Dirk Reinemann
 *
 * @param <T> the type of the objects
 */
public abstract class CachedList<T> implements UpdateableList<T> {

	private static final Boolean DEFAULT = Boolean.FALSE;

	private Map<T, Boolean> objects = new LinkedHashMap<T, Boolean>();
	private T objectCache;

	/**
	 * Constructs an empty cached list.
	 */
	public CachedList() {
	}

	/**
	 * Constructs an empty cached list with the given values.
	 *
	 * @param objects
	 */
	public CachedList(List<T> objects) {
		put(objects);
	}

	/**
	 * Returns all objects in a list.
	 */
	@Override
	public final List<T> getList() {
		return new ArrayList<T>(objects.keySet());
	}

	/**
	 * @see UpdateableList#get(Object)
	 *
	 * Checks the equality of the object with the given id and the cached object.
	 * Returns the cached object on equality. Iterates the list of objects and
	 * checks the equality based in their identifiers otherwise. Returns the
	 * object that was found.
	 */
	@Override
	public final T get(Object id) {
		T result = objectCache;
		if (objectCache == null || !compare(id, objectCache)) {
			for (T obj : objects.keySet()) {
				if (compare(id, obj)) {
					result = obj;
					objectCache = obj;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * @see UpdateableList#put(Object)
	 */
	@Override
	public final void put(T object) {
		objects.put(object, DEFAULT);
	}

	/**
	 * @see UpdateableList#put(List)
	 */
	@Override
	public final void put(List<T> objects) {
		for (T object : objects) {
			put(object);
		}
	}

	/**
	 * @see UpdateableList#isUpdateable(Object)
	 */
	@Override
	public final boolean isUpdateable(Object id) {
		boolean result = false;
		T object = get(id);
		if (objects.get(object)) {
			result = true;
		}
		return result;
	}

	/**
	 * @see UpdateableList#setUpdateable(Object)
	 */
	@Override
	public final void setUpdateable(Object id) {
		setUpdateable(id, Boolean.TRUE);
	}

	/**
	 * @see UpdateableList#setUpdateable(Object)
	 *
	 * Sets the updateable state the object with the given identifier if it
	 * exists in the list.
	 */
	@Override
	public final void setUpdateable(Object id, boolean updateable) {
		T object = get(id);
		if (objects.containsKey(object)) {
			objects.put(object, updateable);
		}
	}

	/**
	 * @see UpdateableList#update(Object, Object)
	 *
	 * Removes the object with the given identifier from the list and adds the
	 * given object to the list afterwards.
	 */
	@Override
	public final void update(Object id, T object) {
		T obj = get(id);
		if (obj != null) {
			objects.remove(obj);
		}
		put(object);
	}

	/**
	 * @see UpdateableList#remove(Object)
	 *
	 * Uses the cached object to remove the object with the given identifier.
	 */
	@Override
	public final void remove(Object id) {
		T obj = get(id);
		if (obj != null) {
			objects.remove(obj);
		}
	}

	/**
	 * Compares the object with the given identifier. Needs to be implemented
	 * according the type of object.
	 *
	 * @param id the identifier of the object
	 * @param object the object
	 * @return true on equality, false on inequality
	 */
	protected abstract boolean compare(Object id, T object);
}
