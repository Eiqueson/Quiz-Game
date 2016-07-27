package com.example.eiqueson.quizgame;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;

public class ThreeSecondsActivity extends AppCompatActivity {

    CountDownTimer timer3;
    TextView timerText;
    TextView topicText;
    ProgressBar timerProgress;
    Button timerStart;
    ImageView quizImage;
    RadioButton choice1, choice2, choice3, choice4;

    public static String topic;

    SQLiteDatabase mDatabase;
    Database mHelper;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_seconds);

        Bundle bundle = getIntent().getExtras();
        topic = bundle.getString("Topic");

        mHelper = new Database(this);
        mDatabase = mHelper.getWritableDatabase();
        mHelper.onUpgrade(mDatabase, 1, 1);


        timerText = (TextView) findViewById(R.id.tvTimer);
        topicText = (TextView) findViewById(R.id.topic3sec);
        topicText.setText(topic);

        timerProgress = (ProgressBar) findViewById(R.id.pbTimer);
        timerProgress.setVisibility(View.INVISIBLE);

        quizImage = (ImageView) findViewById(R.id.image3sec);
        choice1 = (RadioButton) findViewById(R.id.radioBtn1);
        choice2 = (RadioButton) findViewById(R.id.radioBtn2);
        choice3 = (RadioButton) findViewById(R.id.radioBtn3);
        choice4 = (RadioButton) findViewById(R.id.radioBtn4);
        mCursor = mDatabase.rawQuery("SELECT " + Database.COL_IMAGE + "," + Database.COL_CHOICE_1
                                    + "," + Database.COL_CHOICE_2 + "," + Database.COL_CHOICE_3
                                    + "," + Database.COL_CHOICE_4 + " FROM " + Database.TABLE_NAME, null);
        mCursor.moveToFirst();

        timerStart = (Button) findViewById(R.id.btnCount);
        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerProgress.setVisibility(View.VISIBLE);
                timerStart.setEnabled(false);

                String imgName = "";
                if (mCursor.isAfterLast())
                {
                    mCursor.moveToFirst();
                }

                    imgName = mCursor.getString(mCursor.getColumnIndex(Database.COL_IMAGE));
                    choice1.setText(mCursor.getString(mCursor.getColumnIndex(Database.COL_CHOICE_1)));
                    choice2.setText(mCursor.getString(mCursor.getColumnIndex(Database.COL_CHOICE_2)));
                    choice3.setText(mCursor.getString(mCursor.getColumnIndex(Database.COL_CHOICE_3)));
                    choice4.setText(mCursor.getString(mCursor.getColumnIndex(Database.COL_CHOICE_4)));
                    mCursor.moveToNext();

                int drawId = getResources().getIdentifier(imgName, "drawable", getPackageName());
                quizImage.setImageResource(drawId);

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

    @Override
    public void onPause()
    {
        super.onPause();
        mHelper.close();
        mDatabase.close();
    }

    @Override
    public void onBackPressed()
    {
        if (timerStart.isEnabled())
        {
            finish();
        }
    }
}
