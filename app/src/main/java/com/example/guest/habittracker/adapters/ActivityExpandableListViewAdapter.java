package com.example.guest.habittracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.guest.habittracker.R;
import com.example.guest.habittracker.models.Activity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public ActivityExpandableListViewAdapter(List<Activity> activityList, Context context, String userId){
        itemLayoutId = R.layout.expanded_activity_list_item;
        groupLayoutId = android.R.layout.simple_expandable_list_item_1;
        this.activityList = activityList;
        mContext = context;
        mUserId = userId;
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
        doneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                String date = dateFormat.format(Calendar.getInstance().getTime());
                DatabaseReference dateRef = FirebaseDatabase.getInstance().getReference("users").child(mUserId).child("dates").child(date);
                if(b){
                    dateRef.child(activity.getPushId()).setValue("true");
                } else {
                    dateRef.child(activity.getPushId()).removeValue();
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
