package com.StudentGrader.Config;

import com.StudentGrader.Entity.Admin;
import com.StudentGrader.Repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(AdminRepository adminRepo, PasswordEncoder encoder) {
        return args -> {
            String email = "nandkishorhiwale321@gmail.com";
            if (adminRepo.findByEmail(email).isEmpty()) {
                Admin admin = new Admin();
                admin.setEmail(email);
                admin.setPassword(encoder.encode("Uniquesystem@123"));
                adminRepo.save(admin);
                System.out.println("Default admin created: " + email);
            }
        };
    }
}
