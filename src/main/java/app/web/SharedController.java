package app.web;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.exceptions.CouponNotExistException;
import app.services.SharedService;

@RestController
@RequestMapping("shared")
@Scope("request")
public class SharedController {
	
	private SharedService sharedService;

	public SharedController(SharedService sharedService) {
		super();
		this.sharedService = sharedService;
	}
	
	@GetMapping("coupon/{id}")
	public ResponseEntity<?> getCouponById(@PathVariable long id){
		try {
			return ResponseEntity.ok(sharedService.findCouponById(id));
		} catch (CouponNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	

}
