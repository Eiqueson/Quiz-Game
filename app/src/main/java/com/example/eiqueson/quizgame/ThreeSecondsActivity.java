package com.example.eiqueson.quizgame;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreeSecondsActivity extends AppCompatActivity {

    CountDownTimer timer3;
    TextView timerText;
    TextView topicText;
    ProgressBar timerProgress;
    Button timerStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_seconds);

        Bundle bundle = getIntent().getExtras();
        String topic = bundle.getString("Topic");


        timerText = (TextView) findViewById(R.id.tvTimer);
        topicText = (TextView) findViewById(R.id.topic3sec);
        topicText.setText(topic);

        timerProgress = (ProgressBar) findViewById(R.id.pbTimer);
        timerProgress.setVisibility(View.INVISIBLE);

        timerStart = (Button) findViewById(R.id.btnCount);
        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerProgress.setVisibility(View.VISIBLE);
                timerStart.setEnabled(false);

                timer3 = new CountDownTimer(3000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        String strTime = String.format("%d", (millisUntilFinished / 1000)+1);
                        timerText.setText(String.valueOf(strTime));
                    }

                    @Override
                    public void onFinish() {
                        timerText.setText("0");
                        showTimeOutDialog();
                        timerProgress.setVisibility(View.INVISIBLE);
                        timerStart.setEnabled(true);
                    }
                }.start();
            }
        });
    }

    public void showTimeOutDialog()
    {
        AlertDialog.Builder timeoutDialog = new AlertDialog.Builder(this);
        timeoutDialog.setTitle("Fail");
        timeoutDialog.setMessage("Time's up !");
        timeoutDialog.setIcon(R.drawable.trollo);
        timeoutDialog.setCancelable(false);
        timeoutDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        timeoutDialog.show();
    }
}
