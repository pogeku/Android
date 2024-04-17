package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRegisterTrail = findViewById(R.id.btnRegisterTrail);
        Button btnManageTrail = findViewById(R.id.btnManageTrail);
        Button btnShareTrail = findViewById(R.id.btnShareTrail);
        Button btnSettings = findViewById(R.id.btnSettings);
        Button btnAbout = findViewById(R.id.btnAbout);

        btnRegisterTrail.setOnClickListener(this);
        btnManageTrail.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnShareTrail.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btnAbout){
            Intent a = new Intent (this, AboutActivity.class);
            startActivity(a);
        }

        if(id == R.id.btnRegisterTrail){
            Intent b = new Intent (this, RegisterTrailActivity.class);
            startActivity(b);
        }

        /*if(id == R.id.btnShareTrail){
            Intent c = new Intent (this, AboutActivity.class);
            startActivity(c);
        }*/

        /*if(id == R.id.btnSettings){
            Intent d = new Intent (this, .class);
            startActivity(d);
        } */

        if(id == R.id.btnManageTrail){
            Intent e = new Intent (this, AboutActivity.class);
            startActivity(e);
        }

    }





}
