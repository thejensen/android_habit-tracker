package com.example.guest.habittracker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.guest.habittracker.R;
import com.example.guest.habittracker.models.Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Guest on 12/13/16.
 */
public class ActivityExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<Activity> activityList;
    private int itemLayoutId;
    private int groupLayoutId;
    private Context mContext;
    private Activity activity;
    private String mUserId;
    private String mDate;
    private List<Activity> todaysActivityList = new ArrayList<>();
    private DatabaseReference mDateRef;

    public ActivityExpandableListViewAdapter(List<Activity> activityList, Context context, String userId){
        itemLayoutId = R.layout.expanded_activity_list_item;
        groupLayoutId = android.R.layout.simple_expandable_list_item_1;
        this.activityList = activityList;
        mContext = context;
        mUserId = userId;
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        mDate = dateFormat.format(Calendar.getInstance().getTime());
        mDateRef = FirebaseDatabase.getInstance().getReference("users").child(mUserId).child("dates").child(mDate);
        mDateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Activity doneActivity = snapshot.getValue(Activity.class);
                    todaysActivityList.add(doneActivity);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getGroupCount() {
        return activityList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return activityList.get(i).getName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return activityList.get(groupPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(groupLayoutId, viewGroup, false);
        }

        TextView activityTitleView = (TextView) v.findViewById(android.R.id.text1);
        Activity activity = activityList.get(i);
        activityTitleView.setText(activity.getName());

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(itemLayoutId, parent, false);
        }
        activity = activityList.get(groupPosition);
        TextView weeklyGoalView = (TextView) v.findViewById(R.id.weeklyGoalTextView);
        weeklyGoalView.setText("Your Weekly Goal: " + activity.getWeeklyGoal() + " times");
        CheckBox doneCheckBox = (CheckBox) v.findViewById(R.id.doneCheckBox);
        Log.i("adapter", "getChildView: " + todaysActivityList.contains(activity));
        if(todaysActivityList.contains(activity)){
            doneCheckBox.setChecked(true);
        } else {
            doneCheckBox.setChecked(false);
        }
        doneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mDateRef.child(activity.getPushId()).setValue(activity);
                    todaysActivityList.add(activity);
                } else {
                    mDateRef.child(activity.getPushId()).removeValue();
                    todaysActivityList.remove(activity);
                }
            }
        });
        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
