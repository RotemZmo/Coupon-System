package app.exceptions;

public class InvalidFeeException extends Exception {

	public InvalidFeeException() {
		super("Fee must be between 0-1, please try again");
		// TODO Auto-generated constructor stub
	}

}
