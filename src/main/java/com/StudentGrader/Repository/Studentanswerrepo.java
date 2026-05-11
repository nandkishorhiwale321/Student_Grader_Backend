package com.StudentGrader.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StudentGrader.Entity.Student;
import com.StudentGrader.Entity.StudentAnswer;

public interface Studentanswerrepo extends JpaRepository<StudentAnswer, Integer> {
	 boolean existsByStudent(Student student);
}
