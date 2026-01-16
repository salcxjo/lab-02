package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;
    private int selectedPosition = -1;
    private EditText cityNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        cityNameInput = findViewById(R.id.add_city_editText);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Part of Gemini's response to my delete_city prompt
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityNameInput.setText(dataList.get(position));
        });

        // Add Button setup
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            AddbuttonClicked(v);
        });

        // Delete Button setup
        Button deleteButton = findViewById(R.id.delete_city);
        deleteButton.setOnClickListener(v -> {
            DeletebuttonClicked(v);
        });
    }

    // The template of the following function is from Gemini,
    // "make a function for the add button", 2026-01-13
    // "Add the city written in textbox instead of hardcoding "Calgary", 2026-01-14
    public void AddbuttonClicked(View view) {
        String newCity = cityNameInput.getText().toString().trim();
        if (!newCity.isEmpty()) {
            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged();
            cityNameInput.setText("");
            cityList.smoothScrollToPosition(dataList.size() - 1);
        } else {
            cityNameInput.setError("Please enter a city name");
        }
    }

    // The template of the following function is from Gemini,
    // "make a function for the delete button", 2026-01-13
    // "it will delete a city after clicking the delete button, after clicking the city first", 2026-01-14
    public void DeletebuttonClicked(View view) {
        if (selectedPosition != -1 && !dataList.isEmpty()) {
            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();
            selectedPosition = -1;
        }
    }
}
