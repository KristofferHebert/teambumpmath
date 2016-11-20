package com.example.myfirstapp;

import android.app.AlarmManager;
import android.app.Notification;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.myfirstapp.RandomNumber.randInt;

public class QuestionActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static final String KEY_TEXT_YES = "key_text_yes";

    private static final String KEY_TEXT_NO = "key_text_no";

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
        feelingOfKnowledgeView.setText(getString(R.string.feelingOfConfidence));
        feelingOfKnowledgeView.setTextSize(25);



        //TODO move this out

        Context mContext = this;

        RemoteInput remoteYesInput = new RemoteInput.Builder(KEY_TEXT_YES)
                .setLabel(getString(R.string.yes))
                .build();

        PendingIntent yesPendingIntent = pendingIntent(DisplayActivity.class, QuestionActivity.class);

        //This was deprecated for passing in an Icon not the int from R.drawable
        Notification.Action yesAction =
                new Notification.Action.Builder(R.drawable.cat,
                        getString(R.string.yes), yesPendingIntent)
                        .addRemoteInput(remoteYesInput)
                        .build();


        RemoteInput remoteNoInput = new RemoteInput.Builder(KEY_TEXT_NO)
                .setLabel(getString(R.string.no))
                .build();

        PendingIntent noPendingIntent = pendingIntent(DisplayActivity.class, QuestionActivity.class);

        //TODO the icon is not real
        //This was deprecated for passing in an Icon not the int from R.drawable
        Notification.Action noAction =
                new Notification.Action.Builder(R.drawable.cat,
                        getString(R.string.no), noPendingIntent)
                        .addRemoteInput(remoteNoInput)
                        .build();

        final Notification doYouKnowNotification =
                new Notification.Builder(mContext)
                        .setSmallIcon(R.drawable.cat)
                        .setContentTitle(question.getQuestion())
                        .setContentText(getString(R.string.feelingOfConfidence))
                       // .addAction(yesAction) //TODO want to add two actions
                        .addAction(noAction).build();

//        final Notification hiNotification = getNotification("HI!!");

        scheduleNotification(doYouKnowNotification, 8000);

    }

    private PendingIntent pendingIntent(Class toActivity, Class parentActivity) {
        Intent intent = new Intent(this, toActivity);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack
        stackBuilder.addParentStack(parentActivity);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(intent);

        //TODO instantiate
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.cat);
        return builder.build();
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
        }, randInt(1000, 3000));

    }


    private String incorrectAnswerFeedback(Problem problem){
        return "that was incorrect: " + problem.getIncorrectAnswerFeedback();
    }


}
