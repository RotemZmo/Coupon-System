package app.services;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.db.AdminRepository;
import app.db.CompanyRepository;
import app.db.CouponRepository;
import app.db.CustomerRepository;
import app.db.CustomersCouponsRepository;
import app.db.RoleRepository;
import app.db.UserRepository;
import app.entities.Admin;
import app.entities.ClientType;
import app.entities.Company;
import app.entities.Coupon;
import app.entities.Customer;
import app.entities.CustomersCoupons;
import app.entities.User;
import app.entities.Users;
import app.exceptions.AdminExistException;
import app.exceptions.AdminNotExistException;
import app.exceptions.CompanyExistException;
import app.exceptions.CompanyNotExistException;
import app.exceptions.CompanyUpdateFailedException;
import app.exceptions.CustomerEmailExistException;
import app.exceptions.CustomerExistException;
import app.exceptions.CustomerNotExistException;
import app.exceptions.InvalidFeeException;
import app.exceptions.UserExistException;
import app.exceptions.UserNotFoundException;
import lombok.NoArgsConstructor;

@Service
@Scope("prototype")
@NoArgsConstructor
public class AdminService extends ClientService {
	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomersCouponsRepository purchaseRepo;
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Admin addAdmin(Admin admin) throws AdminExistException {
		try {
			admin.setActive(true);
			return adminRepo.save(admin);
		} catch (DataIntegrityViolationException e) {
			throw new AdminExistException();
		}
	}

	public void deleteUser(long id) throws UserNotFoundException, SQLException, CustomerNotExistException {
		User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
		switch (user.getClientType()) {
		case Company:
			deleteCompany(id);
			break;
		case Customer:
			deleteCustomer(id);
			break;
		case Admin:
			deleteAdmin(id);
			break;
		}
		userRepository.deleteById(id);
	}
	
	public void deactivateUser(long id) throws UserNotFoundException, CompanyNotExistException, CustomerNotExistException, AdminNotExistException {
		User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
		switch (user.getClientType()) {
		case Company:
			deactivateCompany(id);;
			break;
		case Customer:
			deactivateCustomer(id);
			break;
		case Admin:
			deactivateAdmin(id);
			break;
		}
		user.setEnabled(false);
		userRepository.save(user);
	}
	
	public void activateUser(long id) throws CompanyNotExistException, CustomerNotExistException, AdminNotExistException, UserNotFoundException {
		User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
		switch (user.getClientType()) {
		case Company:
			activateCompany(id);
			break;
		case Customer:
			activateCustomer(id);
			break;
		case Admin:
			activateAdmin(id);
			break;
		}
		user.setEnabled(true);
		userRepository.save(user);

	}
	
	public User addUser(Users user, String password, ClientType clientType) throws SQLException, CompanyExistException,
	CustomerExistException, AdminExistException, InvalidFeeException, UserExistException {
		switch (clientType) {
		case Company:
			Company comp = (Company) user;
			if(userRepository.findByEmail(comp.getEmail())!=null)
				throw new UserExistException();
			comp = addCompany(comp);
			return userRepository.save(new User(comp.getId(),comp.getEmail(),passwordEncoder.encode(password),clientType,
					roleRepository.findByName("Company")));
		case Customer:
			Customer cust = (Customer) user;
			if(userRepository.findByEmail(cust.getEmail())!=null)
				throw new UserExistException();
			cust = addCustomer(cust);
			return userRepository.save(new User(cust.getId(),cust.getEmail(),passwordEncoder.encode(password),clientType,
					roleRepository.findByName("Customer")));
		case Admin:
			Admin admin = (Admin) user;
			if(userRepository.findByEmail(admin.getEmail())!=null)
				throw new UserExistException();
			admin = addAdmin(admin);
			return userRepository.save(new User(admin.getId(),admin.getEmail(),passwordEncoder.encode(password),clientType,
					roleRepository.findByName("Admin")));
		default: 
			return null;
		}
	}

	public void deleteAdmin(long id) throws SQLException {
		adminRepo.deleteById(id);
	}

	public void deactivateAdmin(long id) throws AdminNotExistException {
		Admin admin = adminRepo.findById(id).orElseThrow(AdminNotExistException::new);
		admin.setActive(false);
		adminRepo.save(admin);
	}

	public void activateAdmin(long id) throws AdminNotExistException {
		Admin admin = adminRepo.findById(id).orElseThrow(AdminNotExistException::new);
		admin.setActive(true);
		adminRepo.save(admin);
	}

