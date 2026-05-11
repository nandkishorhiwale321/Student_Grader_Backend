//package com.StudentGrader.Controller;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import com.StudentGrader.DTO.QuestionDTO;
//import com.StudentGrader.Entity.Question;
//import com.StudentGrader.Entity.Student;
//import com.StudentGrader.Entity.StudentAnswer;
//import com.StudentGrader.Repository.Questionrepo;
//import com.StudentGrader.Repository.Studentanswerrepo;
//import com.StudentGrader.Repository.Studentrepo;
//
//@RestController
//@RequestMapping("/quiz")
//@CrossOrigin(origins = "*")
//public class QuizController {
//
//    @Autowired
//    private Questionrepo questionRepo;
//
//    @Autowired
//    private Studentrepo studentRepo;
//
//    @Autowired
//    private Studentanswerrepo studentAnswerRepo;
//
//    
//    @GetMapping("/start")
//    public List<QuestionDTO> startQuiz(@RequestParam String email) {
//        Student student = studentRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//      
//        boolean hasAttempted = studentAnswerRepo.existsByStudent(student);
//        if (hasAttempted) {
//            throw new RuntimeException("You have already attempted the quiz.");
//        }
//
//       
//        return questionRepo.findAllByOrderByIdAsc().stream()
//                .map(q -> new QuestionDTO(
//                        q.getId(),
//                        q.getQuestionText(),
//                        q.getOptionA(),
//                        q.getOptionB(),
//                        q.getOptionC(),
//                        q.getOptionD()
//                ))
//                .collect(Collectors.toList());
//    }
//
//  
////    @PostMapping("/submit")
////    public int submitQuiz(@RequestParam String email, @RequestBody List<StudentAnswer> answers) {
////        Student student = studentRepo.findByEmail(email)
////                .orElseThrow(() -> new RuntimeException("Student not found"));
////
////       
////        if (studentAnswerRepo.existsByStudent(student)) {
////            throw new RuntimeException("You have already submitted the quiz.");
////        }
////
////        int score = 0;
////        for (StudentAnswer ans : answers) {
////            ans.setStudent(student);
////            Question q = questionRepo.findById(ans.getQuestion().getId())
////                    .orElseThrow(() -> new RuntimeException("Question not found"));
////            ans.setQuestion(q);
////
////            if (ans.getSelectedAnswer().equalsIgnoreCase(q.getCorrectAnswer())) {
////                score++;
////            }
////              
////            System.out.println(score);
////            studentAnswerRepo.save(ans);
////        }
////        return score;
////    }
////    @PostMapping("/submit")
////    public int submitQuiz(@RequestParam String email, @RequestBody List<StudentAnswer> answers) {
////        Student student = studentRepo.findByEmail(email)
////                .orElseThrow(() -> new RuntimeException("Student not found"));
////
////        if (studentAnswerRepo.existsByStudent(student)) {
////            throw new RuntimeException("You have already submitted the quiz.");
////        }
////
////        int score = 0;
////        for (StudentAnswer ans : answers) {
////            ans.setStudent(student);
////            Question q = questionRepo.findById(ans.getQuestion().getId())
////                    .orElseThrow(() -> new RuntimeException("Question not found"));
////            ans.setQuestion(q);
////
////            String selected = ans.getSelectedAnswer() != null ? ans.getSelectedAnswer().trim() : "";
////            String correct = q.getCorrectAnswer() != null ? q.getCorrectAnswer().trim() : "";
////
////            System.out.println("Question ID: " + q.getId());
////            System.out.println("Selected Answer: '" + selected + "'");
////           // System.out.println("Correct Answer: '" + correct + "'");
////
////            if (selected.equalsIgnoreCase(correct)) {
////                score++;
////            }
////
////            studentAnswerRepo.save(ans);
////        }
////        System.out.println("Final Score: " + score);
////        return score;
////    }
//
//    @PostMapping("/submit")
//    public int submitQuiz(@RequestParam String email, @RequestBody List<StudentAnswer> answers) {
//        Student student = studentRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        if (studentAnswerRepo.existsByStudent(student)) {
//            throw new RuntimeException("You have already submitted the quiz.");
//        }
//
//        int score = 0;
//        for (StudentAnswer ans : answers) {
//            ans.setStudent(student);
//            Question q = questionRepo.findById(ans.getQuestion().getId())
//                    .orElseThrow(() -> new RuntimeException("Question not found"));
//            ans.setQuestion(q);
//
//            String selected = ans.getSelectedAnswer() != null ? ans.getSelectedAnswer().trim() : "";
//            String correct = q.getCorrectAnswer() != null ? q.getCorrectAnswer().trim() : "";
//
//            System.out.println("Question ID: " + q.getId());
//            System.out.println("Selected Answer: '" + selected + "'");
//
//            if (selected.equalsIgnoreCase(correct)) {
//                score++;
//            }
//
//            studentAnswerRepo.save(ans);
//        }
//
//        // Save the final score in the student entity
//        student.setFinalScore(score);
//        studentRepo.save(student);
//
//        System.out.println("Final Score: " + score);
//        return score;
//    }
//
//}



package com.StudentGrader.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.StudentGrader.DTO.QuestionDTO;
import com.StudentGrader.Entity.Question;
import com.StudentGrader.Entity.Student;
import com.StudentGrader.Entity.StudentAnswer;
import com.StudentGrader.Exceptions.QuizAlreadySubmittedException;
import com.StudentGrader.Repository.Questionrepo;
import com.StudentGrader.Repository.Studentanswerrepo;
import com.StudentGrader.Repository.Studentrepo;
import com.StudentGrader.Service.MailSenderService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins="*")
public class QuizController {

    @Autowired
    private Questionrepo questionRepo;

    @Autowired
    private Studentrepo studentRepo;

    @Autowired
    private Studentanswerrepo studentAnswerRepo;

    @Autowired
    private MailSenderService mailSenderService;  // inject mail sender service

    @GetMapping("/start")
    public List<QuestionDTO> startQuiz(@RequestParam String email) {
        Student student = studentRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        boolean hasAttempted = studentAnswerRepo.existsByStudent(student);
        if (hasAttempted) {
            throw new RuntimeException("You have already attempted the quiz.");
        }

        return questionRepo.findAllByOrderByIdAsc().stream()
                .map(q -> new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping("/submit")
    public int submitQuiz(@RequestParam String email, @RequestBody List<StudentAnswer> answers) throws MessagingException, QuizAlreadySubmittedException {
        Student student = studentRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (studentAnswerRepo.existsByStudent(student)) {
            throw new QuizAlreadySubmittedException("You have already submitted the quiz.");
        }

        int score = 0;
        for (StudentAnswer ans : answers) {
            ans.setStudent(student);
            Question q = questionRepo.findById(ans.getQuestion().getId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            ans.setQuestion(q);

            String selected = ans.getSelectedAnswer() != null ? ans.getSelectedAnswer().trim() : "";
            String correct = q.getCorrectAnswer() != null ? q.getCorrectAnswer().trim() : "";

            if (selected.equalsIgnoreCase(correct)) {
                score++;
            }

            studentAnswerRepo.save(ans);
        }

        // Save the final score in the student entity
        student.setFinalScore(score);
        studentRepo.save(student);

        // Send quiz completion email
        System.out.println("sending the completion score email....");
        mailSenderService.sendQuizCompletionEmail(student.getEmail(), student.getName(), score);

        System.out.println("Final Score: " + score);
        return score;
    }
}
