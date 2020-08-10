package app.exceptions;

public class CouponOutOfStockException extends Exception {
		public CouponOutOfStockException() {
			super("Coupon out of stock, purchase failed");
		}

}
