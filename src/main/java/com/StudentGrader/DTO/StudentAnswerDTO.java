package com.StudentGrader.DTO;

public class StudentAnswerDTO {
    private Long id;
    private String selectedAnswer;  // matches your entity's selectedAnswer field

    public StudentAnswerDTO() {}

    public StudentAnswerDTO(Long id, String selectedAnswer) {
        this.id = id;
        this.selectedAnswer = selectedAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
