package br.ucsal.trabalho_n2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigScreen extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor, ediot;
    RadioGroup speedGroup, geograficGroup, controlMapGroup, controlTypeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        pref = getSharedPreferences("button_config", Context.MODE_PRIVATE);
        ediot = pref.edit();
        setupRadioGroups();
    }

    private void setupRadioGroups() {
        speedGroup = findViewById(R.id.control_speed);
        geograficGroup = findViewById(R.id.control_geographic);
        controlMapGroup = findViewById(R.id.control_map);
        controlTypeMap = findViewById(R.id.control_mapType);

        findViewById(R.id.config_return).setOnClickListener(this);
        findViewById(R.id.config_save).setOnClickListener(this);

        setupRadioGroupListener(speedGroup, "speed_option");
        setupRadioGroupListener(geograficGroup, "coordinates_option");
        setupRadioGroupListener(controlMapGroup, "map_orientation_option");
        setupRadioGroupListener(controlTypeMap, "map_view_option");

        loadRadioGroupOption(speedGroup, "speed_option", R.id.Km);
        loadRadioGroupOption(geograficGroup, "coordinates_option", R.id.A);
        loadRadioGroupOption(controlMapGroup, "map_orientation_option", R.id.Nothing);
        loadRadioGroupOption(controlTypeMap, "map_view_option", R.id.Vector);
    }

    private void setupRadioGroupListener(final RadioGroup radioGroup, final String preferenceKey) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            ediot.putInt(preferenceKey, checkedId);
            int option = -1;
            if (checkedId == R.id.Km || checkedId == R.id.A || checkedId == R.id.Nothing || checkedId == R.id.Vector) {
                option = 1;
            } else if (checkedId == R.id.m || checkedId == R.id.B || checkedId == R.id.NorthUp || checkedId == R.id.Satellite) {
                option = 2;
            } else if (checkedId == R.id.C || checkedId == R.id.CourseUp) {
                option = 3;
            }
            if (option != -1) {
                editor.putInt(preferenceKey, option);
            }
        });
    }

    private void loadRadioGroupOption(RadioGroup radioGroup, String preferenceKey, int defaultOptionId) {
        int checkedButtonId = pref.getInt(preferenceKey, -1);
        if (checkedButtonId != -1) {
            radioGroup.check(checkedButtonId);
        } else {
            radioGroup.check(defaultOptionId);
            editor.putInt(preferenceKey, 1);
            ediot.putInt(preferenceKey, defaultOptionId);
            ediot.apply();
            editor.apply();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.config_return) {
            finish();
        }
        if (id == R.id.config_save) {
            Toast.makeText(getApplicationContext(), "Options Saved", Toast.LENGTH_SHORT).show();
            ediot.apply();
            editor.apply();
            finish();
        }
    }
}
