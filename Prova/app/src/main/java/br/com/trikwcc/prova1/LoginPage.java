package br.com.trikwcc.prova1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private int errors;
    private SharedPreferences sharedPref;
    private EditText usernametxt, passwordtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        sharedPref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        usernametxt = findViewById(R.id.Username);
        passwordtxt = findViewById(R.id.Password);
    }

    public void checkAccount(View view) {
        String usuarioSalvo = sharedPref.getString("username", "");
        String senhaSalva = sharedPref.getString("password", "");

        String username = usernametxt.getText().toString().trim();
        String password = passwordtxt.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Nenhum campo pode ficar vazio");
            return;
        }

        if (!password.equals(senhaSalva)) {
            showToast("Senha inválida");
            errors++;
            if (errors == 3) {
                lockApp();
            }
            return;
        }

        if (username.equals(usuarioSalvo) && password.equals(senhaSalva)) {
            showToast("Login aceito");
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void lockApp() {

    }
}
