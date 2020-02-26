package com.android.bignerdranch.mytimecounter.model;

import com.android.bignerdranch.mytimecounter.TimeHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyEmploymentsList implements HasDate{
    private Calendar mCalendar;
    private Map<String,Employment> mEmployments;

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
        mEmployments = new HashMap<>();
    }

    public void addEmployment(Employment employment) {
        mEmployments.put(employment.getTitle(), employment);
    }

    public List<Employment> getEmployments() {
        return new ArrayList<>(mEmployments.values());
    }
}
