package com.StudentGrader.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StudentGrader.Entity.Question;
import com.StudentGrader.Repository.Questionrepo;

@Service
public class QuestionService {
	
	
	
	@Autowired
	private Questionrepo repo;
	
	
	
	public List<Question> getall()
	{
		return repo.findAll();
	}

}
