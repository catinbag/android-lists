package org.demo.lists.rvanimations;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final DemoMode[] modes = {
            new AnimationsMode(), new TouchMode()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout content = findViewById(R.id.content);
        String[] titles = new String[modes.length];
        for (int i = 0; i < modes.length; i++) {
            titles[i] = modes[i].title();
        }

        Spinner spinner = findViewById(R.id.mode_spinner);
        spinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, titles));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                content.removeAllViews();
                content.addView(modes[position].create(getLayoutInflater(), content));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
