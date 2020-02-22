package com.android.bignerdranch.mytimecounter.model;

import com.android.bignerdranch.mytimecounter.TimeHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class Employment implements HasDate{
    private UUID mId;
    private String mTitle;
    private int mColor;
    private int mTimeInt;
    private String mTime;
    private Calendar mDate;


    public Calendar getDate() {
        return mDate;
    }

    @Override
    public String getDateString() {
        return TimeHelper.getDateString(mDate);
    }

    public void setDate(Calendar date) {
        mDate = date;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public Employment() {
        mId = UUID.randomUUID();
        mTime = TimeHelper.getTime(mTimeInt);
        mTimeInt = 0;
        mDate = new GregorianCalendar();
    }

    public Employment(String title) {
        this();
        mId = this.getId();
        mTitle = title;
    }

    public int getTimeInt() {
        return mTimeInt;
    }

    public void setTimeInt(int timeInt) {
        mTimeInt = timeInt;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


}
