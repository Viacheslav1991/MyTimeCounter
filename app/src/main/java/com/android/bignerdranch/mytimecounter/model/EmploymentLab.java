package com.android.bignerdranch.mytimecounter.model;

import android.graphics.Color;

import com.android.bignerdranch.mytimecounter.TimeHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EmploymentLab {
    private static final EmploymentLab ourInstance = new EmploymentLab();
    private List<Employment> employments;
    private List<ListEmploymentsItem> mListEmploymentItems;

    public static EmploymentLab getInstance() {
        return ourInstance;
    }

    private EmploymentLab() {
        employments = new ArrayList<>();
        mListEmploymentItems = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
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
        }
    }

    public List<Employment> getEmployments() {
        return employments;
    }

    public List<ListEmploymentsItem> getListEmploymentItems() {
        return mListEmploymentItems;
    }

    public void addItemListEmployment(ListEmploymentsItem item) {
        mListEmploymentItems.add(item);
    }

    public void addEmployment(Employment employment) {
        employment.setTimeInt(0);
        employments.add(employment);
    }

    public void deleteEmployment(Employment employment) {
        employments.remove(employment);
    }

    public void deleteItem(ListEmploymentsItem item) {
        mListEmploymentItems.remove(item);
    }

    public Employment getEmployment(UUID uuid) {
        for (Employment employment : employments
        ) {
            if (employment.getId().equals(uuid)) {
                return employment;
            }
        }
        return null;
    }

    public Employment getEmployment(String title) {
        for (Employment employment : employments
        ) {
            if (employment.getTitle().equals(title)) {
                return employment;
            }
        }
        Employment employment = new Employment(title);
        employments.add(employment);
        return employment;
    }

    public Employment getEmployment(ListEmploymentsItem item) {
        for (Employment employment : employments
        ) {
            if (employment.getTitle().equals(item.getTitle())
                    && TimeHelper.compareDate(employment, new GregorianCalendar())) {
                return employment;
            }
        }
        Employment employment = new Employment(item.getTitle());
        employment.setColor(item.getColor());
        employments.add(employment);
        return employment;
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
}
