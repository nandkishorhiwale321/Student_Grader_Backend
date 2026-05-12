package com.StudentGrader.Config;

import com.StudentGrader.Entity.Admin;
import com.StudentGrader.Repository.AdminRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

	Logger log=LoggerFactory.getLogger(AdminInitializer.class);
	
    @Bean
    CommandLineRunner initAdmin(AdminRepository adminRepo, PasswordEncoder encoder) {
        return args -> {
            String email = "nandkishorhiwale321@gmail.com";
            if (adminRepo.findByEmail(email).isEmpty()) {
                Admin admin = new Admin();
                admin.setEmail(email);
                admin.setPassword(encoder.encode("Uniquesystem@123"));
                adminRepo.save(admin);
                
                log.info("Default admin created: "+email);
                
              // System.out.println("Default admin created: " + email);
            }
        };
    }
}
