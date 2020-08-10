package app.db;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.ClientType;
import app.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmailAndPasswordAndClientType(String email, String password, ClientType clientType);
	public User findByEmail(String email);
}
