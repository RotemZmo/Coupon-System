package app.exceptions;

public class CompanyNotExistException extends Exception {
	public CompanyNotExistException() {
		super("Company doesn't exist, failed to update company");
	}
}
