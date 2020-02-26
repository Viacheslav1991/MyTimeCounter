package com.android.bignerdranch.mytimecounter.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.android.bignerdranch.mytimecounter.model.database.EmploymentDbSchema.*;

public class EmploymentBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "employmentBase.db";


    public EmploymentBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + EmploymentTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                EmploymentTable.Cols.UUID + ", " +
                EmploymentTable.Cols.TITLE + ", " +
                EmploymentTable.Cols.DATE + ", " +
                EmploymentTable.Cols.COLOR + ", " +
                EmploymentTable.Cols.TIMEINT +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
