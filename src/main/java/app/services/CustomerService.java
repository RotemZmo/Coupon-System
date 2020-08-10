package app.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.db.CompanyRepository;
import app.db.CouponRepository;
import app.db.CustomerRepository;
import app.db.CustomersCouponsRepository;
import app.entities.CategoryType;
import app.entities.Company;
import app.entities.Coupon;
import app.entities.Customer;
import app.entities.CustomersCoupons;
import app.exceptions.CouponExpiredException;
import app.exceptions.CouponNotExistException;
import app.exceptions.CouponOutOfStockException;
import app.exceptions.CouponOwnedException;
import app.exceptions.CustomerNotExistException;

@Service
@Scope("prototype")
public class CustomerService extends ClientService {
	private Customer cust;

	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomersCouponsRepository purchaseRepo;

	
	public void setCust(String email) {
		this.cust = custRepo.findByEmail(email);
	}

	/**
	 * Purchase coupon for this customer. Purchased coupon must have at least 1
	 * coupon in stock, not expired, and that the customer doesn't have the coupon
	 * already. Subtract 1 from coupon's amount if purchased successfully and update
	 * coupon in DB.
	 * 
	 * @param couponID
	 * @throws SQLException
	 * @throws CouponOwnedException
	 * @throws CouponOutOfStockException
	 * @throws CouponExpiredException
	 * @throws CouponNotExistException 
	 */
	public void buyCoupon(long couponID)
			throws SQLException, CouponOwnedException, CouponOutOfStockException, CouponExpiredException, CouponNotExistException {
		Coupon coup = coupRepo.findById(couponID).orElseThrow(CouponNotExistException::new);
		if (purchaseRepo.findByCustomerIdAndCouponId(cust.getId(), couponID) != null) {
			if (purchaseRepo.findByCustomerIdAndCouponId(cust.getId(), couponID).isActive()) {
				throw new CouponOwnedException();
			}
		}
		if (!coup.isActive())
			throw new CouponExpiredException();
		if (coup.getAmount() < 1)
			throw new CouponOutOfStockException();
		coup.setAmount(coup.getAmount() - 1);
		coupRepo.save(coup);
		Company comp = coup.getCompany();
		cust.getPurchases().add(purchaseRepo.save(new CustomersCoupons(cust, coup, comp.getFee())));
		custRepo.save(cust);
		comp.setTotalIncome(comp.getTotalIncome() + (coup.getPrice() * (1 - comp.getFee())));
		compRepo.save(comp);
	}

	/**
	 * Return all customer's coupons by getting all customer purchases. Customer
	 * purchases list contains purchased coupon's ID and not coupon object therefore
	 * we create another list for coupon object and fill it by getting each coupon
	 * object by ID.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Coupon> getAllCustomerCoupons() throws SQLException {
		List<Coupon> custCoups = new ArrayList<Coupon>();
		for (CustomersCoupons purchase : cust.getPurchases()) {
			custCoups.add(purchase.getCoupon());
		}
		return custCoups;
	}

	/**
	 * Return all customer's coupons by getting all customer purchases by category.
	 * First we get all customer's coupons then we loop through the coupon list,
	 * when method finds a coupon matching the category wanted the coupon is added
	 * to another list which then returned.
	 * 
	 * @param category
	 * @return list of coupons
	 * @throws SQLException
	 */
	public List<Coupon> getAllCustomerCouponsByCategory(CategoryType category) throws SQLException {
		ArrayList<Coupon> allCustCoups = new ArrayList<Coupon>();
		ArrayList<Coupon> custCoupsByCategory = new ArrayList<Coupon>();
		allCustCoups = (ArrayList<Coupon>) getAllCustomerCoupons();
		for (Coupon coupon : allCustCoups) {
			if (coupon.getType() == category)
				custCoupsByCategory.add(coupon);
		}
		return custCoupsByCategory;
	}

	/**
	 * Return all customer's coupons by getting all customer purchases by price.
	 * First we get all customer's coupons then we loop through the coupon list,
	 * when method finds a coupon matching the category wanted the coupon is added
	 * to another list which then returned.
	 * 
	 * @param price
	 * @return list of coupons
	 * @throws SQLException
	 */
	public List<Coupon> getAllCustomerCouponsByPrice(double price) throws SQLException {
		ArrayList<Coupon> allCustCoups = new ArrayList<Coupon>();
		ArrayList<Coupon> custCoupsByPrice = new ArrayList<Coupon>();
		allCustCoups = (ArrayList<Coupon>) getAllCustomerCoupons();
		for (Coupon coupon : allCustCoups) {
			if (coupon.getPrice() <= price)
				custCoupsByPrice.add(coupon);
		}
		return custCoupsByPrice;
	}

	/**
	 * 
	 * @return customer
	 * @throws SQLException
	 * @throws CustomerNotExistException 
	 */
	public Customer getCustomerDetails() throws SQLException, CustomerNotExistException {
		return custRepo.findById(cust.getId()).orElseThrow(()-> new CustomerNotExistException() );
	}
	
	public List<Coupon> getAllAvailableCoupons() throws SQLException{
		return coupRepo.findAllByActive(true);
	}
}
