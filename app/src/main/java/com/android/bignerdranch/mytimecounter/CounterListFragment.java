package com.android.bignerdranch.mytimecounter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bignerdranch.mytimecounter.model.DailyEmploymentListLab;
import com.android.bignerdranch.mytimecounter.model.Employment;
import com.android.bignerdranch.mytimecounter.model.EmploymentLab;
import com.android.bignerdranch.mytimecounter.model.ListEmploymentsItem;

import java.util.GregorianCalendar;
import java.util.List;

public class CounterListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private CountAdapter mCountAdapter;
    private FloatingActionButton fab;
    private Callbacks mCallbacks;
    private int position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    public void updateUI() {
        List<ListEmploymentsItem> items = EmploymentLab.getInstance().getListEmploymentItems();
        List<Employment> employments = EmploymentLab.getInstance().getEmployments();
        if (mCountAdapter == null) {
            mCountAdapter = new CountAdapter(items);
            mRecyclerView.setAdapter(mCountAdapter);
        } else {
            mCountAdapter.setItems(items, employments);
            mCountAdapter.notifyDataSetChanged();
        }

    }

    public interface Callbacks {
        void onEmploymentSelected(Employment employment);

        void onNewEmployment(Employment employment);


        void onChangeEmployment(Employment employment);

        void onNewItemEmployments();

        void onChangeItemEmployments(String itemTitle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_count_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_statistic:
                DailyEmploymentListLab.getInstance().updateLists();
                Intent intent = new Intent(getActivity(), StatisticPagerActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count_list, container, false);
        mRecyclerView = view.findViewById(R.id.count_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> mCallbacks.onNewItemEmployments());
        updateUI();
        return view;

    }


    private class CountAdapter extends RecyclerView.Adapter<CountHolder> {
        private List<ListEmploymentsItem> mListEmploymentItems;

        //Change!

        public CountAdapter(List<ListEmploymentsItem> items) {
            mListEmploymentItems = items;
        }

        @NonNull
        @Override
        public CountHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_count,
                    viewGroup, false);

            return new CountHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CountHolder countHolder, int i) {
            /*Employment employment = mEmployments.get(i);
            countHolder.bind(employment);*/


            //Change this code!!!!!!!!!!!

            ListEmploymentsItem item = mListEmploymentItems.get(i);
            countHolder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mListEmploymentItems.size();
        }

        public void setItems(List<ListEmploymentsItem> items, List<Employment> employments) {
            mListEmploymentItems = items;
        }
    }

    private class CountHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private Employment mEmployment;
        private ListEmploymentsItem mItem;

        private TextView mTitleTextView;
        private TextView mTotalTimeTextView;
        private View mView;

        public CountHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

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


        public void bind(ListEmploymentsItem item) {
            mItem = item;

            mView.setBackgroundColor(item.getColor());
            mTitleTextView.setText(item.getTitle());
//            mTotalTimeTextView.setText(TimeHelper.getTime(0));

            if (item.isHasEmployment()) {
                for (Employment employment : EmploymentLab.getInstance().getEmployments()
                ) {
                    if (employment.getTitle().equals(item.getTitle()) &&
                            TimeHelper.compareDate(employment, new GregorianCalendar())) {
                        mEmployment = employment;
                        mTotalTimeTextView.setText(TimeHelper.getTime(mEmployment.getTimeInt()));
                    }
                }
            } else
                mTotalTimeTextView.setText(TimeHelper.getTime(0));

        }

        @Override
        public void onClick(View v) {
            mItem.setHasEmployment(true);
            mEmployment = EmploymentLab.getInstance().getEmployment(mItem);
            mCallbacks.onEmploymentSelected(mEmployment);
            updateUI();
            position = getAdapterPosition();
        }

        @SuppressLint("NewApi")
        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(getActivity(), "LongClicked", Toast.LENGTH_LONG).show();
            PopupMenu popupMenu = new PopupMenu(getActivity(), v);
            popupMenu.inflate(R.menu.item_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete:
                            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();
                            EmploymentLab.getInstance().deleteItem(mItem);
                            updateUI();
                            break;
                        case R.id.menu_item_change:
                            Toast.makeText(getActivity(), "Changed", Toast.LENGTH_LONG).show();
                            mCallbacks.onChangeItemEmployments(mItem.getTitle());
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
