 package com.example.cgpacalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

 public class MainActivity extends AppCompatActivity {


     private LinearLayout editTextContainer;
     private Button calculateCgpa;
     private Button addCourseButton;

     private Button setupButton;
     private int editTextCount = 0;
     private DataBase db;

     private static final int GRADE_SETUP_REQUEST_CODE = 1;

     Map<String, Double> gradeMap = new HashMap<>();
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         editTextContainer = findViewById(R.id.editTextContainer);
         addCourseButton = findViewById(R.id.addCourseButton);
         calculateCgpa=findViewById(R.id.calculateButton);
         setupButton = findViewById(R.id.setupButton);
        db=new DataBase(MainActivity.this);

         setupButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, GradeSetupActivity.class);
                 startActivityForResult(intent, GRADE_SETUP_REQUEST_CODE);
             }
         });

         calculateCgpa.setBackgroundColor(Color.GREEN);

         addCourseButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addCourseEditTexts();
             }
         });
         calculateCgpa.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 calculateCGPA();
             }
         });
     }
     private void addCourseEditTexts() {
         editTextCount++;

         LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                 LinearLayout.LayoutParams.WRAP_CONTENT,
                 LinearLayout.LayoutParams.WRAP_CONTENT);
         layoutParams.setMargins(0, 16, 0, 0);

         LinearLayout horizontalLayout = new LinearLayout(MainActivity.this);
         horizontalLayout.setLayoutParams(layoutParams);
         horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

         // Create Course Id EditText
         EditText courseIdEditText = new EditText(MainActivity.this);
         courseIdEditText.setId(editTextCount * 10 + 1);
         courseIdEditText.setHint("Course Id");
         courseIdEditText.setLayoutParams(layoutParams);
         horizontalLayout.addView(courseIdEditText);

         // Create Grade EditText
         EditText gradeEditText = new EditText(MainActivity.this);
         gradeEditText.setId(editTextCount * 10 + 2);
         gradeEditText.setHint("Grade");
         gradeEditText.setLayoutParams(layoutParams);
         horizontalLayout.addView(gradeEditText);

         // Create Credits EditText
         EditText creditsEditText = new EditText(MainActivity.this);
         creditsEditText.setId(editTextCount * 10 + 3);
         creditsEditText.setHint("Credits");
         creditsEditText.setLayoutParams(layoutParams);
         horizontalLayout.addView(creditsEditText);

         Button deleteButton = new Button(MainActivity.this);
         deleteButton.setId(editTextCount * 10 + 4);
         deleteButton.setText("Delete");
         deleteButton.setLayoutParams(layoutParams);
         deleteButton.setBackgroundColor(Color.RED);
         deleteButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 editTextContainer.removeView(horizontalLayout);
             }
         });
         horizontalLayout.addView(deleteButton);

         editTextContainer.addView(horizontalLayout);
     }

     private void calculateCGPA() {
         double totalGrade = 0;
         double totalCredit = 0;

         for (int i = 1; i <= editTextCount; i++) {
             int courseIdEditTextId = i * 10 + 1;
             int gradeEditTextId = i * 10 + 2;
             int creditsEditTextId = i * 10 + 3;

             EditText gradeEditText = findViewById(gradeEditTextId);
             EditText creditsEditText = findViewById(creditsEditTextId);

             String gradeName = gradeEditText.getText().toString().toUpperCase();
             double credits = Double.parseDouble(creditsEditText.getText().toString());
             double gradePoint=db.getGradePoint(gradeName);
             totalGrade += gradePoint * credits;
             totalCredit += credits;
         }

         double cgpa = totalGrade / totalCredit;
         Toast.makeText(MainActivity.this, "Your CGPA is: " + cgpa, Toast.LENGTH_SHORT).show();
     }


 }
