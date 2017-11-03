package com.blogspot.direinem.infrastructure.exception;

/**
 * Represents an exception for the deletion of an administrator when is the last
 * one in the database. Encapsulates the primary key of the administrator.
 *
 * @author Dirk Reinemann
 */
public final class DeleteAdminException extends Exception {

	private static final long serialVersionUID = -77888696574815213L;

	private static final String MESSAGE = ";Last admin cannot be deleted!;";

	private int idAdmin;

	/**
	 * Constructs an object with the given primary key of the administrator and
	 * the MESSAGE.
	 *
	 * @param idAdmin the primary key of the administrator
	 */
	public DeleteAdminException(int idAdmin) {
		super(MESSAGE + idAdmin + ";");

		this.idAdmin = idAdmin;
	}

	/**
	 * Returns the primary key of the administrator.
	 *
	 * @return the primary key of the administrator
	 */
	public int getIdAdmin() {
		return idAdmin;
	}
}
