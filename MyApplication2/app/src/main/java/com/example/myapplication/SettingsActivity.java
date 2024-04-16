package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnBackFromSettings = findViewById(R.id.btnBackFromSettings);


        btnBackFromSettings.setOnClickListener(this);



    }
        @Override
        public void onClick(View view) {
            int id = view.getId();
        if(id == R.id.btnBackFromSettings){

        }
    }
}
