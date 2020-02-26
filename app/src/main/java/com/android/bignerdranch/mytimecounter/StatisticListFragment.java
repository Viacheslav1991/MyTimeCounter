package com.android.bignerdranch.mytimecounter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.bignerdranch.mytimecounter.model.DailyEmploymentListLab;
import com.android.bignerdranch.mytimecounter.model.DailyEmploymentsList;
import com.android.bignerdranch.mytimecounter.model.Employment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class StatisticListFragment extends Fragment {
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "DialogDate";


    private RecyclerView mRecyclerView;
    private DailyEmploymentsList mDailyEmploymentsList;
    private Button mDateButton;
    private Callbacks mCallbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    public interface Callbacks {
        void onDateSelected(Integer i);
    }

    private static final String ARG_EMPLOYMENT_LIST_DATE = "employment_list_date";

    public static StatisticListFragment newInstance(String date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_EMPLOYMENT_LIST_DATE, date);

        StatisticListFragment fragment = new StatisticListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_list, container, false);

        mRecyclerView = view.findViewById(R.id.statistic_recycler_view);
        mDateButton = view.findViewById(R.id.select_date_button);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String date = (String) getArguments().getSerializable(ARG_EMPLOYMENT_LIST_DATE);
        mDateButton.setText(date);
        mDateButton.setOnClickListener(v -> {
            /*Intent intent = DatePickerActivity.newIntent(getActivity(), new Date());
            startActivityForResult(intent, REQUEST_DATE);*/
            FragmentManager manager = getFragmentManager();
            DatePickerFragment dialog = new DatePickerFragment();
            dialog.setTargetFragment(StatisticListFragment.this, REQUEST_DATE);
            dialog.show(manager, DIALOG_DATE);
        });
        mDailyEmploymentsList = DailyEmploymentListLab.getInstance(getActivity()).getDailyEmploymentsList(date);
        mRecyclerView.setAdapter(new LineAdapter(mDailyEmploymentsList.getEmployments()));
        return view;
    }

    private class LineAdapter extends RecyclerView.Adapter<StatisticListFragment.LineHolder> {
        private List<Employment> mEmployments;

        //Change!

        public LineAdapter(List<Employment> employments) {
            mEmployments = employments;
        }

        @NonNull
        @Override
        public StatisticListFragment.LineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_count,
                    viewGroup, false);

            return new StatisticListFragment.LineHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull LineHolder lineHolder, int i) {
            Employment employment = mEmployments.get(i);
            lineHolder.bind(employment);
        }

        @Override
        public int getItemCount() {
            return mEmployments.size();
        }


    }

    private class LineHolder extends RecyclerView.ViewHolder {
        private Employment mEmployment;

        private TextView mTitleTextView;
        private TextView mTotalTimeTextView;
        private View mView;

        public LineHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mTitleTextView = itemView.findViewById(R.id.employment_title);
            mTotalTimeTextView = itemView.findViewById(R.id.employment_time_text_view);
        }

        public void bind(Employment employment) {
            mEmployment = employment;
            mView.setBackgroundColor(mEmployment.getColor());
            mTitleTextView.setText(employment.getTitle());
            mTotalTimeTextView.setText(TimeHelper.getTime(employment.getTimeInt()));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            for (int i = 0; i < DailyEmploymentListLab.getInstance(getActivity()).getSize(); i++) {
                DailyEmploymentsList list = DailyEmploymentListLab.getInstance(getActivity()).getDailyEmploymentsList(i);
                if (TimeHelper.compareDate(list, calendar)) {
                    mCallbacks.onDateSelected(i);
                }
            }

        }
    }
}
