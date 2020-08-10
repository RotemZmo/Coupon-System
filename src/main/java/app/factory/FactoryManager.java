package app.factory;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import app.entities.CategoryType;
import app.entities.Company;
import app.entities.Coupon;
import app.entities.Customer;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class FactoryManager {
	Set<String> companyNames = new HashSet<String>();
	Set<String> companyEmails = new HashSet<String>();
	Set<String> customerEmails = new HashSet<String>();
	Set<String> couponNames = new HashSet<String>();

	public Company generateCompany() {
		String name = "Company " + (int) (Math.random() * 10000);
		String email = "company.mail" + (int) (Math.random() * 10000) + "@gmail.com";
		while (!companyNames.add(name)) {
			name = "Company " + (int) (Math.random() * 10000);
		}
		while (!companyEmails.add(email)) {
		email = "company.mail" + (int) (Math.random() * 10000) + "@gmail.com";
		}
		return new Company(name, email);
	}

	public Customer generateCustomer() {
		String firstName = "Customer " + (int) (Math.random() * 1000);
		String lastName = "Last " + (int) (Math.random() * 1000);
		String email = "customer.mail" + (int) (Math.random() * 10000) + "@gmail.com";
		while (!customerEmails.add(email)) {
			email = "customer.mail" + (int) (Math.random() * 10000) + "@gmail.com";
		}

		return new Customer(firstName, lastName, email);
	}

	public Coupon generateCoupon(Company company, CategoryType type) {
		String title = "Coupon" + (int) (Math.random() * 10000) + company.getName();
		while(!couponNames.add(title)) {
			title = "Coupon" + (int) (Math.random() * 10000) + company.getName();
		}
		String description = "test description";
		int amount = (int) (Math.random() * 1000);
		double price = Math.random() * 1000;
		Date startDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		Date endDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis() + (long) (Math.random() * 20000000));
		String image = "Image";

		return new Coupon(title, description, company, amount, price, startDate, endDate, image, type);
	}

}
