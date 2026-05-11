package com.StudentGrader.Controller;

import com.StudentGrader.DTO.StudentAnswerDTO;
import com.StudentGrader.DTO.StudentDTO;
import com.StudentGrader.Entity.Admin;
import com.StudentGrader.Entity.Student;
import com.StudentGrader.Repository.AdminRepository;
import com.StudentGrader.Repository.Studentrepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminRepository adminRepo;
    private final PasswordEncoder encoder;
    private final Studentrepo studentrepo;

    public AdminController(AdminRepository adminRepo, PasswordEncoder encoder,Studentrepo studentrepo) {
        this.adminRepo=adminRepo;
        this.encoder=encoder;
		this.studentrepo=studentrepo;
    }

    @PostMapping("/create")
    public String createAdmin(@RequestBody Admin admin) {
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminRepo.save(admin);
        return "Admin created successfully: " + admin.getEmail();
    }
        
        
//    @GetMapping("/get-all-students")
//    public List<StudentDTO> getAllStudents() {
//        List<Student> students = studentrepo.findAll();
//
//        return students.stream().map(student -> {
//            List<StudentAnswerDTO> answerDTOs = student.getAnswers().stream()
//                .map(ans -> new StudentAnswerDTO(ans.getId(), ans.getAnswerText()))  // adjust field as per your entity
//                .collect(Collectors.toList());
//
//            return new StudentDTO(
//                student.getId(),
//                student.getName(),
//                student.getEmail(),
//                student.getMobile(),
//                student.getBatch(),
//                student.getFinalScore(),
//                answerDTOs
//            );
//        }).collect(Collectors.toList());
//    }
//        
    @GetMapping("/get-all-students")
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentrepo.findAll();

        return students.stream().map(student -> {
            List<StudentAnswerDTO> answerDTOs = Optional.ofNullable(student.getAnswers())
                .orElse(Collections.emptyList())
                .stream()
                .map(ans -> new StudentAnswerDTO(ans.getId(), ans.getSelectedAnswer()))  // use getSelectedAnswer()
                .collect(Collectors.toList());

            return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getMobile(),
                student.getBatch(),
                student.getFinalScore(),
                answerDTOs
            );
        }).collect(Collectors.toList());
    }

        
    }

