package br.com.trikwcc.prova1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Button loginButton = (Button) findViewById(R.id.login);
        Button configButton = (Button) findViewById(R.id.config);
        Button leaveButton = (Button) findViewById(R.id.leave);

        loginButton.setOnClickListener(this);
        configButton.setOnClickListener(this);
        leaveButton.setOnClickListener(this);
    }

    public void onClick (View view){
        int id = view.getId();
        if(id == R.id.login){
            Intent a = new Intent (this, LoginPage.class);
            startActivity(a);
        }
        if(id == R.id.config){
            Intent b = new Intent (this, ConfigPage.class);
            startActivity(b);
        }
        if(id == R.id.leave){finish();}
            finish();
    }

}