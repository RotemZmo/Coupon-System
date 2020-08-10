package app.exceptions;

public class CouponExistException extends Exception {

	public CouponExistException() {
		super("Coupon already exists, failed to add new coupon");
	}

}
