package net.foxandr.sport.universiade.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import net.foxandr.sport.universiade.R;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));


    }
}