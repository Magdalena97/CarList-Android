package com.example.carlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    //fragment w orientacji pionowej
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        detailsFragment.setCar((Car) getIntent().getSerializableExtra("car"));
    }
}
