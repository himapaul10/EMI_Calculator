package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    //Creating variables for views
    ImageView ivBack;
    TextView tvPrice;
    //Creating variable for getting the result of previous screen
    String result;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Changing the color of the top status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));
        //Initializing the view variables
        ivBack = findViewById(R.id.ivBack);
        tvPrice = findViewById(R.id.tvPrice);

        if (getIntent().getExtras() != null){
            // Retrieve the result amount from the intent's extras
            result = getIntent().getStringExtra("result");
            // Display the result amount in the TextView with a "$" prefix
            tvPrice.setText("$"+result);
        }

        // Set up a click listener for the back button
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}