	/**
	 * Add a new company after making sure it's unique
	 * 
	 * @param company
	 * @throws SQLException
	 * @throws CompanyExistException
	 * @throws InvalidFeeException 
	 */
	public Company addCompany(Company company) throws SQLException, CompanyExistException, SQLIntegrityConstraintViolationException, InvalidFeeException {
		if(company.getFee()>=1 || company.getFee()<0)
			throw new InvalidFeeException();
		try {
			company.setActive(true);
			return compRepo.save(company);
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			if (compRepo.findByEmail(company.getEmail()) != null)
				throw new CompanyExistException("email");
			throw new CompanyExistException("name");
		}
	}

	/**
	 * Deletes a company after deleting it's coupon purchases and coupons.
	 * 
	 * @param companyId
	 * @throws SQLException
	 */
	public void deleteCompany(long companyId) throws SQLException {
		Iterator<Coupon> it = coupRepo.findAllByCompanyId(companyId).iterator();
		while (it.hasNext()) {
			purchaseRepo.deleteInBatch(it.next().getPurchases());
		}
		compRepo.deleteById(companyId);
	}

	/**
	 * Deactivate company, cascading to it's coupons and purchases
	 * 
	 * @param companyId
	 * @throws CompanyNotExistException
	 */
	public void deactivateCompany(long companyId) throws CompanyNotExistException {
		Company company = compRepo.findById(companyId).orElseThrow(() -> new CompanyNotExistException());
		Set<Coupon> companyCoups = company.getCoupons();
		for (Coupon coupon : companyCoups) {
			coupon.setActive(false);
			Set<CustomersCoupons> couponPurchases = purchaseRepo.findAllByCouponIdAndIsActive(coupon.getId(), true);
			for (CustomersCoupons purchase : couponPurchases) {
				purchaseRepo.save(purchase);
			}
			purchaseRepo.saveAll(couponPurchases);
		}
		coupRepo.saveAll(companyCoups);
		company.setActive(false);
		compRepo.save(company);
	}

	public void activateCompany(long companyId) throws CompanyNotExistException {
		Company company = compRepo.findById(companyId).orElseThrow(() -> new CompanyNotExistException());
		company.setActive(true);
		compRepo.save(company);
	}

	public void activateCustomer(long customerId) throws CustomerNotExistException {
		Customer customer = custRepo.findById(customerId).orElseThrow(() -> new CustomerNotExistException());
		customer.setActive(true);
		custRepo.save(customer);
	}

	/**
	 * Updates company after making sure that such company exist.
	 * 
	 * @param company
	 * @throws SQLException
	 * @throws CompanyNotExistException
	 * @throws CompanyUpdateFailedException
	 * @throws UserNotFoundException 
	 * @throws InvalidFeeException 
	 * @throws CompanyExistException 
	 */
	public void updateCompany(Company company)
			throws SQLException, CompanyNotExistException, CompanyUpdateFailedException, UserNotFoundException, InvalidFeeException, CompanyExistException {
		Company oldCompData = compRepo.findById(company.getId()).orElseThrow(CompanyNotExistException::new);
		if (oldCompData.getName().equals(company.getName()) && company.getEmail()!="") {
			if(oldCompData.getEmail()!=company.getEmail()) {
				User user = userRepository.findById(company.getId()).orElseThrow(() -> new UserNotFoundException());
				user.setEmail(company.getEmail());
				userRepository.save(user);
			}
			oldCompData.setEmail(company.getEmail());
			if(company.getFee()>=1 || company.getFee()<0)
				throw new InvalidFeeException();
			oldCompData.setFee(company.getFee());
			try {
			compRepo.save(oldCompData);
			} catch (DataIntegrityViolationException | ConstraintViolationException e) {
				if (compRepo.findByEmail(oldCompData.getEmail()) != null)
					throw new CompanyExistException("email");
				throw new CompanyExistException("name");
			}
		}
		else
			throw new CompanyUpdateFailedException();
	}

	/**
	 * 
	 * @return List of all the companies
	 * @throws SQLException
	 */
	public List<Company> getAllCompanies() throws SQLException {
		return compRepo.findAll();
	}

	/**
	 * 
	 * @param companyID
	 * @return company by id
	 * @throws SQLException
	 * @throws CompanyNotExistException
	 */
	public Company getOneCompany(long companyID) throws SQLException, CompanyNotExistException {
		return compRepo.findById(companyID).orElseThrow(CompanyNotExistException::new);
	}

