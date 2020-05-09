package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.KeyEvent;

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

public class Main3Activity extends AppCompatActivity {

    //Create spinners for gender and activity
    Spinner spinner_gen, spinnerWeightBmi,spinnerHeight, spinnerInches, spinnerSystem, spinnerAsianOr;

    String[] genders = {"Female", "Male"};
    String[] weight_metrics = {"kg","lb"};
    String[] cmFt = {"cm", "ft"};
    String[] inches = {"-","in"};
    //String [] systems={"Metric", "US Standard"};
    String [] asianOr = {"Yes", "No"};



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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //Set spinners
        spinner_gen = (Spinner) findViewById(R.id.spinner);
        spinnerWeightBmi=(Spinner)findViewById(R.id.spinnerWeight);
        spinnerHeight=(Spinner)findViewById(R.id.spinnerCm);
        spinnerInches=(Spinner)findViewById(R.id.spinnerInch);
       // spinnerSystem=(Spinner)findViewById(R.id.spinnerBmiSys);
        spinnerAsianOr = (Spinner) findViewById(R.id.spinnerAsian);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders);
        ArrayAdapter<String> arrayAdapterWeight = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weight_metrics);
        ArrayAdapter<String> arrayAdapterHeight = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cmFt);
        ArrayAdapter<String> arrayAdapterInches = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, inches);
        //ArrayAdapter<String> arrayAdapterSyst = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, systems);
        ArrayAdapter<String> arrayAdapterAsian = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, asianOr);


        spinner_gen.setAdapter(arrayAdapter);
      //  spinnerSystem.setAdapter(arrayAdapterSyst);
        spinnerWeightBmi.setAdapter(arrayAdapterWeight);
        spinnerHeight.setAdapter(arrayAdapterHeight);
        spinnerInches.setAdapter(arrayAdapterInches);
        spinnerAsianOr.setAdapter(arrayAdapterAsian);


        //Get values from the user input


        //New BMI app:
        final EditText ageY = findViewById(R.id.ageY);
        final EditText ageM = findViewById(R.id.ageM);
        final EditText weightBmi = findViewById(R.id.weightB);
        final EditText heightCmBmi = findViewById(R.id.heightCm);
        final EditText heightInches = findViewById(R.id.heightIn);

        final TextView genderTextView = findViewById(R.id.genderText);
        final TextView weightTextView = findViewById(R.id.weightText);
        final TextView heightTextView = findViewById(R.id.heightText);
        final TextView outMeta = findViewById(R.id.txt_solution);
        final TextView outTotal = findViewById(R.id.txt_solution2);
        final TextView outTime = findViewById(R.id.txt_solution3);



        Button button = findViewById(R.id.btn_add);
        Button backButton = findViewById(R.id.btn_menu);



        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                //convert user input to strings, declare variables


                //Retrieve values selected or typed by user

                String gender = spinner_gen.getSelectedItem().toString();
                String kgOrLb = spinnerWeightBmi.getSelectedItem().toString();
                String ftOrCm = spinnerHeight.getSelectedItem().toString();
                String inchesOrNo = spinnerInches.getSelectedItem().toString();
                String asianOrNot = spinnerAsianOr.getSelectedItem().toString();
                String ageYS=ageY.getText().toString();
                String ageMS=ageM.getText().toString();
                String weightBmiS = weightBmi.getText().toString();
                String heightCmS = heightCmBmi.getText().toString();
                String heightInS= heightInches.getText().toString();



                //Bmi values
                double bmi, weightBmi,heightCm,heightIn,heightFin, weightFin;
                int ageY, ageM, age;
                heightFin=heightCm=heightIn=-1;



                //Check if inputs are correct
                boolean exception = false;

                if (isNumeric(ageYS)){ ageY = Integer.valueOf(ageYS); }
                else { ageY=-1; outMeta.setText("Please enter integer value for age"); exception = true; }

                if (isNumeric(ageMS)){ ageM = Integer.valueOf(ageMS); }
                else { ageM=-1; outMeta.setText("Please enter integer value for age"); exception = true; }

                if (isNumeric(weightBmiS)){ weightBmi = Double.valueOf(weightBmiS); }
                else { weightBmi=-1; outMeta.setText("Please enter numeric value for weight"); exception = true; }

                if (ftOrCm=="cm"){
                    if (isNumeric(heightCmS)){
                        heightCm = Double.valueOf(heightCmS); heightIn=0; spinnerInches.setVisibility(View.GONE); heightInches.setVisibility(View.GONE);

                    } else { heightCm=-1; outMeta.setText("Please enter numeric value for height"); exception = true; }

                }

                if (ftOrCm=="ft"){
                    spinnerInches.setVisibility(View.VISIBLE); heightInches.setVisibility(View.VISIBLE);
                    if (isNumeric(heightCmS)){
                        heightCm = 30.48* Double.valueOf(heightCmS);
                    } else { heightCm=-1; outMeta.setText("Please enter numeric values"); exception = true; }

                    if (inchesOrNo=="-") {
                        heightIn=0;
                    }
                    if (inchesOrNo=="in"){
                        if (isNumeric(heightInS)){
                            heightIn=2.54*Double.valueOf(heightInS);
                        } else { exception=true; heightIn=0;
                            outMeta.setText("Please enter numeric values");
                        }
                    }
                }



                //If inputs are correct
                if (!exception) {


                    //convert age to months
                    age = ageM + ageY * 12;

                    //calculate height and express in meters
                    heightFin = (heightCm + heightIn)/100;

                    //calculate BMI
                    bmi = weightBmi / (heightFin*heightFin);
                    outMeta.setText("Your bmi is " + String.valueOf(bmi));

                    //Output results

                        if (bmi < 15) {
                            outTotal.setText("Very Severely Underweight");
                        } if (bmi >= 15 && bmi < 16) {
                            outTotal.setText("Severely Underweight"); }
                        //Asian BMI
                        if (asianOrNot == "Yes") {
                                if (bmi >= 16 && bmi < 17.5) {
                                    outTotal.setText("Underweight");
                                } else if (bmi >= 17.5 && bmi < 23) {
                                    outTotal.setText("Normal (healthy weight)");
                                } else if (bmi >= 23 && bmi < 28) {
                                    outTotal.setText("Overweight");
                                } else if (bmi >= 28) {
                                    outTotal.setText("Obese");
                                }
                        } else {
                                if (bmi >= 16 && bmi < 18.5) {
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
                                    outTotal.setText("Very Severely Obese");
                                }
                            }
                }


            }



        });

        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });



    }


}


