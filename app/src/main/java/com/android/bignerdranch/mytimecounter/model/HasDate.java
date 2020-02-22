package com.android.bignerdranch.mytimecounter.model;

import java.util.Calendar;

public interface HasDate {
     Calendar getDate();
     String getDateString();
     void setDate(Calendar date);
}
