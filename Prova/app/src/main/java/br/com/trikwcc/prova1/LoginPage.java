package br.com.trikwcc.prova1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    private int errors = 3;
    private SharedPreferences sharedPref;
    private EditText usernametxt, passwordtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        if (errors == 0) {
            lockApp();
            finish();
        }

        sharedPref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        usernametxt = findViewById(R.id.Username);
        passwordtxt = findViewById(R.id.Password);

        Button clearButton = findViewById(R.id.cancel_login);
        Button configButton = findViewById(R.id.config_login);
        Button loginButton = findViewById(R.id.login_button);

        clearButton.setOnClickListener(this);
        configButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.login_button) {
            checkAccount();
        }
        if (id == R.id.cancel_login) {
            clearFields();
        }
        if (id == R.id.config_login) {
            Intent c = new Intent (this, ConfigPage.class);
            startActivity(c);
        }
    }

    private void checkAccount() {
        if (errors == 0) {
            lockApp();
            finish();
            return;
        }

        String usuarioSalvo = sharedPref.getString("username", "");
        String senhaSalva = sharedPref.getString("password", "");

        String username = usernametxt.getText().toString().trim();
        String password = passwordtxt.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Nenhum campo pode ficar vazio");
            return;
        }

        if (!password.equals(senhaSalva)) {
            showToast("Senha inválida: " + errors);
            errors--;
        }

        if (username.equals(usuarioSalvo) && password.equals(senhaSalva)) {
            showToast("Login aceito");
            errors = 3;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("errors", errors);
            editor.apply();
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    private void clearFields() {
        usernametxt.setText("");
        passwordtxt.setText("");
    }

    private void lockApp() {
        showToast(getString(R.string.app_lock));
        usernametxt.setEnabled(false);
        passwordtxt.setEnabled(false);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("errors", errors);
        editor.apply();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
