package app.exceptions;

public class CouponOwnedException extends Exception {
	public CouponOwnedException() {
	 super("You already bought this coupon, purchase failed");
 }
}
