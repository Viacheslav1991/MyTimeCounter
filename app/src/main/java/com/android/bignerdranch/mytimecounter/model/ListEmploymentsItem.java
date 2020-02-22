package com.android.bignerdranch.mytimecounter.model;

public class ListEmploymentsItem {
    private String mTitle;
    private int mColor;
    private boolean hasEmployment = false;

    public boolean isHasEmployment() {
        return hasEmployment;
    }

    public void setHasEmployment(boolean hasEmployment) {
        this.hasEmployment = hasEmployment;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
}
