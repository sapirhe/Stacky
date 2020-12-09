package com.example.finalprojectstackoverflowapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = MyDatabaseContract.DB_NAME;
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";

    //יצירת טבלאות באמצעות שאילתה
    public static final String SQL_CREATE_SEARCH_HISTORY_TABLE = "CREATE TABLE " + MyDatabaseContract.searchHistoryTable.Table_Name + " (" +
            MyDatabaseContract.searchHistoryTable.COL_SEARCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MyDatabaseContract.searchHistoryTable.COL_SEARCH_WORD + TEXT_TYPE + COMMA_SEP +
            MyDatabaseContract.searchHistoryTable.COL_LOCATION + TEXT_TYPE + ")";

    //מחיקת טבלה
    public static final String SQL_DELETE_SEARCH_HISTORY_TABLE = "DROP TABLE IF EXISTS " + MyDatabaseContract.searchHistoryTable.Table_Name;

    public MyDataBaseHelper(@Nullable Context context) {//יצירת קונסטרקטור בעבור המחלקה
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//בפונקציה זו אנו אמורים לרשום קוד ליצירת db
        db.execSQL(SQL_CREATE_SEARCH_HISTORY_TABLE);//פקודה ליצירת טבלה 1

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//כאן אנו מחליטים אם הDB ישתנו לגרסאות אחרות
        //אנחנו כרגע עובדים עם גרסה 1, יכול להיות שנשנה גרסה בפונקציה זו

        db.execSQL(SQL_DELETE_SEARCH_HISTORY_TABLE);//מחיקת טבלה 1

        onCreate(db);//יצירת טבלאות מחדש
    }
}
