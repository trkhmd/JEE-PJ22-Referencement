package fr.eservices.drive.dao;

public class DataException extends Exception {
	private static final long serialVersionUID = 3634318263611299371L;

	public DataException(String msg) {
		super(msg);
	}
	
	public DataException(String msg, Exception cause) {
		super(msg, cause);
	}
}
