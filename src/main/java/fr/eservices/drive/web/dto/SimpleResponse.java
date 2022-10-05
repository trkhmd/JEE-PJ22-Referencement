package fr.eservices.drive.web.dto;

public class SimpleResponse {
	
	public String message;
	public Status status = Status.OK;
	
	public enum Status {
		OK, ERROR
	};
	

}
