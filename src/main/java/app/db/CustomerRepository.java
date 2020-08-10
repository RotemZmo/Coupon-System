package app.db;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByEmail(String email);
	List<Customer> findAllByEmail(String email);
	@Query("SELECT COUNT(customer_id) FROM Customer c")
	int getSumOfCustomers(); 

}
