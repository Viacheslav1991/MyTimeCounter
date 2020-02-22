package com.android.bignerdranch.mytimecounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.android.bignerdranch.mytimecounter.model.DailyEmploymentListLab;
import com.android.bignerdranch.mytimecounter.model.DailyEmploymentsList;



public class StatisticPagerActivity extends AppCompatActivity implements StatisticListFragment.Callbacks {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_pager);

        mViewPager = findViewById(R.id.statistic_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                DailyEmploymentsList list = DailyEmploymentListLab.getInstance().getDailyEmploymentsList(i);
                return StatisticListFragment.newInstance(list.getDateString());
            }

            @Override
            public int getCount() {
                return DailyEmploymentListLab.getInstance().getSize();
            }
        });
        mViewPager.setCurrentItem(DailyEmploymentListLab.getInstance().getSize() - 1);
    }

    @Override
    public void onDateSelected(Integer i) {
        mViewPager.setCurrentItem(i);
    }

}
