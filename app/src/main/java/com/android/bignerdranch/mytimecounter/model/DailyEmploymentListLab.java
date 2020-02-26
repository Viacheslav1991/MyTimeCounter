package com.android.bignerdranch.mytimecounter.model;

import android.content.Context;

import com.android.bignerdranch.mytimecounter.TimeHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DailyEmploymentListLab {
    private static DailyEmploymentListLab ourInstance;
    Context mContext;
    private List<DailyEmploymentsList> mDailyEmploymentsLists;


    public static DailyEmploymentListLab getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DailyEmploymentListLab(context);
        }
        return ourInstance;
    }

    private DailyEmploymentListLab(Context context) {
        mContext = context.getApplicationContext();
        mDailyEmploymentsLists = new ArrayList<>();
    }

    public DailyEmploymentsList getDailyEmploymentsList(int i) {
        return mDailyEmploymentsLists.get(i);
    }

    public DailyEmploymentsList getDailyEmploymentsList(String sDate) {
        for (DailyEmploymentsList list : mDailyEmploymentsLists) {
            if (TimeHelper.getDateString(list.getDate()).equals(sDate)) {
                return list;
            }
        }
        return null;
    }


    public DailyEmploymentsList getDailyEmploymentsList(Calendar date) {
        for (DailyEmploymentsList list : mDailyEmploymentsLists
        ) {
            if (TimeHelper.compareDate(list, date)) {
                return list;
            }
        }
        DailyEmploymentsList dailyEmploymentsList = new DailyEmploymentsList();
        dailyEmploymentsList.setDate(date);
        mDailyEmploymentsLists.add(dailyEmploymentsList);
        return dailyEmploymentsList;
    }

    public void updateLists() {
        List<Employment> employments = EmploymentLab.getInstance(mContext).getEmployments();
        for (Employment employment : employments) {
            Calendar calendar = employment.getDate();
            getDailyEmploymentsList(calendar).addEmployment(employment);
        }
        Collections.sort(mDailyEmploymentsLists, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    }

    public int getSize() {
        return mDailyEmploymentsLists.size();
    }

}
