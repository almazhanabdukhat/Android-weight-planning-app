package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    Button button_w_l, button_w_g, button_bmi;
    @Override
    protected void onCreate(Bundle savedInstanceStateBmi) {
        super.onCreate(savedInstanceStateBmi);
        setContentView(R.layout.activity_main);

        button_w_l = findViewById(R.id.w_l_time);
        button_w_g = findViewById(R.id.w_g_time);
        button_bmi=findViewById(R.id.btn_bmi);
       //Button button2 = findViewById(R.id.bmi);
       // Button button3 = findViewById(R.id.ideal_w);


        //first button - calculate weight loss
        button_w_l.setOnClickListener(
                new View.OnClickListener(){
        @Override
            public void onClick(View v) {
            Intent intent1=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent1);
        }
        });

        //second button- calculate weight gain
        button_w_g.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent2=new Intent(MainActivity.this,Main4Activity.class);
                        startActivity(intent2);
                    }
                });
        //third button - calculate bmi
        button_bmi.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intentBmi=new Intent(MainActivity.this,Main3Activity.class);
                        startActivity(intentBmi);
                    }
                });


    }

}
