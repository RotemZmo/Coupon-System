package app.db;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
