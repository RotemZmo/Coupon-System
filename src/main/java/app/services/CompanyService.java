package app.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.db.CompanyRepository;
import app.db.CouponRepository;
import app.db.CustomersCouponsRepository;
import app.entities.CategoryType;
import app.entities.Company;
import app.entities.Coupon;
import app.entities.CustomersCoupons;
import app.exceptions.CantActivateException;
import app.exceptions.CompanyNotExistException;
import app.exceptions.CouponExistException;
import app.exceptions.CouponNameExistException;
import app.exceptions.CouponNotExistException;
import lombok.NoArgsConstructor;

@Service
@Scope("prototype")
@NoArgsConstructor
public class CompanyService extends ClientService {

	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomersCouponsRepository purchaseRepo;
	private Company comp;


	public void setComp(String email) {
		this.comp = compRepo.findByEmail(email);
	}

	/**
	 * Add new coupon to DB after making sure that there is no other coupon with the
	 * same name for this company
	 * 
	 * @param coupon
	 * @throws SQLException
	 * @throws CouponExistException
	 * @throws CantActivateException 
	 */
	public void addCoupon(Coupon coupon) throws SQLException, CouponExistException, CantActivateException {
		coupon.setCompany(comp);
		if(coupon.getEndDate().before(new Date()))
			throw new CantActivateException();
		if (coupRepo.findByCompanyIdAndTitle(comp.getId(), coupon.getTitle()) == null) {
			coupon.setActive(true);
			coupRepo.save(coupon);
		} else {
			throw new CouponExistException();
		}
	}

	/**
	 * Update coupon after making sure that this company already have this coupon
	 * and if the name of the current coupon changes after the update, the new name
	 * needs to be unique for the company's coupons.
	 * 
	 * @param coupon
	 * @throws SQLException
	 * @throws CouponNameExistException
	 * @throws CouponNotExistException
	 * @throws CantActivateException 
	 */
	public void updateCoupon(Coupon coupon) throws SQLException, CouponNotExistException, CouponNameExistException, CantActivateException {
		if(coupon.getEndDate().before(new Date()))
			throw new CantActivateException();
		Coupon oldCoup = coupRepo.findByIdAndCompany(coupon.getId(), comp);
		if (oldCoup == null) {
			throw new CouponNotExistException();
		}
		Coupon c1 = coupRepo.findByCompanyIdAndTitle(comp.getId(), coupon.getTitle());
		if (c1 != null) {
			if (c1.getId() != coupon.getId())
				throw new CouponNameExistException();
		}
		coupon.setPurchases(oldCoup.getPurchases());
		coupRepo.save(coupon);

	}

	/**
	 * 
	 * @param couponId
	 * @throws SQLException
	 */
	public void deleteCoupon(long couponId) throws SQLException, EmptyResultDataAccessException {
		coupRepo.deleteById(couponId);
	}
	


	/**
	 * Method is getting ALL coupons, then returning on another list all the coupons
	 * with the same company id as this company.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Coupon> getAllCompanyCoupons() throws SQLException {
		return coupRepo.findAllByCompanyId(comp.getId());
	}

	/**
	 * Returns all company's coupons where price is higher or equals to the coupon's
	 * price. Could've called getAllCompanyCoupons and not check the company id in
	 * this method but it doesn't matter.
	 * 
	 * @param price
	 * @return
	 * @throws SQLException
	 */
	public List<Coupon> getAllCompanyCouponsByPrice(double price) throws SQLException {
		return coupRepo.findAllByPriceGreaterThanEqualAndCompanyId(price, comp.getId());
	}

	/**
	 * Returns all company's coupons where coupon's category is equal to sent
	 * category.
	 * 
	 * @param category
	 * @return list of coupons by category
	 * @throws SQLException
	 */
	public List<Coupon> getAllCompanyCouponsByCategory(CategoryType category) throws SQLException {
		return coupRepo.findAllByCompanyIdAndType(comp.getId(), category);
	}

	/**
	 * Get company by id
	 * 
	 * @return company
	 * @throws SQLException
	 * @throws CompanyNotExistException
	 */
	public Company getCompanyDetails() throws SQLException, CompanyNotExistException {
		return compRepo.findById(comp.getId()).orElseThrow(() -> new CompanyNotExistException());
	}

	/**
	 * 
	 * @param couponId
	 * @throws CouponNotExistException
	 */
	public void deactivateCoupon(long couponId) throws CouponNotExistException {
		Coupon coupon = coupRepo.findById(couponId).orElseThrow(() -> new CouponNotExistException());
		if(coupon.getCompany().getId()!= comp.getId()) {
			throw new CouponNotExistException();
		}
		coupon.setActive(false);
		Set<CustomersCoupons> couponPurchases = purchaseRepo.findAllByCouponIdAndIsActive(couponId, true);
		for (CustomersCoupons purchase : couponPurchases) {
			purchase.setActive(false);
		}
		purchaseRepo.saveAll(couponPurchases);
		coupRepo.save(coupon);

	}
	
	public void activateCoupon(long couponId) throws CouponNotExistException, CantActivateException {
		Coupon coupon = coupRepo.findById(couponId).orElseThrow(() -> new CouponNotExistException());
		if(coupon.getCompany().getId()!= comp.getId()) {
			throw new CouponNotExistException();
		}
		if(coupon.getEndDate().before(new Date()))
			throw new CantActivateException();
		coupon.setActive(true);
		Set<CustomersCoupons> couponPurchases = purchaseRepo.findAllByCouponIdAndIsActive(couponId, false);
		for (CustomersCoupons purchase : couponPurchases) {
			purchase.setActive(true);
		}
		purchaseRepo.saveAll(couponPurchases);
		coupRepo.save(coupon);
	}

}
