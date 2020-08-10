package app.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.entities.Admin;
import app.entities.ClientType;
import app.entities.Company;
import app.entities.Customer;
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
import app.services.AdminService;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	AdminService adService;

	public AdminController(AdminService adService) {
		this.adService = adService;
	}

	/**
	 * Add a new company after making sure it's unique
	 * 
	 * @param company
	 * @throws SQLException
	 * @throws CompanyExistException
	 */

	@PostMapping("add/company")
	public ResponseEntity<?> addCompany(@RequestBody Company company) {
		try {
			adService.addCompany(company);
			return ResponseEntity.noContent().build();
		} catch (SQLException | CompanyExistException | InvalidFeeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deletes a company after deleting it's coupon purchases and coupons.
	 * 
	 * @param companyId
	 * @throws SQLException
	 */

	@DeleteMapping("delete/company/{companyId}")
	public ResponseEntity<?> deleteCompany(@PathVariable int companyId) {
		try {
			adService.deleteCompany(companyId);
			return ResponseEntity.noContent().build();
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deactivate company, cascading to it's coupons and purchases
	 * 
	 * @param companyId
	 * @throws CompanyNotExistException
	 */

	@PutMapping("deactivate/company/{companyId}")
	public ResponseEntity<?> deactivateCompany(@PathVariable int companyId) {
		try {
			adService.deactivateCompany(companyId);
			return ResponseEntity.noContent().build();
		} catch (CompanyNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Updates company after making sure that such company exist.
	 * 
	 * @param company
	 * @throws SQLException
	 * @throws CompanyNotExistException
	 * @throws CompanyUpdateFailedException
	 */

	@PutMapping("update/company")
	public ResponseEntity<?> updateCompany(@RequestBody Company company) {
		try {
			adService.updateCompany(company);
			return ResponseEntity.noContent().build();
		} catch (SQLException | CompanyUpdateFailedException | InvalidFeeException | CompanyExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CompanyNotExistException | UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * @return List of all the companies
	 * @throws SQLException
	 */

	@GetMapping("get/companies")
	public List<Company> getAllCompanies() {
		try {
			return adService.getAllCompanies();
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param companyID
	 * @return company by id
	 * @throws SQLException
	 * @throws CompanyNotExistException
	 */

	@GetMapping("get/company/{companyId}")
	public ResponseEntity<?> getOneCompany(@PathVariable long companyId) {
		try {
			return ResponseEntity.ok(adService.getOneCompany(companyId));
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CompanyNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Add a new customer after validation of unique customer Email.
	 * 
	 * @param customer
	 * @throws SQLException
	 * @throws CustomerExistException
	 */

	@PostMapping("add/customer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
		try {
			adService.addCustomer(customer);
			return ResponseEntity.noContent().build();
		} catch (SQLException | CustomerExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Delete customer and all his purchases
	 * 
	 * @param cust
	 * @throws SQLException
	 * @throws CustomerNotExistException
	 */

	@DeleteMapping("delete/customer/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable long customerId) {
		try {
			adService.deleteCustomer(customerId);
			return ResponseEntity.noContent().build();
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CustomerNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Deactivate a customer and his purchases
	 * 
	 * @param customerId
	 * @throws CustomerNotExistException
	 */

	@PutMapping("deactivate/customer/{customerId}")
	public ResponseEntity<?> deactivateCustomer(@PathVariable long customerId) {
		try {
			adService.deactivateCustomer(customerId);
			return ResponseEntity.noContent().build();
		} catch (CustomerNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Update customer after making sure that such customer exist in DB and that the
	 * Email is unique.
	 * 
	 * @param customer
	 * @throws SQLException
	 * @throws CustomerNotExistException
	 * @throws CustomerEmailExistException
	 */

	@PutMapping("update/customer")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
		try {
			adService.updateCustomer(customer);
			return ResponseEntity.noContent().build();
		} catch (SQLException | CustomerEmailExistException | CustomerExistException | UserExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CustomerNotExistException | UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * @return List of all customers
	 * @throws SQLException
	 */

	@GetMapping("get/customers")
	public List<Customer> getAllCustomer() {
		try {
			return adService.getAllCustomer();
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param customerID
	 * @return customer by ID
	 * @throws SQLException
	 * @throws CustomerNotExistException
	 */

	@GetMapping("get/customer/{customerId}")
	public ResponseEntity<?> getOneCustomer(@PathVariable long customerId) {
		try {
			return ResponseEntity.ok(adService.getOneCustomer(customerId));
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CustomerNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Set new fee after checking fee is valid(fee<1)
	 * 
	 * @param companyId
	 * @param newFee
	 * @throws InvalidFeeException
	 * @throws CompanyNotExistException
	 */

	@PutMapping("setFee/{companyId}&{newFee}")
	public ResponseEntity<?> setFee(@PathVariable int companyId, @PathVariable double newFee) {
		try {
			adService.setFee(companyId, newFee);
			return ResponseEntity.noContent().build();
		} catch (InvalidFeeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CompanyNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id) {
		try {
			adService.deleteUser(id);
			return ResponseEntity.noContent().build();
		} catch (UserNotFoundException | CustomerNotExistException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("addUser/user")
	public ResponseEntity<?> addUser(@RequestBody ObjectNode objNode) {
		ObjectMapper mapper = new ObjectMapper();
		String password = objNode.get("password").asText();
		ClientType clientType = ClientType.valueOf(objNode.get("clientType").asText());
		Users user = null;
		try {
			switch (clientType) {
			case Company:
				user = mapper.readValue(objNode.traverse(), Company.class);
				break;
			case Customer:
				user = mapper.readValue(objNode.traverse(), Customer.class);
				break;
			case Admin:
				user = mapper.readValue(objNode.traverse(), Admin.class);
				break;
			default:
				return ResponseEntity.badRequest().build();
			}
		} catch (IOException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		try {
			return ResponseEntity.ok(adService.addUser(user, password, clientType));
		} catch (SQLException | CompanyExistException | CustomerExistException | AdminExistException|PropertyValueException | InvalidFeeException | UserExistException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}
	/*
	@PostMapping("addUser/company")
	public ResponseEntity<?> addCompanyUser(@RequestBody ObjectNode objNode) {
		try {
			String password = objNode.get("password").asText();
			ObjectMapper mapper = new ObjectMapper();
			Users user = mapper.readValue(objNode.traverse(), Company.class);
			return ResponseEntity.ok(adService.addUser(user, password, ClientType.Company));
		} catch (SQLException | CompanyExistException | CustomerExistException | AdminExistException | IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("addUser/admin")
	public ResponseEntity<?> addAdminUser(@RequestBody ObjectNode objNode) {
		try {
			String password = objNode.get("password").asText();
			ObjectMapper mapper = new ObjectMapper();
			Users user = mapper.readValue(objNode.traverse(), Admin.class);
			return ResponseEntity.ok(adService.addUser(user, password, ClientType.Admin));
		} catch (SQLException | CompanyExistException | CustomerExistException | AdminExistException | IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("addUser/customer")
	public ResponseEntity<?> addCustomerUser(@RequestBody ObjectNode objNode) {
		try {
			String password = objNode.get("password").asText();
			ObjectMapper mapper = new ObjectMapper();
			Users user = mapper.readValue(objNode.traverse(), Customer.class);
			return ResponseEntity.ok(adService.addUser(user, password, ClientType.Customer));
		} catch (SQLException | CompanyExistException | CustomerExistException | AdminExistException | IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
*/
	@PutMapping("activate/{id}")
	public ResponseEntity<?> activateUser(@PathVariable long id) {
		try {
			adService.activateUser(id);
			return ResponseEntity.noContent().build();
		} catch (CompanyNotExistException | CustomerNotExistException | AdminNotExistException
				| UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("deactivate/{id}")
	public ResponseEntity<?> deactivateUser(@PathVariable long id) {
		try {
			adService.deactivateUser(id);
			return ResponseEntity.noContent().build();
		} catch (CompanyNotExistException | CustomerNotExistException | AdminNotExistException
				| UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("get/user/{id}")
	public ResponseEntity<?> getOneUser(@PathVariable long id) {
		try {
			return ResponseEntity.ok(adService.getOneUser(id));
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("get/users")
	public List<User> getAllUsers() {
		return adService.getAllUsers();
	}

}
