package com.example.myfirstapp;

import android.app.AlarmManager;
import android.app.Notification;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import static com.example.myfirstapp.RandomNumber.randInt;

public class QuestionActivity extends AppCompatActivity {

    public final static String ANSWER = "com.example.bumpmath.ANSWER";

    public final static String QUESTION_ID = "com.example.bumpmath.QUESTION_ID";

    public final int NO_QUESTION_EXISTS = -1;



//    private static final String KEY_TEXT_YES = "key_text_yes";
//
//    private static final String KEY_TEXT_NO = "key_text_no";

    private Problem problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent != null){
            int problemId = intent.getIntExtra(QuestionActivity.QUESTION_ID, NO_QUESTION_EXISTS);
            if(problemId == NO_QUESTION_EXISTS){
                problem = Problems.randomProblem();
            } else {
                problem = Problems.get(problemId);
            }
        } else {
            problem = Problems.randomProblem();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(null);

        TextView questionView = (TextView) findViewById(R.id.question_message);
        questionView.setText(problem.getQuestion());
        questionView.setTextSize(25);

        //this is now on the notifications - do we want to keep that interaction inside the activities?
//        TextView feelingOfKnowledgeView = (TextView) findViewById(R.id.feeling_of_knowledge_message);
//        feelingOfKnowledgeView.setText(getString(R.string.feelingOfConfidence));
//        feelingOfKnowledgeView.setTextSize(25);
    }

    private void registerNotification() {
        Context mContext = this;
        Bitmap practiceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.practice);
        Icon practiceIcon = Icon.createWithBitmap(practiceBitmap);

        PendingIntent yesPendingIntent = pendingIntent(QuestionActivity.class, QuestionActivity.class, problem.getId());

        Notification.Action yesAction =
                new Notification.Action.Builder(practiceIcon,
                        getString(R.string.yes), yesPendingIntent)
                        .build();

        PendingIntent noPendingIntent = pendingIntent(QuestionActivity.class, SolutionActivity.class, problem.getId());

        Notification.Action noAction =
                new Notification.Action.Builder(practiceIcon,
                        getString(R.string.no), noPendingIntent)
                        .build();

        final Notification doYouKnowNotification =
                new Notification.Builder(mContext)
                        .setSmallIcon(practiceIcon)
                        .setContentTitle(problem.getQuestion())
                        .setContentText(getString(R.string.feelingOfConfidence))
                        .setPriority(Notification.PRIORITY_MAX)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setCategory(Notification.CATEGORY_REMINDER)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setColor(Color.rgb(106, 206, 107))
                        .addAction(yesAction)
                        .addAction(noAction).build();


        scheduleNotification(doYouKnowNotification, 8000);
    }

    private PendingIntent pendingIntent(Class toActivity, Class parentActivity, int questionId) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtra(QUESTION_ID, questionId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack
        stackBuilder.addParentStack(parentActivity);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(intent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerNotification();
    }

    public void sendMessage(View view){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(QuestionActivity.this, DisplayActivity.class);
                EditText editText = (EditText) findViewById(R.id.edit_message);
                String answer = editText.getText().toString();

                String response = (answer.equals(problem.getAnswer()))? "you are correct" : incorrectAnswerFeedback(problem);

                intent.putExtra(ANSWER, response);
                startActivity(intent);
            }
        }, randInt(10, 30)); // can increase to fulfill time delay later

    }


    private String incorrectAnswerFeedback(Problem problem){
        return "that was incorrect: " + problem.getIncorrectAnswerFeedback();
    }


}
