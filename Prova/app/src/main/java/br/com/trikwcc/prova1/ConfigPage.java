package br.com.trikwcc.prova1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigPage extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private EditText usernameText, passwordText, confirmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_screen);

        usernameText = findViewById(R.id.username_config);
        passwordText = findViewById(R.id.password_config);
        confirmText = findViewById(R.id.confirmpss_config);
    }

    public void createAccount(View view) {
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String confirmPassword = confirmText.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            showToast("Nenhum campo pode ficar vazio");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("As senha está incorreta");
            return;
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        showToast("Conta criada com sucesso");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
