package app.exceptions;

public class CustomerExistException extends Exception {
		public CustomerExistException() {
			super("Customer already exist, failed to add customer");
		}
}
