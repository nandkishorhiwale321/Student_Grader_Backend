package com.StudentGrader.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StudentGrader.Entity.Student;

public interface Studentrepo extends JpaRepository<Student, Integer> {
	
	Optional<Student> findByEmail(String email);
}
