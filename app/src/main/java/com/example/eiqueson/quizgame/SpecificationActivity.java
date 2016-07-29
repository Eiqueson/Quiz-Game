package com.example.eiqueson.quizgame;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class SpecificationActivity extends AppCompatActivity {

    TextView topicText;
    TextView scoreText;
    TextView fullScoreText;
    EditText specAnswer;
    TextView specText1, specText2, specText3, specText4, specText5;
    Button spec1, spec2, spec3, spec4, spec5;
    Button specStart;
    Button specMore, specSubmit;

    public static String topic;
    static int fullScore;
    int score;
    LinkedList<String> specState = new LinkedList<String>();

    SQLiteDatabase mDatabase;
    SpecificationDatabase mHelper;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specification);

        Bundle bundle = getIntent().getExtras();
        topic = bundle.getString("Topic");
        fullScore = bundle.getInt("Fullscore");
        score = 0;

        mHelper = new SpecificationDatabase(this);
        mDatabase = mHelper.getWritableDatabase();
        mHelper.onUpgrade(mDatabase, 1, 1);

        topicText = (TextView) findViewById(R.id.topicSpec);
        topicText.setText(topic);
        scoreText = (TextView) findViewById(R.id.scoreSpec);
        fullScoreText = (TextView) findViewById(R.id.fullscoreSpec);
        fullScoreText.setText(String.valueOf(fullScore));

        specStart = (Button) findViewById(R.id.specBtnStart);
        spec1 = (Button) findViewById(R.id.specBtn1);
        spec2 = (Button) findViewById(R.id.specBtn2);
        spec3 = (Button) findViewById(R.id.specBtn3);
        spec4 = (Button) findViewById(R.id.specBtn4);
        spec5 = (Button) findViewById(R.id.specBtn5);
        specText1 = (TextView) findViewById(R.id.specText1);
        specText2 = (TextView) findViewById(R.id.specText2);
        specText3 = (TextView) findViewById(R.id.specText3);
        specText4 = (TextView) findViewById(R.id.specText4);
        specText5 = (TextView) findViewById(R.id.specText5);
        specMore = (Button) findViewById(R.id.specBtnMore);
        specSubmit = (Button) findViewById(R.id.specBtnAns);
        specAnswer = (EditText) findViewById(R.id.specEditText);

        mCursor = mDatabase.rawQuery("SELECT * FROM " + SpecificationDatabase.TABLE_NAME, null);
        mCursor.moveToFirst();

        enableSpecButton(false);
        specMore.setEnabled(false);
        specSubmit.setEnabled(false);

        specStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearAllSpec();
                enableSpecButton(true);
                specStart.setEnabled(false);
                specSubmit.setEnabled(true);
                specState.clear();
                specAnswer.setEnabled(false);

                if (mCursor.isAfterLast())
                {
                    mCursor.moveToFirst();
                }


            }
        });

        specSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAnswerDialog();
            }
        });

        spec1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpec(1);
            }
        });
        spec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpec(2);
            }
        });
        spec3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpec(3);
            }
        });
        spec4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpec(4);
            }
        });
        spec5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpec(5);
            }
        });

        specMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enableSpecButton(true);
                specMore.setEnabled(false);
                for (int i=0; i<specState.size(); i++)
                {
                    switch (specState.get(i))
                    {
                        case "1":
                            spec1.setEnabled(false);
                            break;

                        case "2":
                            spec2.setEnabled(false);
                            break;

                        case "3":
                            spec3.setEnabled(false);
                            break;

                        case "4":
                            spec4.setEnabled(false);
                            break;

                        case "5":
                            spec5.setEnabled(false);
                            break;
                    }
                }
            }
        });

    }

    public void showSpec(int btn)
    {
        switch (btn)
        {
            case 1:
                specText1.setText(mCursor.getString(mCursor.getColumnIndex(SpecificationDatabase.COL_SPEC_1)));
                specState.add("1");
                enableSpecButton(false);
                specMore.setEnabled(true);
                if (specState.size() == 5) {
                    specMore.setEnabled(false);
                }
                break;

            case 2:
                specText2.setText(mCursor.getString(mCursor.getColumnIndex(SpecificationDatabase.COL_SPEC_2)));
                specState.add("2");
                enableSpecButton(false);
                specMore.setEnabled(true);
                if (specState.size() == 5) {
                    specMore.setEnabled(false);
                }
                break;

            case 3:
                specText3.setText(mCursor.getString(mCursor.getColumnIndex(SpecificationDatabase.COL_SPEC_3)));
                specState.add("3");
                enableSpecButton(false);
                specMore.setEnabled(true);
                if (specState.size() == 5) {
                    specMore.setEnabled(false);
                }
                break;

            case 4:
                specText4.setText(mCursor.getString(mCursor.getColumnIndex(SpecificationDatabase.COL_SPEC_4)));
                specState.add("4");
                enableSpecButton(false);
                specMore.setEnabled(true);
                if (specState.size() == 5) {
                    specMore.setEnabled(false);
                }
                break;

            case 5:
                specText5.setText(mCursor.getString(mCursor.getColumnIndex(SpecificationDatabase.COL_SPEC_5)));
                specState.add("5");
                enableSpecButton(false);
                specMore.setEnabled(true);
                if (specState.size() == 5) {
                    specMore.setEnabled(false);
                }
                break;
        }
        specAnswer.setEnabled(true);
    }

    public void clearAllSpec()
    {
        specText1.setText("");
        specText2.setText("");
        specText3.setText("");
        specText4.setText("");
        specText5.setText("");
    }

    public void enableSpecButton(boolean toggle)
    {
        spec1.setEnabled(toggle);
        spec2.setEnabled(toggle);
        spec3.setEnabled(toggle);
        spec4.setEnabled(toggle);
        spec5.setEnabled(toggle);
    }

    public void confirmAnswerDialog()
    {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setTitle("Confirm");
        confirmDialog.setMessage("Are you sure ?");
        confirmDialog.setIcon(R.drawable.meme15);
        confirmDialog.setCancelable(false);
        confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ans = mCursor.getString(mCursor.getColumnIndex(SpecificationDatabase.COL_ANSWER));
                if (specAnswer.getText().toString().toLowerCase().matches(ans))
                {
                    showCorrectDialog(ans);
                }
                else
                {
                    showWrongDialog();
                }
                mCursor.moveToNext();
                specStart.setEnabled(true);
                specSubmit.setEnabled(false);
                specMore.setEnabled(false);
                specAnswer.setText("");
            }
        });
        confirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        confirmDialog.show();
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
                dialogInterface.dismiss();
            }
        });
        wrongDialog.show();
    }

    public void showCorrectDialog(String ans)
    {
        AlertDialog.Builder correctDialog = new AlertDialog.Builder(this);
        correctDialog.setTitle("Congrats !");
        correctDialog.setMessage("Correct !! It is [" + ans + "]");
        correctDialog.setIcon(R.drawable.trollo);
        correctDialog.setCancelable(false);
        correctDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        correctDialog.show();
        score += (6 - specState.size());
        scoreText.setText(String.valueOf(score));
    }
}
