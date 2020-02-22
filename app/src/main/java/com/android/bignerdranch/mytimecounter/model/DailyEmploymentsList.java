package com.android.bignerdranch.mytimecounter.model;

import com.android.bignerdranch.mytimecounter.TimeHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DailyEmploymentsList implements HasDate{
    private Calendar mCalendar;
    private List<Employment> mEmployments;

    public Calendar getDate() {
        return mCalendar;
    }

    @Override
    public String getDateString() {
        return TimeHelper.getDateString(mCalendar);
    }

    @Override
    public void setDate(Calendar date) {
        mCalendar = date;
    }

    public DailyEmploymentsList() {
        mCalendar = new GregorianCalendar();
        mEmployments = new ArrayList<>();
    }

    public void addEmployment(Employment employment) {
        if (mEmployments.contains(employment)) {
            return;
        }
        mEmployments.add(employment);
    }

    public List<Employment> getEmployments() {
        return mEmployments;
    }
}
