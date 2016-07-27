package com.example.eiqueson.quizgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn3sec;
    Button btnQA;

    String[] topic3sec = {"Animal", "Fruit", "Minecraft"};
    String topicSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn3sec = (Button) findViewById(R.id.btnMode1);
        btnQA = (Button) findViewById(R.id.btnMode2);

        btn3sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTopicList();
            }
        });
        btnQA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getErrorDialog();
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

    public void goTo3seconds()
    {
        Intent intent = new Intent(getApplicationContext(), ThreeSecondsActivity.class);
        startActivity(intent);
    }

    public void showTopicList()
    {
        topicSelected = topic3sec[0];
        AlertDialog.Builder topicDialog1 = new AlertDialog.Builder(this);
        topicDialog1.setTitle("Select Topic");
        topicDialog1.setSingleChoiceItems(topic3sec, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                topicSelected = topic3sec[i];
            }
        });

        topicDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (topicSelected == "Minecraft")
                {
                    Toast toast = Toast.makeText(MainActivity.this, "This topic is not available", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), ThreeSecondsActivity.class);
                    intent.putExtra("Topic", topicSelected);
                    startActivity(intent);
                }
            }
        });
        topicDialog1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        topicDialog1.show();
    }
}
