package com.StudentGrader.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailSenderService {

    Logger log = LoggerFactory.getLogger(MailSenderService.class);

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

 

    // Sending welcome email after signup (HTML)
    public void sendWelcomeEmail(String toEmail, String studentName) throws IOException {
        String subject = "Welcome to StudentGrader!";
        String body = buildWelcomeEmailHtml(studentName);
        sendHtmlEmail(toEmail, subject, body);
    }

    // Send quiz completion email with final score (HTML)
    public void sendQuizCompletionEmail(String toEmail, String studentName, int finalScore) throws IOException {
        String subject = "Quiz Completed! Your Final Score";
        String body = buildQuizCompletionEmailHtml(studentName, finalScore);
        sendHtmlEmail(toEmail, subject, body);
    }

    // ✅ SendGrid Email Sender
    private void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws IOException {

        log.info("Sending email to: " + toEmail + " with subject: " + subject);

        Email from = new Email("nandkishorhiwale321@gmail.com");
        Email to = new Email(toEmail);

        Content content = new Content("text/html", htmlBody);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            log.info("Status Code: " + response.getStatusCode());
            log.info("Email sent successfully to: " + toEmail);

        } catch (IOException ex) {
            log.error("Error sending email: " + ex.getMessage());
            throw ex;
        }
    }

    // HTML build for welcome Email
    private String buildWelcomeEmailHtml(String studentName) {
        return "<html>" +
                "<body style=\"font-family: 'Segoe UI', sans-serif; background:#f4f6f8; padding:20px;\">" +
                "<div style=\"max-width:600px;background:#fff;margin:auto;padding:30px;border-radius:10px;\">" +
                "<h2 style=\"color:#2E86C1;\">Welcome to StudentGrader, " + studentName + "!</h2>" +
                "<p>Thank you for signing up.</p>" +
                "</div></body></html>";
    }

    // HTML for quiz completion
    private String buildQuizCompletionEmailHtml(String studentName, int finalScore) {
        return "<html>" +
                "<body style=\"font-family: 'Segoe UI', sans-serif; background:#f4f6f8; padding:20px;\">" +
                "<div style=\"max-width:600px;background:#fff;margin:auto;padding:30px;border-radius:10px;\">" +
                "<h2 style=\"color:#27AE60;\">Congratulations, " + studentName + "!</h2>" +
                "<p>Your final score is: <b>" + finalScore + "</b></p>" +
                "</div></body></html>";
    }
}