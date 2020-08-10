package app.exceptions;

public class CouponExpiredException extends Exception {
		public CouponExpiredException() {
			super("Coupon expired, failed to purchase");
		}

}
