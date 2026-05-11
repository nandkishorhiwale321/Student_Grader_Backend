//package com.StudentGrader.Controller;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.StudentGrader.DTO.LoginRequest;
//import com.StudentGrader.DTO.LoginResponse;
//import com.StudentGrader.Entity.Student;
//import com.StudentGrader.Repository.Studentrepo;
//
//@RequestMapping("/students")
//@RestController
//@CrossOrigin(origins ="*")
//public class StudentContoller {
//	
//	 @Autowired
//	    private Studentrepo studentRepository;
//
//	 @PostMapping("/signup")
//	 public ResponseEntity<?> signup(@RequestBody Student student) {
//	     Optional<Student> existingStudent = studentRepository.findByEmail(student.getEmail());
//	     if (existingStudent.isPresent()) {
//	         return ResponseEntity.status(HttpStatus.CONFLICT)
//	                 .body("User with email " + student.getEmail() + " already exists.");
//	     }
//	     // Save new student (consider hashing password here)
//	     Student savedStudent = studentRepository.save(student);
//	     System.out.println(student.getEmail()+"registered");
//	     return ResponseEntity.ok(savedStudent);
//	 }
//
////	
//	 @PostMapping("/login")
//	 public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//	     Optional<Student> existingStudentOpt = studentRepository.findByEmail(loginRequest.getEmail());
//
//	     if (existingStudentOpt.isPresent()) {
//	         Student existingStudent = existingStudentOpt.get();
//
//	         if (existingStudent.getPassword().equals(loginRequest.getPassword())) {
//	             // Build response DTO without password
//	             LoginResponse response = new LoginResponse(
//	                 existingStudent.getName(),
//	                 existingStudent.getEmail(),
//	                 existingStudent.getFinalScore()  // null if not attempted
//	             );
//
//	             return ResponseEntity.ok(response);
//	         } else {
//	             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//	                     .body("Incorrect password");
//	         }
//	     } else {
//	         return ResponseEntity.status(HttpStatus.NOT_FOUND)
//	                 .body("User with email " + loginRequest.getEmail() + " not found.");
//	     }
//	 }
//	 
//
//
//	 @GetMapping("/score")
//	    public ResponseEntity<?> getFinalScore(@RequestParam String email) {
//	        Optional<Student> studentOpt = studentRepository.findByEmail(email);
//	        if (studentOpt.isPresent()) {
//	            Student student = studentOpt.get();
//	            Integer finalScore = student.getFinalScore();
//	            if (finalScore == null) {
//	                return ResponseEntity.ok("The student has not attempted the quiz yet.");
//	            }
//	            return ResponseEntity.ok(finalScore);
//	        } else {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//	                    .body("User with email " + email + " not found.");
//	        }
//	    }
//	}
//
//


package com.StudentGrader.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.StudentGrader.DTO.LoginRequest;
import com.StudentGrader.DTO.LoginResponse;
import com.StudentGrader.Entity.Student;
import com.StudentGrader.Repository.Studentrepo;
import com.StudentGrader.Service.MailSenderService;

import jakarta.mail.MessagingException;

@RequestMapping("/students")
@RestController
@CrossOrigin(origins = "*")
public class StudentContoller {

    @Autowired
    private Studentrepo studentRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Student student) throws MessagingException {
        Optional<Student> existingStudent = studentRepository.findByEmail(student.getEmail());
        if (existingStudent.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User with email " + student.getEmail() + " already exists.");
        }
        Student savedStudent = studentRepository.save(student);

        // Send welcome email after signup
        mailSenderService.sendWelcomeEmail(savedStudent.getEmail(), savedStudent.getName());

        System.out.println(student.getEmail() + " registered");
        return ResponseEntity.ok(savedStudent);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Student> existingStudentOpt = studentRepository.findByEmail(loginRequest.getEmail());

        if (existingStudentOpt.isPresent()) {
            Student existingStudent = existingStudentOpt.get();

            if (existingStudent.getPassword().equals(loginRequest.getPassword())) {
                LoginResponse response = new LoginResponse(
                        existingStudent.getName(),
                        existingStudent.getEmail(),
                        existingStudent.getFinalScore() // null if not attempted
                );

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Incorrect password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with email " + loginRequest.getEmail() + " not found.");
        }
    }

    @GetMapping("/score")
    public ResponseEntity<?> getFinalScore(@RequestParam String email) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            Integer finalScore = student.getFinalScore();
            if (finalScore == null) {
                return ResponseEntity.ok("The student has not attempted the quiz yet.");
            }
            return ResponseEntity.ok(finalScore);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with email " + email + " not found.");
        }
    }
}

