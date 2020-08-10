package app.web;

import java.sql.SQLException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.CategoryType;
import app.entities.Coupon;
import app.exceptions.CantActivateException;
import app.exceptions.CompanyNotExistException;
import app.exceptions.CouponExistException;
import app.exceptions.CouponNameExistException;
import app.exceptions.CouponNotExistException;
import app.services.CompanyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("company")
@Scope("request")
public class CompanyController {

	CompanyService compService;
    //private final HttpServletRequest request;
    //private final SecretKey secretKey;

    @Autowired
	public CompanyController(CompanyService compService, HttpServletRequest request,SecretKey secretKey) {
		super();
		//this.request = request; 
        //this.secretKey = secretKey;
		this.compService = compService;
		String jwt = request.getHeader("Authorization").substring(7);
		Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
		String email = claims.get("sub", String.class);
		this.compService.setComp(email);

	}

	@PostMapping("buyCoupon")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon) {
		try {
			System.out.println(coupon.getType());
			compService.addCoupon(coupon);
			return ResponseEntity.noContent().build();
		} catch (SQLException | CouponExistException | CantActivateException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
	 */

	@PutMapping("updateCoupon")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) {
		try {
			compService.updateCoupon(coupon);
			return ResponseEntity.noContent().build();
		} catch (SQLException | CouponNameExistException | CantActivateException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CouponNotExistException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * 
	 * @param couponId
	 * @throws SQLException
	 */

	@DeleteMapping("delete/{couponId}")
	public ResponseEntity<?> deleteCoupon(@PathVariable long couponId) {
		try {
			compService.deleteCoupon(couponId);
			return ResponseEntity.noContent().build();
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(new CouponNotExistException().getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method is getting ALL coupons, then returning on another list all the coupons
	 * with the same company id as this company.
	 * 
	 * @return
	 * @throws SQLException
	 */

	@GetMapping("all")
	public List<Coupon> getAllCompanyCoupons() {
		try {
			return compService.getAllCompanyCoupons();
		} catch (SQLException e) {
			return null;
		}
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

	@GetMapping("couponsByPrice/{price}")
	public List<Coupon> getAllCompanyCouponsByPrice(@PathVariable double price) {
		try {
			return compService.getAllCompanyCouponsByPrice(price);
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Returns all company's coupons where coupon's category is equal to sent
	 * category.
	 * 
	 * @param category
	 * @return list of coupons by category
	 * @throws SQLException
	 */

	@GetMapping("couponsByCat/{category}")
	public List<Coupon> getAllCompanyCouponsByCategory(@PathVariable CategoryType category) {
		try {
			return compService.getAllCompanyCouponsByCategory(category);
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Get company by id
	 * 
	 * @return company
	 * @throws SQLException
	 * @throws CompanyNotExistException
	 */
	
	@GetMapping("details")
	public ResponseEntity<?> getCompanyDetails(){
		try {
			return ResponseEntity.ok(compService.getCompanyDetails());
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (CompanyNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * @param couponId
	 * @throws CouponNotExistException
	 */
	
	@PutMapping("deactivateCoup/{couponId}")
	public ResponseEntity<?> deactivateCoupon(@PathVariable long couponId) {
		try {
			compService.deactivateCoupon(couponId);
			return ResponseEntity.noContent().build();
		} catch (CouponNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("activateCoupon/{couponId}")
	public ResponseEntity<?> activateCoupon(@PathVariable long couponId){
		try {
			compService.activateCoupon(couponId);
			return ResponseEntity.noContent().build();
		} catch (CouponNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (CantActivateException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}
}
