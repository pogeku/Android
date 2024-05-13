package br.com.trikwcc.Trabalho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    Button btn_register, btn_config, btn_retorn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_screen);
        startButtons();
    }

    private void startButtons(){
        btn_register = findViewById(R.id.manage_register);
        btn_config = findViewById(R.id.manage_config);
        btn_retorn = findViewById(R.id.manage_return);

        btn_register.setOnClickListener(this);
        btn_config.setOnClickListener(this);
        btn_retorn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.manage_register){}
        if(id == R.id.manage_config){startActivity(new Intent(this, ConfigScreen.class));}
        if(id == R.id.manage_return){finish();}
    }
}
