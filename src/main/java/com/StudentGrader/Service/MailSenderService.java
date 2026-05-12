//package com.StudentGrader.Service;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MailSenderService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
package com.StudentGrader.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
	
	Logger log = LoggerFactory.getLogger(MailSenderService.class);

    @Autowired
    private JavaMailSender mailSender;

    // Sending welcome email after signup (HTML)
    public void sendWelcomeEmail(String toEmail, String studentName) throws MessagingException {
        String subject = "Welcome to StudentGrader!";
        String body = buildWelcomeEmailHtml(studentName);
        sendHtmlEmail(toEmail, subject, body);
    }

    // Send quiz completion email with final score (HTML)
    public void sendQuizCompletionEmail(String toEmail, String studentName, int finalScore) throws MessagingException {
        String subject = "Quiz Completed! Your Final Score";
        String body = buildQuizCompletionEmailHtml(studentName, finalScore);
        sendHtmlEmail(toEmail, subject, body);
    }

    // Utility method to send HTML email
    private void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException {
    	log.info("Sending email to: " + toEmail + " with subject: " + subject);
      //  System.out.println("Sending email to: " + toEmail + " with subject: " + subject);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);  // true = isHtml
        //helper.setFrom("maheshgavale07@gmail.com"); 
        helper.setFrom("nandkishorhiwale321@gmail.com");

        mailSender.send(message);

        log.info("Email sent successfully to: " + toEmail);
       // System.out.println("Email sent successfully to: " + toEmail);
    }

   //HTML build for welcome Email.
    private String buildWelcomeEmailHtml(String studentName) {
    	return "<html>" +
    		       "<body style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background:#f4f6f8; margin:0; padding:20px;\">" +
    		       "  <div style=\"max-width: 600px; background: #ffffff; margin: 40px auto; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); color: #333;\">" +
    		       "    <h2 style=\"color:#2E86C1; margin-top: 0;\">Welcome to StudentGrader, " + studentName + "!</h2>" +
    		       "    <p style=\"font-size: 1.1em;\">Thank you for signing up at <strong>StudentGrader</strong>. We're excited to have you onboard.</p>" +
    		       "    <p style=\"font-size: 1em; margin-top: 20px;\">Get ready to challenge yourself and improve your skills!</p>" +
    		       "    <hr style=\"border:none; border-top:1px solid #eee; margin: 30px 0;\"/>" +
    		       "    <p style=\"font-size: 0.85em; color: #666; text-align: center;\">" +
    		       "      Developed by <a href=\"mailto:nandkishorhiwale321@gmail.com\" style=\"color:#2E86C1; text-decoration:none;\">nandkishorhiwale321@gmail.com</a>" +
    		       "    </p>" +
    		       "  </div>" +
    		       "</body>" +
    		       "</html>";

    }

    // Build HTML for quiz completion email
    private String buildQuizCompletionEmailHtml(String studentName, int finalScore) {
    	return "<html>" +
    		       "<body style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background:#f4f6f8; margin:0; padding:20px;\">" +
    		       "  <div style=\"max-width: 600px; background: #ffffff; margin: 40px auto; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); color: #333;\">" +
    		       "    <h2 style=\"color:#27AE60; margin-top: 0;\">Congratulations, " + studentName + "!</h2>" +
    		       "    <p style=\"font-size: 1.1em;\">You have successfully completed the quiz.</p>" +
    		       "    <p style=\"font-size: 1.5em; font-weight: bold; color: #E67E22; margin: 20px 0;\">" +
    		       "      Your final score is: " + finalScore + " / 50" +
    		       "    </p>" +
    		       "    <p style=\"font-size: 1em;\">Keep up the great work and continue learning!</p>" +
    		       "    <hr style=\"border:none; border-top:1px solid #eee; margin: 30px 0;\"/>" +
    		       "    <p style=\"font-size: 0.85em; color: #666; text-align: center;\">" +
    		       "      Developed by <a href=\"mailto:nandkishorhiwale321@gmail.com\" style=\"color:#27AE60; text-decoration:none;\">nandkishorhiwale321@gmail.com</a>" +
    		       "    </p>" +
    		       "  </div>" +
    		       "</body>" +
    		       "</html>";

    }
}

//    // Send welcome email after signup (HTML)
//    public void sendWelcomeEmail(String toEmail, String studentName) {
//        String subject = "Welcome to StudentGrader!";
//        String body = buildWelcomeEmailHtml(studentName);
//        sendHtmlEmail(toEmail, subject, body);
//    }
//
//    // Send quiz completion email with final score (HTML)
//    public void sendQuizCompletionEmail(String toEmail, String studentName, int finalScore) {
//        String subject = "Quiz Completed! Your Final Score";
//        String body = buildQuizCompletionEmailHtml(studentName, finalScore);
//        sendHtmlEmail(toEmail, subject, body);
//    }
//
//    // Utility method to send HTML email
//    private void sendHtmlEmail(String toEmail, String subject, String htmlBody) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
//
//            helper.setTo(toEmail);
//            helper.setSubject(subject);
//            helper.setText(htmlBody, true);  // true = isHtml
//
//            mailSender.send(message);
//        } catch (MessagingException e) {
//            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
//        }
//    }
//
//    // Build HTML for welcome email
//    private String buildWelcomeEmailHtml(String studentName) {
//        return "<html>" +
//                "<body style='font-family:Arial,sans-serif; color:#333;'>" +
//                "<h2 style='color:#2E86C1;'>Welcome to StudentGrader, " + studentName + "!</h2>" +
//                "<p>Thank you for signing up at <strong>StudentGrader</strong>. We're excited to have you onboard.</p>" +
//                "<p>Get ready to challenge yourself and improve your skills!</p>" +
//                "<hr style='border:none; border-top:1px solid #eee;'/>" +
//                "<p style='font-size:0.9em; color:#666;'>Developed by <a href='mailto:maheshgavale087@gmail.com'>maheshgavale087@gmail.com</a></p>" +
//                "</body>" +
//                "</html>";
//    }
//
//    // Build HTML for quiz completion email
//    private String buildQuizCompletionEmailHtml(String studentName, int finalScore) {
//        return "<html>" +
//                "<body style='font-family:Arial,sans-serif; color:#333;'>" +
//                "<h2 style='color:#27AE60;'>Congratulations, " + studentName + "!</h2>" +
//                "<p>You have successfully completed the quiz.</p>" +
//                "<p>Your final score is: <strong style='font-size:1.5em; color:#E67E22;'>" + finalScore + "</strong></p>" +
//                "<p>Keep up the great work and continue learning!</p>" +
//                "<hr style='border:none; border-top:1px solid #eee;'/>" +
//                "<p style='font-size:0.9em; color:#666;'>Developed by <a href='mailto:maheshgavale087@gmail.com'>maheshgavale087@gmail.com</a></p>" +
//                "</body>" +
//                "</html>";
//    }
//}
