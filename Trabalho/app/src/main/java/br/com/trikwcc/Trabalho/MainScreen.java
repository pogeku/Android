package br.com.trikwcc.Trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences sharedPrefs;
    SharedPreferences.Editor sharedPrefsEditor;
    Button btn_register, btn_share, btn_manage, btn_settings, btn_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        sharedPrefs = getSharedPreferences("trail_info", MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();

        btn_register = findViewById(R.id.path_register);
        btn_share = findViewById(R.id.path_share);
        btn_manage = findViewById(R.id.path_manage);
        btn_settings = findViewById(R.id.path_settings);
        btn_about = findViewById(R.id.path_about);

        btn_register.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_manage.setOnClickListener(this);
        btn_settings.setOnClickListener(this);
        btn_about.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.path_register) {
            Intent a = new Intent(this, TrailControl.class);
            startActivity(a);
        }
        if (id == R.id.path_settings) {
            Intent b = new Intent(this, ConfigScreen.class);
            startActivity(b);
        }
        if (id == R.id.path_about) {
            Intent c = new Intent(this, CreditsScreen.class);
            startActivity(c);
        }
        if (id == R.id.path_manage) {
            Intent d = new Intent(this, RegisterScreen.class);
            startActivity(d);
        }
        if (id == R.id.path_share) {

            String trail_info = sharedPrefs.getString("local", "");
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", trail_info);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
        }

    }

}
