package app.db;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Collection<Role> findByName(String name);
	
}
