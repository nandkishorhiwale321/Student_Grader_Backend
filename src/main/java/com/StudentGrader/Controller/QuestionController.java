package com.StudentGrader.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StudentGrader.Entity.Question;
import com.StudentGrader.Service.QuestionService;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins ="*")
public class QuestionController {
	
	
	
	@Autowired
	private  QuestionService service;
	
	@GetMapping("/get-all-questions")
	public ResponseEntity<List<Question>>  getallquestions()
	{
		 List<Question> questions = service.getall();

		    if (questions != null && !questions.isEmpty()) {
		        return ResponseEntity.ok(questions);
		    } else {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		    }
	}

}
