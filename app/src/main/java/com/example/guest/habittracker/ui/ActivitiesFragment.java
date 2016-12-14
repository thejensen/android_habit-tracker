package com.example.guest.habittracker.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.guest.habittracker.R;
import com.example.guest.habittracker.adapters.ActivityExpandableListViewAdapter;
import com.example.guest.habittracker.models.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * TODO: hook up expanded tracker of the habit to database
 * TODO: calendar
 * TODO: make it look less terrible
 * TODO: do the animation/fragment stuff they actually want us to do
 * TODO: color code by motivation level -> darker color = more things accomplished for that day
 */
public class ActivitiesFragment extends Fragment{
    private static final String TAG = ActivitiesFragment.class.getSimpleName();
    @Bind(R.id.addActivityButton)
    Button mAddActivityButton;
    private SharedPreferences mSharedPreferences;
    private ArrayList<Activity> mActivities = new ArrayList<>();
    private ActivityExpandableListViewAdapter mAdapter;
    @Bind(R.id.expandableListView)
    ExpandableListView mExpandableListView;
    private String mUserId;
    private Query mActivityRef;
    private ChildEventListener mchildEventListener;

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
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAdapter = new ActivityExpandableListViewAdapter(mActivities, getActivity(), mUserId);
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                Log.i(TAG, "onGroupCollapse: ");
                InputMethodManager inputManager = (InputMethodManager) getActivity().getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (mExpandableListView.getFocusedChild() != null) {
                    Log.i(TAG, "onGroupCollapse: focused child not null");
                    inputManager.hideSoftInputFromWindow(mExpandableListView.getFocusedChild().getWindowToken(), 0);
                    mExpandableListView.getFocusedChild().clearFocus();
                }
            }
        });

        mActivityRef = FirebaseDatabase.getInstance().getReference("users").child(mUserId).child("activities").orderByChild("motivationLevel").equalTo(motivation);
        mchildEventListener = mActivityRef.addChildEventListener(new ChildEventListener() {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivityRef.removeEventListener(mchildEventListener);
    }
}
