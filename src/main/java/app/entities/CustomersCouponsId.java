package app.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CustomersCouponsId implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -247167686031804212L;
	@Column
	private Long customerId;
	@Column
	private Long couponId;

	public CustomersCouponsId() {
		super();
	}

	public CustomersCouponsId(Long customerId, Long couponId) {
		this.customerId = customerId;
		this.couponId = couponId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	 @Override
	    public int hashCode() {
	        return Objects.hash(customerId, couponId);
	    }

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	 
	        if (o == null || getClass() != o.getClass())
	            return false;
	 
	        CustomersCouponsId that = (CustomersCouponsId) o;
	        return Objects.equals(customerId, that.customerId) &&
	               Objects.equals(couponId, that.couponId);
	    }
	 
	
	

}
