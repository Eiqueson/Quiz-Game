package com.example.eiqueson.quizgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn3sec;
    Button btnQA;

    String[] topic3sec = {"Animal", "Fruit", "Minecraft"};
    String[] topicSpec = {"Animal", "Minecraft"};
    String topicSelected;
    int[] fullScoreSet3sec = {8,3,3};
    int[] fullScoreSetSpec = {25, 50};
    int fullScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn3sec = (Button) findViewById(R.id.btnMode1);
        btnQA = (Button) findViewById(R.id.btnMode2);

        btn3sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTopic3secList();
            }
        });
        btnQA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTopicSpecList();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);
        exitDialog.setTitle("Exit");
        exitDialog.setMessage("Do you want to quit ?");
        exitDialog.setCancelable(true);
        exitDialog.setIcon(R.drawable.meme15);
        exitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        exitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        exitDialog.show();
    }

    public void getErrorDialog()
    {
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
        errorDialog.setTitle("Error !!");
        errorDialog.setMessage("You cannot access this, problem ?");
        errorDialog.setCancelable(false);
        errorDialog.setIcon(R.drawable.trollo);
        errorDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        errorDialog.show();
    }

    public void goToSpec()
    {
        Intent intent = new Intent(getApplicationContext(), SpecificationActivity.class);
        startActivity(intent);
    }

    public void showTopic3secList()
    {
        //topicSelected = topic3sec[0];
        AlertDialog.Builder topicDialog1 = new AlertDialog.Builder(this);
        topicDialog1.setTitle("Select Topic");
        topicDialog1.setSingleChoiceItems(topic3sec, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                topicSelected = topic3sec[i];
                fullScore = fullScoreSet3sec[i];
            }
        });

        topicDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (topicSelected == null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select a topic first", Toast.LENGTH_LONG);
                    toast.show();
                }
                else if (topicSelected == "Minecraft")
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "This topic is not available", Toast.LENGTH_LONG);
                    topicSelected = null;
                    toast.show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), ThreeSecondsActivity.class);
                    intent.putExtra("Topic", topicSelected);
                    intent.putExtra("Fullscore", fullScore);
                    topicSelected = null;
                    startActivity(intent);
                }
            }
        });
        topicDialog1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                topicSelected = null;
                dialogInterface.cancel();
            }
        });
        topicDialog1.show();
    }

    public void showTopicSpecList()
    {
        //topicSelected = topic3sec[0];
        AlertDialog.Builder topicDialog2 = new AlertDialog.Builder(this);
        topicDialog2.setTitle("Select Topic");
        topicDialog2.setSingleChoiceItems(topicSpec, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                topicSelected = topicSpec[i];
                fullScore = fullScoreSetSpec[i];
            }
        });

        topicDialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (topicSelected == null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select a topic first", Toast.LENGTH_LONG);
                    toast.show();
                }
                else if (topicSelected == "Minecraft")
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "This topic is not available", Toast.LENGTH_LONG);
                    topicSelected = null;
                    toast.show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), SpecificationActivity.class);
                    intent.putExtra("Topic", topicSelected);
                    intent.putExtra("Fullscore", fullScore);
                    topicSelected = null;
                    startActivity(intent);
                }
            }
        });
        topicDialog2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                topicSelected = null;
                dialogInterface.cancel();
            }
        });
        topicDialog2.show();
    }
}
