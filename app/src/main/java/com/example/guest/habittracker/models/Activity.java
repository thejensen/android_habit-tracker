package com.example.guest.habittracker.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 12/13/16.
 */
public class Activity {
    String name;
    int weeklyGoal;
    int motivationLevel;
    String pushId;
    List<String> dates;

    public Activity() {
    }

    public Activity(String name, int weeklyGoal, int motivationLevel) {
        this.name = name;
        this.weeklyGoal = weeklyGoal;
        this.motivationLevel = motivationLevel;
        this.dates = new ArrayList<>();
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return pushId != null ? pushId.equals(activity.pushId) : activity.pushId == null;

    }

    @Override
    public int hashCode() {
        return pushId != null ? pushId.hashCode() : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeeklyGoal() {
        return weeklyGoal;
    }

    public void setWeeklyGoal(int weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }

    public int getMotivationLevel() {
        return motivationLevel;
    }

    public void setMotivationLevel(int motivationLevel) {
        this.motivationLevel = motivationLevel;
    }

}
