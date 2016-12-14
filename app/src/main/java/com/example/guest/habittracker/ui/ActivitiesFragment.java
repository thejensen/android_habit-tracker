package com.example.guest.habittracker.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.guest.habittracker.R;
import com.example.guest.habittracker.adapters.ActivityExpandableListViewAdapter;
import com.example.guest.habittracker.models.Activity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitiesFragment extends Fragment {
    @Bind(R.id.addActivityButton)
    Button mAddActivityButton;
    private SharedPreferences mSharedPreferences;
    private ArrayList<Activity> mActivities = new ArrayList<>();
    private ActivityExpandableListViewAdapter mAdapter;
    @Bind(R.id.expandableListView)
    ExpandableListView mExpandableListView;

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instructs fragment to include menu options:
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_activities, container, false);
        ButterKnife.bind(this, view);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int motivation = mSharedPreferences.getInt("motivation", 4);
        mAdapter = new ActivityExpandableListViewAdapter(mActivities, getActivity());
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        Query activityRef = FirebaseDatabase.getInstance().getReference("activities").orderByChild("motivationLevel").equalTo(motivation);
        activityRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mActivities.add(dataSnapshot.getValue(Activity.class));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAddActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new CreateActivityDialogFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "add");
            }
        });
        return view;
    }

}
