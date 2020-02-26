package com.android.bignerdranch.mytimecounter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.bignerdranch.mytimecounter.model.Employment;
import com.android.bignerdranch.mytimecounter.model.ListEmploymentsItem;

public class CounterListActivity extends AppCompatActivity implements CounterListFragment.Callbacks, CounterFragment.Callbacks, AddItemEmploymentFragment.Callbacks {
    private static final String NEW_EMPLOYMENT = "NewEmployment";
    private static final String NEW_ITEM_LIST_EMPLOYMENT = "NewItemListEmployment";
    private static final String CHANGE_EMPLOYMENT = "ChangeEmployment";
    private static final String CHANGE_ITEM_EMPLOYMENT = "ChangeItemEmployment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new CounterListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        /*Fragment counterFragment = fm.findFragmentById(R.id.counter_container);
        if (counterFragment == null) {
            counterFragment = CounterFragment.newInstance(EmploymentLab.getInstance().getEmployments().get(0).getId());
            fm.beginTransaction()
                    .add(R.id.counter_container, counterFragment)
                    .commit();
        }*/
    }


    @Override
    public void onEmploymentUpdated(Employment employment) {
        CounterListFragment listFragment = (CounterListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateEmployment(employment);
    }

    @Override
    public void onItemEmploymentUpdated() {
        CounterListFragment listFragment = (CounterListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();

    }

    @Override
    public void onEmploymentSelected(Employment employment) {
        Fragment counterFragment = CounterFragment.newInstance(employment.getId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.counter_container, counterFragment)
                .commit();
    }

    @Override
    public void onNewEmployment(Employment employment) {
        FragmentManager manager = getSupportFragmentManager();
        AddItemEmploymentFragment dialog = AddItemEmploymentFragment.newInstance(employment.getId());
        dialog.show(manager, NEW_EMPLOYMENT);
    }


    @Override
    public void onNewItemEmployments() {
        FragmentManager manager = getSupportFragmentManager();
        AddItemEmploymentFragment dialog = AddItemEmploymentFragment.newInstance();
        dialog.show(manager, NEW_ITEM_LIST_EMPLOYMENT);
    }

    @Override
    public void onChangeItemEmployments(String itemTitle) {
        FragmentManager manager = getSupportFragmentManager();
        AddItemEmploymentFragment dialog = AddItemEmploymentFragment.newInstance(itemTitle);
        dialog.show(manager, CHANGE_ITEM_EMPLOYMENT);
    }

    @Override
    public void onChangeEmployment(Employment employment) {
        FragmentManager manager = getSupportFragmentManager();
        AddItemEmploymentFragment dialog = AddItemEmploymentFragment.newInstance(employment.getId());
        dialog.show(manager, CHANGE_EMPLOYMENT);
    }
}
