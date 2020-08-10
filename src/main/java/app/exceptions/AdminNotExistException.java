package app.exceptions;

public class AdminNotExistException extends Exception {

	public AdminNotExistException() {
		super("Admin don't exist");
	}
}
