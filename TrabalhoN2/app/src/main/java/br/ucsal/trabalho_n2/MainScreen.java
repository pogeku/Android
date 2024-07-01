package br.ucsal.trabalho_n2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        findViewById(R.id.path_register).setOnClickListener(this);
        findViewById(R.id.path_manage).setOnClickListener(this);
        findViewById(R.id.path_settings).setOnClickListener(this);
        findViewById(R.id.path_about).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.path_register) {
            startActivity(new Intent(this, TrailControl.class));
        }
        if (view.getId() == R.id.path_settings) {
            startActivity(new Intent(this, ConfigScreen.class));
        }
        if (view.getId() == R.id.path_about) {
            startActivity(new Intent(this, CreditScreen.class));
        }
        if (view.getId() == R.id.path_manage) {
            startActivity(new Intent(this, ManageScreen.class));
        }
    }

}
