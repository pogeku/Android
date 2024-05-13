package br.com.trikwcc.Trabalho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreditsScreen extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits_screen);

        Button retorne = findViewById(R.id.returnate);
        retorne.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.returnate) {
            Intent a = new Intent(this, MainScreen.class);
            startActivity(a);
        }
    }
}
