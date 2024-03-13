package com.trikwcc.FirstTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView resultado;
    private EditText primeiro, segundo;

    Double first , second, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Welcome User", Toast.LENGTH_SHORT).show();

        resultado = findViewById(R.id.resultado);
        primeiro = findViewById(R.id.primeiro);
        segundo = findViewById(R.id.segundo);
    }

    public void calculo (View view) {
        try {
            first = Double.parseDouble(primeiro.getText().toString());
            second = Double.parseDouble(segundo.getText().toString());
            result = first + second;

        resultado.setText("Resultado: " + String.valueOf(result));
        } catch (NumberFormatException e) {
            if (first == null || second == null) {
                Toast.makeText(this, "Insert values.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Bye user", Toast.LENGTH_SHORT).show();

    }













}