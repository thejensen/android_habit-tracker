package com.example.guest.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.button) Button mButton1;
    @Bind(R.id.button2) Button mButton2;
    @Bind(R.id.button3) Button mButton3;
    @Bind(R.id.button4) Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ActivitiesActivity.class);
        switch(view.getId()){
            case R.id.button:
                intent.putExtra("motivation", 1);
                break;
            case R.id.button2:
                intent.putExtra("motivation", 2);
                break;
            case R.id.button3:
                intent.putExtra("motivation", 3);
                break;
            case R.id.button4:
                intent.putExtra("motivation", 4);
                break;
        }
        startActivity(intent);
    }
}
