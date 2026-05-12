package com.StudentGrader.Loader;

import com.StudentGrader.Entity.Question;
import com.StudentGrader.Repository.Questionrepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class QuestionDataLoader implements CommandLineRunner {
	
	Logger log = LoggerFactory.getLogger(QuestionDataLoader.class);

    private final Questionrepo questionRepo;

    public QuestionDataLoader(Questionrepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Question>> typeReference = new TypeReference<>() {};
        InputStream inputStream = getClass().getResourceAsStream("/questions_full.json");
        List<Question> questions = mapper.readValue(inputStream, typeReference);
        questionRepo.saveAll(questions);
        log.info("Questions loaded into DB: " + questions.size());
       // System.out.println("Questions loaded into DB: " + questions.size());
    }
}

