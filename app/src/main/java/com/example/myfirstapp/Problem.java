package com.example.myfirstapp;

/**
 * Created by toddfrisch on 10/23/16.
 */

public class Problem {

    private int id;

    private String question;

    private String answer;

    private String incorrectAnswerFeedback;

    public Problem(int id, String question, String answer, String incorrectAnswerFeedback) {
        this.id = id;
        this.answer = answer;
        this.question = question;
        this.incorrectAnswerFeedback = incorrectAnswerFeedback;

    }

    public int getId(){
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getIncorrectAnswerFeedback() {
        return incorrectAnswerFeedback;
    }
}
