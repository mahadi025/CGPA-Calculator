package com.example.cgpacalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class GradeSetupActivity extends AppCompatActivity {

    private LinearLayout editTextContainer;
    private int editTextCount = 0;
    private Button saveButton, addGradeButton, viewAllButton;
    private ListView listView;
    private ArrayAdapter gradeArrayAdapter;
    private DataBase db;
    private Grade grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_setup);

        editTextContainer = findViewById(R.id.editTextContainer);
        addGradeButton = findViewById(R.id.addGradeBtn);
        viewAllButton=findViewById(R.id.viewAllBtn);
        saveButton = findViewById(R.id.saveBtn);
        listView = findViewById(R.id.gradeList);

        db=new DataBase(GradeSetupActivity.this);
        showGradeListView();

        addGradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGradeEditTexts();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessGradeEditTexts();
            }
        });

        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase db=new DataBase(GradeSetupActivity.this);
                gradeArrayAdapter=new ArrayAdapter<Grade>(GradeSetupActivity.this, android.R.layout.simple_list_item_1, db.getALL());
                listView.setAdapter(gradeArrayAdapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Grade clickedGrade= (Grade) adapterView.getItemAtPosition(position);
                boolean check= db.deleteOne(clickedGrade);
                showGradeListView();
                Toast.makeText(GradeSetupActivity.this, "Grade deleted from the list "+check, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showGradeListView() {
        gradeArrayAdapter=new ArrayAdapter<Grade>(GradeSetupActivity.this, android.R.layout.simple_list_item_1, db.getALL());
        listView.setAdapter(gradeArrayAdapter);
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

        EditText gradeNameEditText = new EditText(GradeSetupActivity.this);
        gradeNameEditText.setId(editTextCount * 10 + 1);
        gradeNameEditText.setHint("Grade Name");
        gradeNameEditText.setLayoutParams(layoutParams);
        horizontalLayout.addView(gradeNameEditText);

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

            String gradeName = gradeNameEditText.getText().toString();
            double gradePoints = Double.parseDouble(gradePointsEditText.getText().toString());
            try{
                grade=new Grade(gradeName, gradePoints);
                Toast.makeText(this, "Successfully inserted the grade", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                grade=new Grade("-1", -1);
                Toast.makeText(this, "Unable to save the grade", Toast.LENGTH_LONG).show();
            }
            DataBase dataBase =new DataBase(GradeSetupActivity.this);
            boolean b = dataBase.addOne(grade);
            showGradeListView();
        }
    }
}
