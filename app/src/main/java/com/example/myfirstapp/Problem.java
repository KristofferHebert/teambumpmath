package com.example.myfirstapp;

/**
 * Created by toddfrisch on 10/23/16.
 */

public class Problem {

    private String question;

    private String answer;

    private String incorrectAnswerFeedback;

    public Problem(String question, String answer, String incorrectAnswerFeedback) {
        this.answer = answer;
        this.question = question;
        this.incorrectAnswerFeedback = incorrectAnswerFeedback;

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
