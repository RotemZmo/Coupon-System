package app.web;

import java.sql.SQLException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.CategoryType;
import app.entities.Coupon;
import app.exceptions.CouponExpiredException;
import app.exceptions.CouponNotExistException;
import app.exceptions.CouponOutOfStockException;
import app.exceptions.CouponOwnedException;
import app.exceptions.CustomerNotExistException;
import app.services.CustomerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("customer")
@Scope("request")
public class CustomerController {
		
	private CustomerService customerService;

	
	@Autowired
	public CustomerController(CustomerService customerService, HttpServletRequest request,SecretKey secretKey) {
		super();
		this.customerService = customerService;
		String jwt = request.getHeader("Authorization").substring(7);
		Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
		String email = claims.get("sub", String.class);
		this.customerService.setCust(email);
	}
	/*
	@Override
	public boolean login(String email, String password) throws SQLException, FailedLoginException {
		this.cust = custRepo.findByEmailAndPassword(email, password);
		if (cust != null) {
			return true;
		}
		throw new FailedLoginException();
	}
*/
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
	
	@PostMapping("buy/{couponID}")
	public ResponseEntity<?> buyCoupon(@PathVariable long couponID) {
		try {
			customerService.buyCoupon(couponID);
			return ResponseEntity.noContent().build();
		} catch (SQLException | CouponOwnedException | CouponOutOfStockException | CouponExpiredException
				e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (CouponNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		
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
	
	@GetMapping("all")
	public List<Coupon> getAllCustomerCoupons(){
		try {
			return customerService.getAllCustomerCoupons();
		} catch (SQLException e) {
			return null;
		}
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
	@GetMapping("byCategory/{category}")
	public List<Coupon> getAllCustomerCouponsByCategory(@PathVariable CategoryType category) {
		try {
			return customerService.getAllCustomerCouponsByCategory(category);
		} catch (SQLException e) {
			return null;
		}
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
	
	@GetMapping("byPrice/{price}")
	public List<Coupon> getAllCustomerCouponsByPrice(@PathVariable double price){
		try {
			return customerService.getAllCustomerCouponsByPrice(price);
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * 
	 * @return customer
	 * @throws SQLException
	 * @throws CustomerNotExistException 
	 */
	
	@GetMapping("details")
	public ResponseEntity<?> getCustomerDetails(){
		try {
			return ResponseEntity.ok(customerService.getCustomerDetails());
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (CustomerNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("home")
	public List<Coupon> getAllActiveCoupons(){
		try {
			return customerService.getAllAvailableCoupons();
		} catch (SQLException e) {
			return null;
		}
	}
	
}
