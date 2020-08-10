package app.exceptions;

public class CustomerEmailExistException extends Exception {
	public CustomerEmailExistException() {
		super("Email already in use, failed to update company");
	}
}
