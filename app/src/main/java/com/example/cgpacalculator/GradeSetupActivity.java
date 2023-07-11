package com.example.cgpacalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GradeSetupActivity extends AppCompatActivity {

    private LinearLayout editTextContainer;
    private int editTextCount = 0;

//    private Map<String, Double> gradeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_setup);

        editTextContainer = findViewById(R.id.editTextContainer);
        Button addGradeButton = findViewById(R.id.addGradeButton);
        addGradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGradeEditTexts();
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessGradeEditTexts();
            }
        });
    }

    private void addGradeEditTexts() {
        editTextCount++;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 16, 0, 0);

        LinearLayout horizontalLayout = new LinearLayout(GradeSetupActivity.this);
        horizontalLayout.setLayoutParams(layoutParams);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create Grade Name EditText
        EditText gradeNameEditText = new EditText(GradeSetupActivity.this);
        gradeNameEditText.setId(editTextCount * 10 + 1);
        gradeNameEditText.setHint("Grade Name");
        gradeNameEditText.setLayoutParams(layoutParams);
        horizontalLayout.addView(gradeNameEditText);

        // Create Grade Points EditText
        EditText gradePointsEditText = new EditText(GradeSetupActivity.this);
        gradePointsEditText.setId(editTextCount * 10 + 2);
        gradePointsEditText.setHint("Grade Points");
        gradePointsEditText.setLayoutParams(layoutParams);
        horizontalLayout.addView(gradePointsEditText);

        editTextContainer.addView(horizontalLayout);
    }

    private void accessGradeEditTexts() {

        for (int i = 1; i <= editTextCount; i++) {
            int gradeNameEditTextId = i * 10 + 1;
            int gradePointsEditTextId = i * 10 + 2;

            EditText gradeNameEditText = findViewById(gradeNameEditTextId);
            EditText gradePointsEditText = findViewById(gradePointsEditTextId);

            String gradeName = gradeNameEditText.getText().toString().trim();
            double gradePoints = Double.parseDouble(gradePointsEditText.getText().toString().trim());

//            gradeMap.put(gradeName, gradePoints);
        }

    }


}
