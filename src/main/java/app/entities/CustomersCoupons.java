package app.entities;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers_coupons")
@NoArgsConstructor
public @Data class CustomersCoupons {

	@EmbeddedId
	private CustomersCouponsId purchaseId;
	@ManyToOne
	@MapsId("customerId")
	@JoinColumn(name = "customer_id")
	Customer customer;
	@ManyToOne
	@MapsId("couponId")
	@JoinColumn(name = "coupon_id")
	Coupon coupon;
	@Column
	private Date purchaseDate;
	@Column
	boolean isActive;
	@Column
	double feePaid;

	/**
	 * 
	 * @param customer
	 * @param coupon
	 * @param feeRate
	 */
	public CustomersCoupons(Customer customer, Coupon coupon, double feeRate) {
		this.coupon = coupon;
		this.customer = customer;
		this.isActive = coupon.isActive();
		this.purchaseDate = new Date(System.currentTimeMillis());
		this.purchaseId = new CustomersCouponsId(customer.getId(), coupon.getId());
		this.feePaid = coupon.getPrice()*feeRate;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		CustomersCoupons that = (CustomersCoupons) o;
		return Objects.equals(customer, that.customer) && Objects.equals(coupon, that.coupon);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, coupon);
	}

	@Override
	public String toString() {
		return "CustomersCoupons [purchaseId=" + purchaseId + ", customer=" + customer.getId() + ", coupon=" + coupon.getId()
				+ ", purchaseDate=" + purchaseDate + ", isActive=" + isActive + ", feePaid=" + feePaid + "]";
	}
	

}
