package com.StudentGrader.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StudentGrader.Entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	//Optional<Admin> findByUsername(String username);

	 Optional<Admin> findByEmail(String email);
	
}
