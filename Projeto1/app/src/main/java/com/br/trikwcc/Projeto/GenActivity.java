package com.br.trikwcc.Projeto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GenActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private RadioButton radMans, radWoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genselect);

        sharedPrefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        radMans = findViewById(R.id.radMan);
        radWoms = findViewById(R.id.radWom);

        boolean isMale = sharedPrefs.getBoolean("IsMale", true); // Valor padrão é true (masculino)
        radMans.setChecked(isMale);
        radWoms.setChecked(!isMale);

        radMans.setOnCheckedChangeListener(this);
        radWoms.setOnCheckedChangeListener(this);

        Button buttonReturne = findViewById(R.id.Return_Select);
        buttonReturne.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        sharedPrefsEditor = sharedPrefs.edit();
        if (buttonView.getId() == R.id.radMan && isChecked) {
                sharedPrefsEditor.putBoolean("IsMale", true);
            Toast.makeText(this, "Você escolheu Masculino", Toast.LENGTH_SHORT).show();
        } else if (buttonView.getId() == R.id.radWom && isChecked) {
            sharedPrefsEditor.putBoolean("IsMale", false);
            Toast.makeText(this, "Você escolheu Feminino", Toast.LENGTH_SHORT).show();
        }
        sharedPrefsEditor.apply();
    }

    @Override
    public void onClick (View view) {
        int id = view.getId();
        if (id == R.id.Return_Select) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}