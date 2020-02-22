package com.android.bignerdranch.mytimecounter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.bignerdranch.mytimecounter.model.Employment;
import com.android.bignerdranch.mytimecounter.model.EmploymentLab;
import com.android.bignerdranch.mytimecounter.model.ListEmploymentsItem;

import java.util.UUID;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddItemEmploymentFragment extends DialogFragment {
    private static final String ARG_EMPLOYMENT_ID = "employment_id";
    private static final String ARG_ITEM_EMPLOYMENT_TITLE = "employment_title";
    private static final String TAG = "AddItemEmploymentFragment";

    private Employment mEmployment;
    private ListEmploymentsItem mListEmploymentsItem;

    private Button selectColorButton;
    private ColorPicker colorPicker;
    private EditText mTitleField;
    private Callbacks mCallbacks;
    private boolean change = false;

    public static AddItemEmploymentFragment newInstance() {
        return new AddItemEmploymentFragment();
    }

    public static AddItemEmploymentFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_EMPLOYMENT_TITLE, title);

        AddItemEmploymentFragment fragment = new AddItemEmploymentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddItemEmploymentFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EMPLOYMENT_ID, uuid);

        AddItemEmploymentFragment fragment = new AddItemEmploymentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    public interface Callbacks {
        void onEmploymentUpdated(Employment employment);

        void onItemEmploymentUpdated();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*UUID employmentId = (UUID) getArguments().getSerializable(ARG_EMPLOYMENT_ID);
        mEmployment = EmploymentLab.getInstance().getEmployment(employmentId);*/

        if (getArguments() != null && getArguments().getSerializable(ARG_ITEM_EMPLOYMENT_TITLE) != null) {
            String title = (String) getArguments().getSerializable(ARG_ITEM_EMPLOYMENT_TITLE);
            mListEmploymentsItem = EmploymentLab.getInstance()
                    .getItemListEmployment(title);
            change = true;
        } else {
            mListEmploymentsItem = new ListEmploymentsItem();
        }


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_new_employment, null);


        mTitleField = view.findViewById(R.id.enter_name_edit_text);
        mTitleField.setText(mListEmploymentsItem.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mListEmploymentsItem.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        selectColorButton = view.findViewById(R.id.select_color_button);
        if (change) {
            selectColorButton.setBackgroundColor(mListEmploymentsItem.getColor());
        }
        selectColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPicker.show();
            }
        });

        colorPicker = new ColorPicker(getActivity());
        final int[] colors = getResources().getIntArray(R.array.default_colors);
        colorPicker
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onChooseColor(int position, int color) {
                        Log.i(TAG, "Position " + position);
                        Log.i(TAG, "Color " + color);
                        Log.i(TAG, "Color from array" + colors[position]);
                        selectColorButton.setBackgroundColor(color);
                        mListEmploymentsItem.setColor(color);
                    }

                    @Override
                    public void onCancel() {

                    }
                });


        return new AlertDialog.Builder(getActivity())
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListEmploymentsItem.getTitle() == null) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("You should enter title!")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .create()
                                    .show();

                        } else if (!change) {
                            EmploymentLab.getInstance().addItemListEmployment(mListEmploymentsItem);
                        }
                    }
                })
                .setView(view)
                .create();
    }

    private void updateEmployment() {
        mCallbacks.onItemEmploymentUpdated();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        updateEmployment();
        mCallbacks = null;
    }
}
