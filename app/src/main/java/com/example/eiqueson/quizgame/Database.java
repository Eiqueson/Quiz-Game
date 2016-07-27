package com.example.eiqueson.quizgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by eiqueson on 7/26/2016.
 */
public class Database extends SQLiteOpenHelper
{

    private static final String DB_NAME = "3 Seconds Data";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Fruit";
    public static final String COL_IMAGE = "image";
    public static final String COL_CHOICE_1 = "choice1";
    public static final String COL_CHOICE_2 = "choice2";
    public static final String COL_CHOICE_3 = "choice3";
    public static final String COL_CHOICE_4 = "choice4";
    public static final String COL_ANSWER = "answer";

    Context ctx;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String dbName = ThreeSecondsActivity.topic.toLowerCase()+"DB.csv";
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME
                                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + COL_IMAGE + " TEXT, " + COL_CHOICE_1 + " TEXT, "
                                + COL_CHOICE_2 + " TEXT, " + COL_CHOICE_3 + " TEXT, "
                                + COL_CHOICE_4 + " TEXT, " + COL_ANSWER + " TEXT);");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(ctx.getAssets().open(dbName)));
            String readLine = null;
            readLine = br.readLine();

            try {
                while ((readLine = br.readLine()) != null)
                {
                    String[] str = readLine.split(",");
                    sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME
                                            + " (" + COL_IMAGE + ", " + COL_CHOICE_1
                                            + ", " + COL_CHOICE_2 + ", " + COL_CHOICE_3
                                            + ", " + COL_CHOICE_4 + ", " + COL_ANSWER
                                            + ") VALUES ('" + str[0] + "', '" + str[1]
                                            + "', '" + str[2] + "', '" + str[3]
                                            + "', '" + str[4] + "', '" + str[5] + "');");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
