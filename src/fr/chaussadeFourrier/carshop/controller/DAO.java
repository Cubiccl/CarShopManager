package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;

public abstract class DAO<T> {
	protected Connection connect = null;
	
	/**
	 * init DAO by connecting to the database.
	 * @param conn connexion to the database
	 */
	public DAO(Connection conn){
		this.connect = conn;
	}
	
	/**
	 * Create an object in the database and return success.
	 * @param obj object to create
	 * @return success
	 */
	public abstract boolean create(T obj);
	
	/**
	 * Delete an object in the database and return success.
	 * @param obj object to delete
	 * @return success
	 */
	public abstract boolean delete(T obj);
	
	/**
	 * Update an object in the database and return success.
	 * @param obj object to update
	 * @return success
	 */
	public abstract boolean update(T obj);
	
	/**
	 * Search an object by id of type int and return it.
	 * @param id id to searched object
	 * @return object found
	 */
	public abstract T find(int id);
	
	/**
	 * Search an object by id of type String and return it.
	 * @param id id to searched object
	 * @return object found
	 */
	public abstract T find(String id);
}
