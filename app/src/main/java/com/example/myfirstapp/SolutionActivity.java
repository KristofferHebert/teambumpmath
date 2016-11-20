package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SolutionActivity extends AppCompatActivity {

    private Problem problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        Intent intent = getIntent();
        int problemId = intent.getIntExtra(QuestionActivity.QUESTION_ID, QuestionActivity.NO_QUESTION_EXISTS);
        problem = Problems.get(problemId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView answerText = (TextView) findViewById(R.id.answer_text);
        answerText.setText(problem.getAnswer());
        answerText.setTextSize(25);

        TextView questionView = (TextView) findViewById(R.id.question_text);
        questionView.setText(problem.getQuestion());
        questionView.setTextSize(25);

        TextView explainationView = (TextView) findViewById(R.id.explaination_text);
        explainationView.setText(problem.getIncorrectAnswerFeedback());
        explainationView.setTextSize(25);



        //this is now on the notifications - do we want to keep that interaction inside the activities?
//        TextView feelingOfKnowledgeView = (TextView) findViewById(R.id.feeling_of_knowledge_message);
//        feelingOfKnowledgeView.setText(getString(R.string.feelingOfConfidence));
//        feelingOfKnowledgeView.setTextSize(25);
    }
}
