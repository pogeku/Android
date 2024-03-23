package com.br.trikwcc.Projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button ButtonGender = (Button) findViewById(R.id.button_gender);
        Button ButtonAbout = (Button) findViewById(R.id.button_about);
        Button ButtonLeave = (Button) findViewById(R.id.button_leave);

        ButtonGender.setOnClickListener(this);
        ButtonAbout.setOnClickListener(this);
        ButtonLeave.setOnClickListener(this);
    }

    public void onClick (View view) {
        int id = view.getId();
        if (id == R.id.button_gender) {
            Intent a = new Intent(this, GenActivity.class);
            startActivity(a);
        }
        if (id == R.id.button_about) {
            Intent i = new Intent(this,AboutActivity.class);
            startActivity(i);
        }
        if (id == R.id.button_leave) {
            finish();

        }
    }

}