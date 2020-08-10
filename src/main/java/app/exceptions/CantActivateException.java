package app.exceptions;

public class CantActivateException extends Exception {

	public CantActivateException() {
		super("Coupon's expiration date is in the past, please set valid expiration date before activating coupon");
		// TODO Auto-generated constructor stub
	}

	
}
