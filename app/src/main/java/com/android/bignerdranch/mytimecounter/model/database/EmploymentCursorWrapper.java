package com.android.bignerdranch.mytimecounter.model.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.bignerdranch.mytimecounter.model.Employment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static com.android.bignerdranch.mytimecounter.model.database.EmploymentDbSchema.*;

public class EmploymentCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public EmploymentCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Employment getEmployment() {
        String uuidString = getString(getColumnIndex(EmploymentTable.Cols.UUID));
        String title = getString(getColumnIndex(EmploymentTable.Cols.TITLE));
        long date = getLong(getColumnIndex(EmploymentTable.Cols.DATE));
        int color = getInt(getColumnIndex(EmploymentTable.Cols.COLOR));
        int timeInt = getInt(getColumnIndex(EmploymentTable.Cols.TIMEINT));

        Employment employment = new Employment(UUID.fromString(uuidString));
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date);
        employment.setDate(calendar);
        employment.setTitle(title);
        employment.setColor(color);
        employment.setTimeInt(timeInt);
        return employment;
    }
}
