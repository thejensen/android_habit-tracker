package com.example.guest.habittracker.models;

/**
 * Created by Guest on 12/13/16.
 */
public class Activity {
    String name;
    int weeklyGoal;
    int motivationLevel;
    String pushId;
    String notes;
    int emotion;

    public Activity() {
    }

    public Activity(String name, int weeklyGoal, int motivationLevel) {
        this.name = name;
        this.weeklyGoal = weeklyGoal;
        this.motivationLevel = motivationLevel;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getEmotion() {
        return emotion;
    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }
}
