package com.example.mohammedalrabeai.ali2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mohammedalrabeai.ali2.main.presentation.activities.GridLayoutActivity;
import com.example.mohammedalrabeai.ali2.main.presentation.activities.LinearLayoutActivity;
import com.example.mohammedalrabeai.ali2.main.presentation.activities.LinearLayoutHorizontalActivity;
import com.example.mohammedalrabeai.ali2.main.presentation.activities.StaggeredGridLayoutActivity;
//import com.orafaaraujo.rxrecyclerexample.R;

public class MainActivity555 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main555);
    }

    public void linearVertical(View view) {
        startActivity(new Intent(this, LinearLayoutActivity.class));
    }

    public void horizontalVertical(View view) {
        startActivity(new Intent(this, LinearLayoutHorizontalActivity.class));
    }

    public void grid(View view) {
        startActivity(new Intent(this, GridLayoutActivity.class));
    }

    public void staggredGrid(View view) {
        startActivity(new Intent(this, StaggeredGridLayoutActivity.class));
    }

}
