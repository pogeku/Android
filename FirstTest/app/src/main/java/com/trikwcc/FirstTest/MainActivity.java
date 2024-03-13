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
    private EditText primeiro;
    private EditText segundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Hello user", Toast.LENGTH_SHORT).show();

        resultado = findViewById(R.id.resultado);
        primeiro = findViewById(R.id.primeiro);
        segundo = findViewById(R.id.segundo);


    }

    public void calculo (View view) {
        try {
            double first = Double.parseDouble(primeiro.getText().toString());
            double second = Double.parseDouble(segundo.getText().toString());
            double result = first + second;

            resultado.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
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