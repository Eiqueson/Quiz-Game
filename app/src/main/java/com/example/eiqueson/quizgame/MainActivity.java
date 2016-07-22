package com.example.eiqueson.quizgame;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
}
