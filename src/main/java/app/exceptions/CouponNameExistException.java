package app.exceptions;

public class CouponNameExistException extends Exception {
	public CouponNameExistException() {
		super("Coupon name already in use for your company, failed to update coupon");
	}
}
