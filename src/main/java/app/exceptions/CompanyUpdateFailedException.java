package app.exceptions;

public class CompanyUpdateFailedException extends Exception {
	public CompanyUpdateFailedException() {
		super("Company name change is not allowed when updating company, failed to  update company");
	}
}
