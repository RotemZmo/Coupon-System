package app.services;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.db.CompanyRepository;
import app.db.CouponRepository;
import app.db.CustomerRepository;
import app.db.CustomersCouponsRepository;
import lombok.NoArgsConstructor;

@Service
@Scope("prototype")
@NoArgsConstructor
public class AnalyticsService {
	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomersCouponsRepository purchaseRepo;

	public int getNumOfCustomers() {
		return custRepo.getSumOfCustomers();
	}

	public int getNumOfCompanies() {
		return compRepo.getSumOfCompanies();
	}
	
	public double getTotalIncome() {
		return purchaseRepo.sumAllFees();
	}
	
	public int getNumOfCoupons() {
		return coupRepo.getSumOfCoupons();
	}
	
	public double getTotalIncomeBetweenDates(Date startDate, Date endDate) {
		return purchaseRepo.sumAllFeesBetweenDates(startDate, endDate);
	}
}
