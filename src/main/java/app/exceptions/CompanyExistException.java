package app.exceptions;

public class CompanyExistException extends Exception {
	public CompanyExistException() {
		super("Company name/email alredy exist, failed to add company");
	}
	
	public CompanyExistException(String reason) {
		super("Company " + reason +  " alredy exist, failed to add company");
	}

}
