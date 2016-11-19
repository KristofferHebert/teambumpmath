package com.example.myfirstapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.myfirstapp.RandomNumber.randInt;

public class QuestionActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private String feeling_of_knowledge_prompt = "Do you know this?";

    private Problem question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();


        question = Problems.randomQuestion();

        EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(null);

        TextView questionView = (TextView) findViewById(R.id.question_message);
        questionView.setText(question.getQuestion());
        questionView.setTextSize(25);

        TextView feelingOfKnowledgeView = (TextView) findViewById(R.id.feeling_of_knowledge_message);
        feelingOfKnowledgeView.setText(feeling_of_knowledge_prompt);
        feelingOfKnowledgeView.setTextSize(25);
    }


    public void sendMessage(View view){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(QuestionActivity.this, DisplayActivity.class);
                EditText editText = (EditText) findViewById(R.id.edit_message);
                String answer = editText.getText().toString();

                String response = (answer.equals(question.getAnswer()))? "you are correct" : incorrectAnswerFeedback(question);


                intent.putExtra(EXTRA_MESSAGE, response);
                startActivity(intent);
            }
        }, randInt(2000, 8000));

    }


    private String incorrectAnswerFeedback(Problem problem){
        return "that was incorrect: " + problem.getIncorrectAnswerFeedback();
    }


}
