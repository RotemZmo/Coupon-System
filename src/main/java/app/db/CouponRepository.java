package app.db;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.entities.CategoryType;
import app.entities.Company;
import app.entities.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
	public List<Coupon> findAllByCompanyId(long compId);

	public List<Coupon> findAllByCompanyIdAndType(long compId, CategoryType type);

	public Coupon findByCompanyIdAndTitle(long compId, String name);
	
	public Coupon findByIdAndCompany(long id, Company company);

	public List<Coupon> findAllByCompanyIdAndActive(long compId, boolean active);

	@Query("SELECT COUNT(coupon_id) FROM Coupon c")
	int getSumOfCoupons();

	public List<Coupon> findAllByEndDateLessThan(Date date);

	public List<Coupon> findAllByEndDateLessThanAndActive(Date date, boolean active);

	public List<Coupon> findAllByPriceGreaterThanEqualAndCompanyId(double price, long compId);
	
	public List<Coupon> findAllByActive(boolean active);
}
