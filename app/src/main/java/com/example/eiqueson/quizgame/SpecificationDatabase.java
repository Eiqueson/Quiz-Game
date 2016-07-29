package com.example.eiqueson.quizgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by eiqueson on 7/29/2016.
 */
public class SpecificationDatabase extends SQLiteOpenHelper{

    private static final String DB_NAME = "Specification Data";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Specification";
    public static final String COL_SPEC_1 = "spec1";
    public static final String COL_SPEC_2 = "spec2";
    public static final String COL_SPEC_3 = "spec3";
    public static final String COL_SPEC_4 = "spec4";
    public static final String COL_SPEC_5 = "spec5";
    public static final String COL_ANSWER = "answer";

    Context ctx;

    public SpecificationDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String dbName = SpecificationActivity.topic.toLowerCase()+"SpecDB.csv";
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SPEC_1 + " TEXT, " + COL_SPEC_2 + " TEXT, "
                + COL_SPEC_3 + " TEXT, " + COL_SPEC_4 + " TEXT, "
                + COL_SPEC_5 + " TEXT, " + COL_ANSWER + " TEXT);");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(ctx.getAssets().open(dbName)));
            String readLine = null;
            readLine = br.readLine();

            try {
                while ((readLine = br.readLine()) != null)
                {
                    String[] str = readLine.split(",");
                    sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME
                            + " (" + COL_SPEC_1 + ", " + COL_SPEC_2
                            + ", " + COL_SPEC_3 + ", " + COL_SPEC_4
                            + ", " + COL_SPEC_5 + ", " + COL_ANSWER
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
