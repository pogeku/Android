package br.ucsal.trabalho_n2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;

public class ManageScreen extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_screen);
        dbHelper = new DatabaseHelper(this);

        List<Trail> trails = dbHelper.getAllTrails();
        trails.sort(Comparator.comparing(Trail::getStartDate).reversed());

        RecyclerView recyclerView = findViewById(R.id.list_bg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TrailAdapter adapter = new TrailAdapter(trails, this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.manage_return).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.manage_return) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
