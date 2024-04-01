package br.com.trikwcc.prova1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigPage extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPref;
    private EditText usernameText, passwordText, confirmText;
    private String username, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_screen);

        sharedPref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        int errors = sharedPref.getInt("errors", 3);
        if (errors == 0) {
            showToast(getString(R.string.app_lock));
            usernameText.setEnabled(false);
            passwordText.setEnabled(false);
            confirmText.setEnabled(false);
            finish();
        }
        sharedPref = getSharedPreferences("myPrefs", MODE_PRIVATE);

        usernameText = findViewById(R.id.username_config);
        passwordText = findViewById(R.id.password_config);
        confirmText = findViewById(R.id.confirmpss_config);

        Button cancelButton = findViewById(R.id.cancel_config);
        Button leaveButton = findViewById(R.id.leave_config);

        cancelButton.setOnClickListener(this);
        leaveButton.setOnClickListener(this);
    }

    public void createAccount(View view) {
        username = usernameText.getText().toString().trim();
        password = passwordText.getText().toString().trim();
        confirmPassword = confirmText.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            showToast("Nenhum campo pode ficar vazio");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("As senhas estão diferentes");
            return;
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        showToast("Conta criada com sucesso");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.cancel_config){
            usernameText.setText("");
            passwordText.setText("");
            confirmText.setText("");
        }
        if(id == R.id.leave_config){
            Intent c = new Intent (this, MainActivity.class);
            startActivity(c);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
