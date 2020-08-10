package app.exceptions;

public class AdminExistException extends Exception {

	public AdminExistException() {
		super("There is already an admin with this email");
		// TODO Auto-generated constructor stub
	}

	
}
