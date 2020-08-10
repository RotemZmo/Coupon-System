package app.db;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	Company findByEmail(String email);
	@Query("SELECT COUNT(id) FROM Company c")
	int getSumOfCompanies(); 
}
