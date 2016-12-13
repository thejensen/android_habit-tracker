package com.example.guest.habittracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.guest.habittracker.R;
import com.example.guest.habittracker.models.Activity;

import java.util.List;

/**
 * Created by Guest on 12/13/16.
 */
public class ActivityExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<Activity> activityList;
    private int itemLayoutId;
    private int groupLayoutId;
    private Context mContext;

    public ActivityExpandableListViewAdapter(List<Activity> activityList, Context context){
        itemLayoutId = R.layout.expanded_activity_list_item;
        groupLayoutId = android.R.layout.simple_expandable_list_item_1;
        this.activityList = activityList;
        mContext = context;
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
        Activity activity = activityList.get(groupPosition);
        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
