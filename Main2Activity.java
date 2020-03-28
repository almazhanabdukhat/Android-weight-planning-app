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

public class Main2Activity extends AppCompatActivity {

    //Create spinners for gender and activity
    Spinner spinner_gen, spinner_activity,spinner_time;
    //String[] genders = {"Select gender", "Female", "Male"};
    String[] genders = {"Female", "Male"};
    String[] activity_levels = {"No exercise/Desk job", "Light: 1-2 times/week", "Moderate: 3-4 times/week", "Hard exercise: 5-7 times/week","Physical job"};
    String[] time = {"Day(s)","Month(s)"};


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
        setContentView(R.layout.activity_main2);

        //Set spinners
        spinner_gen = (Spinner) findViewById(R.id.spinner);
        spinner_activity = (Spinner) findViewById(R.id.spinner_activity);
        spinner_time=(Spinner) findViewById(R.id.spinner_time);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders);
        ArrayAdapter<String> arrayAdapterAct = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activity_levels);
        ArrayAdapter<String> arrayAdapterTime = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, time);
        spinner_gen.setAdapter(arrayAdapter);
        //spinner_gen.setVisibility(View.GONE);
        spinner_activity.setAdapter(arrayAdapterAct);
        spinner_time.setAdapter(arrayAdapterTime);

        //Get values from the user input
        final EditText weight = findViewById(R.id.et_first_number);
        final EditText height = findViewById(R.id.et_second_number);
        final EditText age = findViewById(R.id.age);
        final EditText number_time = findViewById(R.id.number_time);
        final EditText goal_weight = findViewById(R.id.goal_weight);

        Button button = findViewById(R.id.btn_add);
        Button backButton = findViewById(R.id.btn_menu);
        final TextView outMeta = findViewById(R.id.txt_solution);
        final TextView outTotal = findViewById(R.id.txt_solution2);
        final TextView outTime = findViewById(R.id.txt_solution3);


        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                //convert user input to strings, declare variables
                String gender = spinner_gen.getSelectedItem().toString();
                String activity = spinner_activity.getSelectedItem().toString();
                String time_type = spinner_time.getSelectedItem().toString();
                String weightS = weight.getText().toString();
                String heightS = height.getText().toString();
                String ageS = age.getText().toString();
                String number_timeS = number_time.getText().toString();
                String goal_weightS = goal_weight.getText().toString();
                double rest_met, total_exp, cal_lose_day, cal_eat_day, cal_burn_rate;
                int weight, age, height, number_time, goal_weight,daysToLose, kg_to_lose,cal_to_lose;


                //Check if inputs are correct
                boolean exception = false;

                if (isNumeric(weightS)){ weight = Integer.valueOf(weightS); }
                else { weight=-1; outMeta.setText("Please enter weight in kg (i.e. 45)"); exception = true; }

                if (isNumeric(heightS)){ height = Integer.valueOf(heightS); }
                else { height=-1; outMeta.setText("Please enter height in cm (i.e. 165)"); exception = true; }

                if (isNumeric(ageS)){ age = Integer.valueOf(ageS); }
                else { age=-1; outMeta.setText("Please enter age (i.e. 23)"); exception = true; }

                if (isNumeric(number_timeS)){ number_time = Integer.valueOf(number_timeS); }
                else { number_time=-1;outMeta.setText("Please enter time to lose weight in days or months"); exception = true;}

                if (isNumeric(goal_weightS)){ goal_weight = Integer.valueOf(goal_weightS); }
                else { goal_weight=-1; outMeta.setText("Please enter goal weight in kg (i.e. 40)"); exception = true;}



                //If inputs are correct
                if (!exception) {

                    total_exp = daysToLose = -1;

                    //Calculate resting metabolism based on gender, weight, height based on Harris-Benedict equation
                    if (gender == "Female")
                        rest_met = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
                    else
                        rest_met = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);

                    //Calculate total calorie expenditure based on activity level
                    if (activity == "No exercise/Desk job") {
                        total_exp = rest_met * 0.21 + rest_met;
                        outTime.setText("To maintain your current weight you need to eat " + String.valueOf(Math.round(total_exp)) + " calories");
                    } else if (activity == "Light: 1-2 times/week") {
                        total_exp = rest_met * 0.388 + rest_met;
                        outTime.setText("To maintain your current weight you need to eat " + String.valueOf(Math.round(total_exp)) + " calories");
                    } else if (activity == "Moderate: 3-4 times/week") {
                        total_exp = rest_met * 0.574 + rest_met;
                        outTime.setText("To maintain your current weight you need to eat " + String.valueOf(Math.round(total_exp)) + " calories");
                    } else {
                        total_exp = rest_met * 0.743 + rest_met;
                        outTime.setText("To maintain your current weight you need to eat " + String.valueOf(Math.round(total_exp)) + " calories");
                    }

                    //Calculate time for weight loss
                    kg_to_lose = weight - goal_weight;
                    cal_to_lose = kg_to_lose * 7700;
                    daysToLose=(time_type =="Month(s)")? number_time*30 : number_time;
                    cal_lose_day = cal_to_lose / daysToLose;
                    cal_to_lose = kg_to_lose * 7700;
                    cal_eat_day = total_exp - cal_lose_day;
                    cal_burn_rate = total_exp - cal_eat_day;


                    //Output results
                    if (cal_eat_day>-500) {
                        if (time_type == "Month(s)") {
                            outMeta.setText("To lose " + String.valueOf(kg_to_lose) + " kg in " + String.valueOf(number_time) + " month(s), you need to eat " + String.valueOf(Math.round(cal_eat_day)) + " calories per day");
                        } else {
                            outMeta.setText("To lose " + String.valueOf(kg_to_lose) + " kg in " + String.valueOf(number_time) + " day(s), you need to eat " + String.valueOf(Math.round(cal_eat_day)) + " calories per day");
                        } outTotal.setText("or need to increase exercise to up calorie burn rate by " + String.valueOf(Math.round(cal_burn_rate)) + " calories");
                    } else {
                            outMeta.setText("It is unsafe for you to lose " + String.valueOf(kg_to_lose)+ " kg at this pace, please try again - select");
                            outTotal.setText("a higher activity level / higher goal weight / longer time to lose weight"); }


                }




            }
        });

        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }

}


