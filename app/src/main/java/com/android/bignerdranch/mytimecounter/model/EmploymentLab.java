package com.android.bignerdranch.mytimecounter.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import com.android.bignerdranch.mytimecounter.TimeHelper;
import com.android.bignerdranch.mytimecounter.model.database.EmploymentBaseHelper;
import com.android.bignerdranch.mytimecounter.model.database.EmploymentCursorWrapper;
import com.android.bignerdranch.mytimecounter.model.database.EmploymentDbSchema;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.android.bignerdranch.mytimecounter.model.database.EmploymentDbSchema.*;

public class EmploymentLab {
    private static EmploymentLab ourInstance;
    private List<Employment> employments;
    private List<ListEmploymentsItem> mListEmploymentItems;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static EmploymentLab getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new EmploymentLab(context);
        }
        return ourInstance;
    }

    private EmploymentLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new EmploymentBaseHelper(mContext)
                .getWritableDatabase();

        employments = new ArrayList<>();
        mListEmploymentItems = new ArrayList<>();

       /* for (int i = 0; i < 10; i++) {
            Employment employment = new Employment(("Employment number " + i));
            employment.setDate(GregorianCalendar.getInstance());
            Random rnd = new Random();
            employment.setTimeInt((rnd.nextInt(86400)));
            employments.add(employment);
        }
        employments.get(4).getDate().set(Calendar.DAY_OF_MONTH, 9);
        employments.get(5).getDate().set(Calendar.DAY_OF_MONTH, 10);
        employments.get(6).getDate().set(Calendar.DAY_OF_MONTH, 11);

        for (int i = 0; i < 15; i++) {
            ListEmploymentsItem item = new ListEmploymentsItem();
            item.setTitle(("Employment number " + i));

            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            item.setColor(color);
            for (Employment employment : employments) {
                if (employment.getTitle().equals(item.getTitle())) {
                    if (TimeHelper.compareDate(employment, new GregorianCalendar())) {
                        item.setHasEmployment(true);
                    }
                }
            }
            mListEmploymentItems.add(item);
        }*/
    }

    public List<Employment> getEmployments() {
        List<Employment> employments = new ArrayList<>();
        EmploymentCursorWrapper cursor = queryEmployments(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                employments.add(cursor.getEmployment());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return employments;
    }

    public List<ListEmploymentsItem> getListEmploymentItems() {
        return mListEmploymentItems;
    }

    public void addItemListEmployment(ListEmploymentsItem item) {
        mListEmploymentItems.add(item);
    }

    public void addEmployment(Employment employment) {
//        employment.setTimeInt(0);
//        employments.add(employment);
        ContentValues values = getContentValues(employment);
        mDatabase.insert(EmploymentTable.NAME, null, values);
    }

    public void updateEmployment(Employment employment) {
        String uuidString = employment.getId().toString();
        ContentValues values = getContentValues(employment);
        mDatabase.update(EmploymentTable.NAME, values,
                EmploymentTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteEmployment(Employment employment) {
        employments.remove(employment);
    }

    public void deleteItem(ListEmploymentsItem item) {
        mListEmploymentItems.remove(item);
    }

    public Employment getEmployment(UUID id) {
        EmploymentCursorWrapper cursor = queryEmployments(
                EmploymentTable.Cols.UUID + " = ?",
                new String[]{id.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getEmployment();
        } finally {
            cursor.close();
        }
    }

    public Employment getEmployment(String title) {

        for (Employment employment : employments
        ) {
            if (employment.getTitle().equals(title)) {
                return employment;
            }
        }
        Employment employment = new Employment(title);
        addEmployment(employment);
        return employment;
    }

    public Employment getEmployment(ListEmploymentsItem item) {
        EmploymentCursorWrapper cursor = queryEmployments(
                EmploymentTable.Cols.TITLE + " = ?",
                new String[]{item.getTitle()});
        try {
            if (cursor.getCount() == 0) {
                Employment employment = new Employment(item.getTitle());
                employment.setColor(item.getColor());
                addEmployment(employment);
                return employment;
//                return null;
            }
            cursor.moveToFirst();
            Employment employment = cursor.getEmployment();
            if (TimeHelper.compareDate(employment, new GregorianCalendar())) {
                return employment;
            }
        } finally {
            cursor.close();
        }

        /*Employment employment = new Employment(item.getTitle());
        employment.setColor(item.getColor());
        addEmployment(employment);
        return employment;*/
        return null;


        /*for (Employment employment : employments
        ) {
            if (employment.getTitle().equals(item.getTitle())
                    && TimeHelper.compareDate(employment, new GregorianCalendar())) {
                return employment;
            }
        }
        Employment employment = new Employment(item.getTitle());
        employment.setColor(item.getColor());
        addEmployment(employment);
        return employment;*/
    }

    public ListEmploymentsItem getItemListEmployment(String title) {
        for (ListEmploymentsItem item : mListEmploymentItems
        ) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        return null;
    }


    private static ContentValues getContentValues(Employment employment) {
        ContentValues values = new ContentValues();
        values.put(EmploymentTable.Cols.UUID, employment.getId().toString());
        values.put(EmploymentTable.Cols.TITLE, employment.getTitle());
        values.put(EmploymentTable.Cols.DATE, employment.getDate().getTimeInMillis());
        values.put(EmploymentTable.Cols.COLOR, employment.getColor());
        values.put(EmploymentTable.Cols.TIMEINT, employment.getTimeInt());
        return values;
    }

    private EmploymentCursorWrapper queryEmployments(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                EmploymentTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new EmploymentCursorWrapper(cursor);

    }
}
