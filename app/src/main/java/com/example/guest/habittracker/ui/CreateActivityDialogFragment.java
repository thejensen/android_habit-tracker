package com.example.guest.habittracker.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.guest.habittracker.R;
import com.example.guest.habittracker.models.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Guest on 12/13/16.
 */
public class CreateActivityDialogFragment extends DialogFragment {
    private SharedPreferences mSharedPreferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Enter the Activity to add")
                .setView(inflater.inflate(R.layout.dialog_add_activity, null))
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        EditText activityNameInput = (EditText) getDialog().findViewById(R.id.activityNameEditText);
                        EditText goalInput = (EditText) getDialog().findViewById(R.id.goalEditText);
                        int motivation = mSharedPreferences.getInt("motivation", 4);
                        Activity activity = new Activity(activityNameInput.getText().toString(), Integer.parseInt(goalInput.getText().toString()), motivation);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userId).child("activities").push();
                        activity.setPushId(ref.getKey());
                        ref.setValue(activity);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
