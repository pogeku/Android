package br.com.trikwcc.Trabalho;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigScreen extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private SharedPreferences saved;
    private SharedPreferences.Editor editor;

    private boolean changesMade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saved = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        setContentView(R.layout.config_screen);
        editor = saved.edit();

        setupRadioGroups();
        loadSavedOptions();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId != -1) {
            changesMade = true;
            if (checkedId == R.id.km) {
                editor.putInt("speed_option", 1);
            }
            if (checkedId == R.id.m) {
                editor.putInt("speed_option", 2);
            }

            if (checkedId == R.id.A) {
                editor.putInt("coordinates_option", 1);
            }
            if (checkedId == R.id.B) {
                editor.putInt("coordinates_option", 2);
            }
            if (checkedId == R.id.C) {
                editor.putInt("coordinates_option", 3);
            }

            if (checkedId == R.id.CourseUp) {
                editor.putInt("map_orientation_option", 1);
            }
            if (checkedId == R.id.NorthUp) {
                editor.putInt("map_orientation_option", 2);
            }
            if (checkedId == R.id.Nothing) {
                editor.putInt("map_orientation_option", 3);
            }

            if (checkedId == R.id.Vector) {
                editor.putInt("map_view_option", 1);
            }
            if (checkedId == R.id.Satellite) {
                editor.putInt("map_view_option", 2);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.config_return) {
            if (changesMade) {
                showToast("Changes not saved!");
            }
            finish();
        }

        if (view.getId() == R.id.config_save) {
            if (changesMade) {
                editor.apply();
                showToast("Options Saved");
            } else {
                showToast("No changes to save");
            }
            finish();
        }
    }

    private void setupRadioGroups() {
        RadioGroup speed = findViewById(R.id.control_speed);
        RadioGroup coordinates = findViewById(R.id.control_geographic);
        RadioGroup mapOrientation= findViewById(R.id.control_map);
        RadioGroup mapView = findViewById(R.id.control_mapType);
        Button returnButton = findViewById(R.id.config_return);
        Button saveButton = findViewById(R.id.config_save);

        returnButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        speed.setOnCheckedChangeListener(this);
        coordinates.setOnCheckedChangeListener(this);
        mapOrientation.setOnCheckedChangeListener(this);
        mapView.setOnCheckedChangeListener(this);
    }

    private void loadSavedOptions() {
        loadRadioGroupOption(R.id.control_speed, "speed_option");
        loadRadioGroupOption(R.id.control_geographic, "coordinates_option");
        loadRadioGroupOption(R.id.control_map, "map_orientation_option");
        loadRadioGroupOption(R.id.control_mapType, "map_view_option");
    }

    private void loadRadioGroupOption(int radioGroupId, String preferenceKey) {
        int defaultValue = 1;
        int selectedOption = saved.getInt(preferenceKey, defaultValue);
        RadioGroup radioGroup = findViewById(radioGroupId);
        if (selectedOption != defaultValue) {
            radioGroup.check(selectedOption);
        } else {
            ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
