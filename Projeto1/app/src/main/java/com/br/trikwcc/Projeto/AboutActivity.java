package com.br.trikwcc.Projeto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_second);

        Button buttonReturn = findViewById(R.id.Return_About);
        buttonReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.Return_About) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
