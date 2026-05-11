package com.StudentGrader.DTO;

public class LoginResponse {
	 private String name;
	    private String email;
	    private Integer finalScore;  // null if quiz not attempted

	    public LoginResponse() {}

	    public LoginResponse(String name, String email, Integer finalScore) {
	        this.name = name;
	        this.email = email;
	        this.finalScore = finalScore;
	    }

	    // getters and setters
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

	    public Integer getFinalScore() {
	        return finalScore;
	    }
	    public void setFinalScore(Integer finalScore) {
	        this.finalScore = finalScore;
	    }
}
