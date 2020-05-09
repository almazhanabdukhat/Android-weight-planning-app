package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Toast;

public class BmiActivity extends AppCompatActivity {

    //Create spinners for gender and activity
    Spinner spinner_gen, spinner_system, spinnerKgLb, spinnerCmFt, spinnerIn;
    String[] genders = {"Female", "Male"};
    String[] systems = {"Metric", "Imperial"};
    String[] weight_metrics = {"kg","lb"};
    String[] cmFt = {"cm", "ft"};
    String[] inches = {"-","in"};


    //method to check the input
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState3) {
        super.onCreate(savedInstanceState3);
        setContentView(R.layout.bmi);

        //Set spinners
        spinner_gen = (Spinner) findViewById(R.id.spinner_genB);
        //spinner_system = (Spinner) findViewById(R.id.spinnerB);
        spinnerKgLb=(Spinner) findViewById(R.id.spinnerKgLb);
        spinnerCmFt=(Spinner) findViewById(R.id.spinnerCmFt);
        spinnerIn = (Spinner) findViewById(R.id.spinnerInches);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders);
        ArrayAdapter<String> arrayAdapterSyst = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, systems);
        ArrayAdapter<String> arrayAdapterKgLb = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weight_metrics);
        ArrayAdapter<String> arrayAdapterCmFt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cmFt);
        ArrayAdapter<String> arrayAdapterInches = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, inches);

        spinner_gen.setAdapter(arrayAdapter);
        spinner_system.setAdapter(arrayAdapterSyst);
        spinnerKgLb.setAdapter(arrayAdapterKgLb);
        spinnerCmFt.setAdapter(arrayAdapterCmFt);
        spinnerIn.setAdapter(arrayAdapterInches);


        //Get values from the user input
        final EditText weight = findViewById(R.id.weight);
        final EditText ageY = findViewById(R.id.ageY);
        final EditText ageM = findViewById(R.id.ageM);
        final EditText heightCmFt=findViewById(R.id.heightCm);
        final EditText heightIn = findViewById(R.id.heightIn);

        Button button = findViewById(R.id.btn_calcBmi);
        Button backButton = findViewById(R.id.btn_menu);
        final TextView outMeta = findViewById(R.id.txt_solution1B);
        final TextView outTotal = findViewById(R.id.txt_solution2B);
        final TextView outTime = findViewById(R.id.txt_solution3B);


        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v3) {

                //convert user input to strings, declare variables
                String gender = spinner_gen.getSelectedItem().toString();
                String system = spinner_system.getSelectedItem().toString();
                String kgOrLb = spinnerKgLb.getSelectedItem().toString();
                String cmOrFt = spinnerCmFt.getSelectedItem().toString();
                String inches= spinnerIn.getSelectedItem().toString();
                String weightS = weight.getText().toString();
                String heightSCmFt = heightCmFt.getText().toString();
                String heightSIn = heightIn.getText().toString();
                String ageYear=ageY.getText().toString();
                String ageMonth=ageM.getText().toString();


                double bmi;
                int ageY, ageM;
                double weightFin, weight, height,heightCm, heightIn, heightFin;


                //Check if inputs are correct
                boolean exception = false;

                if (isNumeric(weightS)){ weight = Integer.valueOf(weightS); }
                else { weight=-1; outMeta.setText("Please enter weight in kg (i.e. 45)"); exception = true; }

                if (isNumeric(heightSCmFt)){ heightCm = Integer.valueOf(heightSCmFt); }
                else { heightCm=-1; outMeta.setText("Please enter height in cm (i.e. 165)"); exception = true; }

                if (isNumeric(heightSIn)){ heightIn = Integer.valueOf(heightSIn); }
                else { heightIn=-1; outMeta.setText("Please enter height in cm (i.e. 165)"); exception = true; }

                if (isNumeric(ageYear)){ ageY = Integer.valueOf(ageYear); }
                else { ageY=-1; outMeta.setText("Please years (i.e. 0, 7)"); exception = true; }

                if (isNumeric(ageMonth)){ ageM = Integer.valueOf(ageMonth); }
                else { ageM=-1; outMeta.setText("Please enter months - choose 0 if don't know"); exception = true; }




                int age=0; int percentile=0; height=0;
                //If inputs are correct
                if (!exception) {

                    bmi = -1;
                    //convert age to months
                    age = ageM + ageY * 12;
                    //convert to metric system
                    if (kgOrLb == "lb") {
                        weightFin = weight / 2.2046;
                    }
                    if (cmOrFt == "ft") {
                        height = heightCm * 30.48;
                    }
                    if (inches == "in") {
                        heightFin = (height + heightIn) / 100;
                    }
                    if (age < 24) {
                        outMeta.setText("BMI can't be calculated");
                    } else {
                        bmi = weight / (height * height);
                    }




                    //Output results
                    if (age > 252 && age < 239) {
                        if (bmi < 15) {
                            outTotal.setText("Very Severely Underweight");
                        } else if (bmi >= 15 && bmi < 16) {
                            outTotal.setText("Severely Underweight");
                        } else if (bmi >= 16 && bmi < 18.5) {
                            outTotal.setText("Underweight");
                        } else if (bmi >= 18.5 && bmi < 25) {
                            outTotal.setText("Normal (healthy weight)");
                        } else if (bmi >= 25 && bmi < 30) {
                            outTotal.setText("Overweight");
                        } else if (bmi >= 30 && bmi < 35) {
                            outTotal.setText("Moderately Obese");
                        } else if (bmi >= 35 && bmi < 40) {
                            outTotal.setText("Severely Obese");
                        } else if (bmi >= 40) {
                            outTotal.setText("Very Severely Underweight");
                        }
                    }

                }



            }



        });

        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentBmi = new Intent(BmiActivity.this, MainActivity.class);
                        startActivity(intentBmi);
                    }
                });
    }

}


