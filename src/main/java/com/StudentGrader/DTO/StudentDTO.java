package com.StudentGrader.DTO;

import java.util.List;

public class StudentDTO {
    private int id;
    private String name;
    private String email;
    private String mobile;
    private String batch;
    private Integer finalScore;
    private List<StudentAnswerDTO> answers;

    public StudentDTO() {}

    public StudentDTO(int id, String name, String email, String mobile, String batch, Integer finalScore, List<StudentAnswerDTO> answers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.batch = batch;
        this.finalScore = finalScore;
        this.answers = answers;
    }

    // Getters and Setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Integer finalScore) {
        this.finalScore = finalScore;
    }

    public List<StudentAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswerDTO> answers) {
        this.answers = answers;
    }
}