	/**
	 * Add a new customer after validation of unique customer Email.
	 * 
	 * @param customer
	 * @throws SQLException
	 * @throws CustomerExistException
	 */
	public Customer addCustomer(Customer customer) throws SQLException, CustomerExistException {
		try {
			customer.setActive(true);
			return custRepo.save(customer);
		} catch (DataIntegrityViolationException e) {
			throw new CustomerExistException();
		}
	}

	/**
	 * Delete customer and all his purchases
	 * 
	 * @param cust
	 * @throws SQLException
	 * @throws CustomerNotExistException
	 */
	public void deleteCustomer(long customerId) throws SQLException, CustomerNotExistException {
		Customer customer = custRepo.findById(customerId).orElseThrow(() -> new CustomerNotExistException());
		purchaseRepo.deleteInBatch(customer.getPurchases());
		custRepo.deleteById(customer.getId());
	}

	/**
	 * Deactivate a customer and his purchases
	 * 
	 * @param customerId
	 * @throws CustomerNotExistException
	 */
	public void deactivateCustomer(long customerId) throws CustomerNotExistException {
		Customer customer = custRepo.findById(customerId).orElseThrow(() -> new CustomerNotExistException());
		Set<CustomersCoupons> customerPurchases = purchaseRepo.findAllByCouponIdAndIsActive(customerId, true);
		for (CustomersCoupons purchase : customerPurchases) {
			purchase.setActive(false);
		}
		customer.setActive(false);
		purchaseRepo.saveAll(customerPurchases);
		custRepo.save(customer);
	}

	/**
	 * Update customer after making sure that such customer exist in DB and that the
	 * Email is unique.
	 * 
	 * @param customer
	 * @throws SQLException
	 * @throws CustomerNotExistException
	 * @throws CustomerEmailExistException
	 * @throws UserNotFoundException 
	 * @throws CustomerExistException 
	 * @throws UserExistException 
	 */
	public void updateCustomer(Customer customer)
			throws SQLException, CustomerNotExistException, CustomerEmailExistException, UserNotFoundException, CustomerExistException, UserExistException {
		Customer oldCustData = custRepo.findById(customer.getId()).orElse(null);
		if (oldCustData == null)
			throw new CustomerNotExistException();
		if(customer.getEmail()=="")
			throw new CustomerEmailExistException();
		List<Customer> customers = custRepo.findAllByEmail(customer.getEmail());
		for (Customer customer2 : customers) {
			if (customer2.getEmail().equals(customer.getEmail()) && customer2.getId() != customer.getId())
				throw new CustomerEmailExistException();
		}
		if(oldCustData.getEmail()!=customer.getEmail()) {
			User user = userRepository.findById(customer.getId()).orElseThrow(() -> new UserNotFoundException());
			user.setEmail(customer.getEmail());
			try {
			userRepository.save(user);
			} catch (DataIntegrityViolationException | ConstraintViolationException e) {
				throw new UserExistException();
			}
		}
		oldCustData.setEmail(customer.getEmail());
		oldCustData.setFirstName(customer.getFirstName());
		oldCustData.setLastName(customer.getLastName());
		try {
		custRepo.save(oldCustData);
		}catch (DataIntegrityViolationException | ConstraintViolationException e) {
			throw new CustomerExistException();
		}
		
	}

	/**
	 * 
	 * @return List of all customers
	 * @throws SQLException
	 */
	public List<Customer> getAllCustomer() throws SQLException {
		return custRepo.findAll();
	}

	/**
	 * 
	 * @param customerID
	 * @return customer by ID
	 * @throws SQLException
	 * @throws CustomerNotExistException
	 */
	public Customer getOneCustomer(long customerID) throws SQLException, CustomerNotExistException {
		return custRepo.findById(customerID).orElseThrow(() -> new CustomerNotExistException());
	}

	/**
	 * Set new fee after checking fee is valid(fee<1)
	 * 
	 * @param companyId
	 * @param newFee
	 * @throws InvalidFeeException
	 * @throws CompanyNotExistException
	 */
	public void setFee(long companyId, double newFee) throws InvalidFeeException, CompanyNotExistException {
		Company company = compRepo.findById(companyId).orElseThrow(() -> new CompanyNotExistException());
		if (newFee < 1  || company.getFee()<0) {
			company.setFee(newFee);
			compRepo.save(company);
		} else
			throw new InvalidFeeException();
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User getOneUser(long id) throws UserNotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
	}
	
	public User getUserByEmail(String email) throws UserNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}
}
