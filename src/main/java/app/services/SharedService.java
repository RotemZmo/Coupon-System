package app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.db.CouponRepository;
import app.entities.Coupon;
import app.exceptions.CouponNotExistException;

@Service
@Scope("prototype")
public class SharedService {

	@Autowired
	private CouponRepository coupRepo;
	
	public Coupon findCouponById(long id) throws CouponNotExistException {
		return coupRepo.findById(id).orElseThrow(()-> new CouponNotExistException());
	}
	
}
