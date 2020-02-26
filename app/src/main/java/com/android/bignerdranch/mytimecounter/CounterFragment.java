package com.android.bignerdranch.mytimecounter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bignerdranch.mytimecounter.model.Employment;
import com.android.bignerdranch.mytimecounter.model.EmploymentLab;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class CounterFragment extends Fragment {
    private Employment mEmployment;
    private static final String ARG_EMPLOYMENT_ID = "employment_id";

    private TextView mEmploymentTextView;
    private TextView mTotalTimeTextView;
    private TextView mCurrentTimeTextView;
    private Callbacks mCallbacks;
    private Activity mActivity;

    private int currentTime;
    private int totalTime;


    private MyTimerTask mTimerTask;
    private Timer mTimer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
        mActivity = (Activity) context;
    }

    public interface Callbacks {
        void onEmploymentUpdated(Employment employment);
    }

    public static CounterFragment newInstance(UUID employmentID) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_EMPLOYMENT_ID, employmentID);

        CounterFragment fragment = new CounterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID employmentID = (UUID) getArguments().getSerializable(ARG_EMPLOYMENT_ID);
        mEmployment = EmploymentLab.getInstance(getContext()).getEmployment(employmentID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter, container, false);

        totalTime = mEmployment.getTimeInt();
        currentTime = 0;

        mEmploymentTextView = view.findViewById(R.id.counterTitleTextView);
        mTotalTimeTextView = view.findViewById(R.id.counterTotalTimeTextView);
        mCurrentTimeTextView = view.findViewById(R.id.counterCurrentTimeTextView);

//        mEmploymentTextView.setText(mEmployment.getTitle());
//        mTotalTimeTextView.setText(getTime(mEmployment.getTimeInt()));
        mEmploymentTextView.setText(mEmployment.getTitle());
        mTotalTimeTextView.setText(mActivity.getString(R.string.total_time_is, TimeHelper.getTime(totalTime + currentTime)));
        mCurrentTimeTextView.setText(mActivity.getString(R.string.current_time_is, TimeHelper.getTime(currentTime++)));

        mTimer = new Timer();
        mTimerTask = new MyTimerTask();
        mTimer.scheduleAtFixedRate(mTimerTask, 0, 1000);

        return view;
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mTotalTimeTextView.setText(mActivity.getString(R.string.total_time_is, TimeHelper.getTime(totalTime + currentTime)));
                    mCurrentTimeTextView.setText(mActivity.getString(R.string.current_time_is, TimeHelper.getTime(currentTime++)));
                }
            });
        }
    }


    @Override
    public void onPause() {
        super.onPause();
//        mCallbacks.onEmploymentUpdated(mEmployment);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mEmployment.setTimeInt(totalTime + currentTime);
//        mCallbacks.onEmploymentUpdated(mEmployment);
        mCallbacks = null;
    }
}
