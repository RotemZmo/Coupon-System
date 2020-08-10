package app.exceptions;

public class CustomerNotExistException extends Exception {
		public CustomerNotExistException() {
			super("Customer doesn't exist, failed to update customer");
		}
}
