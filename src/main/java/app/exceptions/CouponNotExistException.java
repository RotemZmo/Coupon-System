package app.exceptions;

public class CouponNotExistException extends Exception {

	public CouponNotExistException() {
		super("Coupon doesn't exist therefore couldn't update the coupon");
	}

}
