package fr.eservices.drive.dao;

import fr.eservices.drive.model.Cart;

public interface CartDao {

	/**
	 * get a Cart from its Id
	 * @param id cart Id
	 * @throws DataException if id is lower or equals 0
	 */
	Cart getCartContent(int id) throws DataException;

	/**
	 * store a new cart
	 * @param id cart Id
	 * @param cart to store
	 * @throws DataException if cart already exists or if id is lower or equals 0
	 */
	void store(int id, Cart cart) throws DataException;

}
