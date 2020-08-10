package app.db;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import app.entities.CustomersCoupons;

public interface CustomersCouponsRepository extends JpaRepository<CustomersCoupons, Long> {
	public CustomersCoupons findByCustomerIdAndCouponId(long customerId, long couponId);
	public Set<CustomersCoupons> findAllByIsActive(boolean active);
	public Set<CustomersCoupons> findAllByCustomerId(long customerId);
	public Set<CustomersCoupons> findAllByCouponId(long couponId);
	public Set<CustomersCoupons> findAllByCustomerIdAndCouponIdAndIsActive(long customerId, long couponId,boolean active);
	public Set<CustomersCoupons> findAllByCouponIdAndIsActive(long couponId,boolean active);
	public Set<CustomersCoupons> findAllByCustomerIdAndIsActive(long customerId,boolean active);
	@Query("SELECT sum(feePaid) FROM CustomersCoupons c")
	public double sumAllFees();
	@Query("SELECT SUM(feePaid) FROM CustomersCoupons e WHERE e.purchaseDate BETWEEN :startDate AND :endDate")
	public double sumAllFeesBetweenDates(Date startDate, Date endDate);



}
