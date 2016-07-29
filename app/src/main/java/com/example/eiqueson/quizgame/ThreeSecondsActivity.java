package com.example.eiqueson.quizgame;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ThreeSecondsActivity extends AppCompatActivity {

    CountDownTimer timer3;
    TextView timerText;
    TextView topicText;
    TextView scoreText;
    TextView fullScoreText;
    ProgressBar timerProgress;
    Button timerStart;
    ImageView quizImage;
    RadioGroup choices;
    RadioButton choice1, choice2, choice3, choice4;

    public static String topic;
    static int fullScore;
    int score;

    SQLiteDatabase mDatabase;
    Database mHelper;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_seconds);

        Bundle bundle = getIntent().getExtras();
        topic = bundle.getString("Topic");
        fullScore = bundle.getInt("Fullscore");
        score = 0;

        mHelper = new Database(this);
        mDatabase = mHelper.getWritableDatabase();
        mHelper.onUpgrade(mDatabase, 1, 1);


        timerText = (TextView) findViewById(R.id.tvTimer);
        timerText.setVisibility(View.INVISIBLE);
        topicText = (TextView) findViewById(R.id.topic3sec);
        topicText.setText(topic);
        scoreText = (TextView) findViewById(R.id.score3sec);
        fullScoreText = (TextView) findViewById(R.id.fullscore3sec);
        fullScoreText.setText(String.valueOf(fullScore));

        timerProgress = (ProgressBar) findViewById(R.id.pbTimer);
        timerProgress.setVisibility(View.INVISIBLE);

        quizImage = (ImageView) findViewById(R.id.image3sec);
        choices = (RadioGroup) findViewById(R.id.radioGroup1);
        choice1 = (RadioButton) findViewById(R.id.radioBtn1);
        choice2 = (RadioButton) findViewById(R.id.radioBtn2);
        choice3 = (RadioButton) findViewById(R.id.radioBtn3);
        choice4 = (RadioButton) findViewById(R.id.radioBtn4);
        enableRadioButton(false);
        /*
        mCursor = mDatabase.rawQuery("SELECT " + Database.COL_IMAGE + "," + Database.COL_CHOICE_1

                                    + "," + Database.COL_CHOICE_2 + "," + Database.COL_CHOICE_3
                                    + "," + Database.COL_CHOICE_4 + " FROM " + Database.TABLE_NAME, null);
        */
        mCursor = mDatabase.rawQuery("SELECT * FROM " + Database.TABLE_NAME, null);
        mCursor.moveToFirst();

        //Quiz start
        timerStart = (Button) findViewById(R.id.btnCount);
        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerProgress.setVisibility(View.VISIBLE);
                timerStart.setEnabled(false);
                timerText.setVisibility(View.VISIBLE);

                enableRadioButton(true);

                if (mCursor.isAfterLast())
                {
                    mCursor.moveToFirst();
                }

                String imgName = mCursor.getString(mCursor.getColumnIndex(Database.COL_IMAGE));
                choice1.setText(mCursor.getString(mCursor.getColumnIndex(Database.COL_CHOICE_1)));
                choice2.setText(mCursor.getString(mCursor.getColumnIndex(Database.COL_CHOICE_2)));
                choice3.setText(mCursor.getString(mCursor.getColumnIndex(Database.COL_CHOICE_3)));
                choice4.setText(mCursor.getString(mCursor.getColumnIndex(Database.COL_CHOICE_4)));

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
                        mCursor.moveToNext();
                        timerProgress.setVisibility(View.INVISIBLE);
                        timerText.setVisibility(View.INVISIBLE);
                        timerStart.setEnabled(true);
                    }
                };
                timer3.start();
            }
        });

        choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String ans = mCursor.getString(mCursor.getColumnIndex(Database.COL_ANSWER));
                switch (i)
                {
                    case R.id.radioBtn1:
                        if (choice1.getText().toString().matches(ans))
                        {
                            showCorrectDialog(ans);
                        }
                        else
                        {
                            showWrongDialog();
                        }
                        timer3.cancel();
                        timerText.setText("0");
                        timerProgress.setVisibility(View.INVISIBLE);
                        timerText.setVisibility(View.INVISIBLE);
                        timerStart.setEnabled(true);
                        choice1.setChecked(false);
                        enableRadioButton(false);
                        mCursor.moveToNext();
                        break;

                    case R.id.radioBtn2:
                        if (choice2.getText().toString().matches(ans))
                        {
                            showCorrectDialog(ans);
                        }
                        else
                        {
                            showWrongDialog();
                        }
                        timer3.cancel();
                        timerText.setText("0");
                        timerProgress.setVisibility(View.INVISIBLE);
                        timerText.setVisibility(View.INVISIBLE);
                        timerStart.setEnabled(true);
                        choice2.setChecked(false);
                        enableRadioButton(false);
                        mCursor.moveToNext();
                        break;

                    case R.id.radioBtn3:
                        if (choice3.getText().toString().matches(ans))
                        {
                            showCorrectDialog(ans);
                        }
                        else
                        {
                            showWrongDialog();
                        }
                        timer3.cancel();
                        timerText.setText("0");
                        timerProgress.setVisibility(View.INVISIBLE);
                        timerText.setVisibility(View.INVISIBLE);
                        timerStart.setEnabled(true);
                        choice3.setChecked(false);
                        enableRadioButton(false);
                        mCursor.moveToNext();
                        break;

                    case R.id.radioBtn4:
                        if (choice4.getText().toString().matches(ans))
                        {
                            showCorrectDialog(ans);
                        }
                        else
                        {
                            showWrongDialog();
                        }
                        timer3.cancel();
                        timerText.setText("0");
                        timerProgress.setVisibility(View.INVISIBLE);
                        timerText.setVisibility(View.INVISIBLE);
                        timerStart.setEnabled(true);
                        choice4.setChecked(false);
                        enableRadioButton(false);
                        mCursor.moveToNext();
                        break;
                }
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

    public void showWrongDialog()
    {
        AlertDialog.Builder wrongDialog = new AlertDialog.Builder(this);
        wrongDialog.setTitle("Fail");
        wrongDialog.setMessage("Wrong !!");
        wrongDialog.setIcon(R.drawable.trollo);
        wrongDialog.setCancelable(false);
        wrongDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        wrongDialog.show();
    }

    public void showCorrectDialog(String ans)
    {
        AlertDialog.Builder correctDialog = new AlertDialog.Builder(this);
        correctDialog.setTitle("Congrats !");
        correctDialog.setMessage("Correct !! This is [" + ans + "]");
        correctDialog.setIcon(R.drawable.trollo);
        correctDialog.setCancelable(false);
        correctDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        correctDialog.show();
        score++;
        scoreText.setText(String.valueOf(score));
    }

    public void enableRadioButton(boolean toggle)
    {
        choice1.setEnabled(toggle);
        choice2.setEnabled(toggle);
        choice3.setEnabled(toggle);
        choice4.setEnabled(toggle);
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
