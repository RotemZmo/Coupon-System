package app.exceptions;

public class UserExistException extends Exception {

	public UserExistException() {
		super("Email already in use, failed to add user");
	}
	
	

}
