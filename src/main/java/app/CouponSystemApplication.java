package app;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import app.db.RoleRepository;
import app.entities.Admin;
import app.entities.ClientType;
import app.entities.Role;
import app.exceptions.AdminExistException;
import app.exceptions.CompanyExistException;
import app.exceptions.CustomerExistException;
import app.exceptions.InvalidFeeException;
import app.exceptions.UserExistException;
import app.services.AdminService;

@SpringBootApplication
public class CouponSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CouponSystemApplication.class, args);
		AdminService adS = ctx.getBean(AdminService.class);
		//build roles and 1st admin 
		RoleRepository rr = ctx.getBean(RoleRepository.class);
		if (rr.findAll().size() < 2) {
			rr.save(new Role("Admin"));
			rr.save(new Role("Customer"));
			rr.save(new Role("Company"));
			try {
				adS.addUser(new Admin("admin@admin.com"), "admin", ClientType.Admin);
			} catch (SQLException | CompanyExistException | CustomerExistException | AdminExistException | InvalidFeeException | UserExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
