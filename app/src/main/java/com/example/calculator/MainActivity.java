package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    //Creating variables for views
    TextInputEditText etHomePrice, etDownPayment, etPercentage, etLength, etInterest;
    AppCompatButton btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Changing the color of the top status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));
        //Initializing the view variables
        etHomePrice = findViewById(R.id.etHomePrice);
        etDownPayment = findViewById(R.id.etDownPayment);
        etPercentage = findViewById(R.id.etPercentage);
        etLength = findViewById(R.id.etLength);
        etInterest = findViewById(R.id.etInterest);
        btnCalculate = findViewById(R.id.btnCalculate);

        //When user input any digit into home price field
        //Here adding addTextChangedListener to the input field so that percentage should be updated
        etHomePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Calling the method for Updating the percentage
                updatePercentage();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //When user input any digit into down payment field
        //Here adding addTextChangedListener to the input field so that percentage should be updated
        etDownPayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePercentage();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //Clicking on Calculate button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checking all the fields should not be empty
                if (etHomePrice.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter home price", Toast.LENGTH_SHORT).show();
                } else if (etDownPayment.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter down payment", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(etDownPayment.getText().toString()) > Double.parseDouble(etHomePrice.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Down payment should be within home price", Toast.LENGTH_SHORT).show();
                } else if (etLength.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter years", Toast.LENGTH_SHORT).show();
                } else if (etInterest.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter interest rate", Toast.LENGTH_SHORT).show();
                } else {
                    //If all the input fields are not empty then calling the method for calculation
                    calculate();
                }
            }
        });

    }

    private void calculate() {
        //Getting all input texts and converting them into double and storing them into double variables
        double homePrice = Double.parseDouble(etHomePrice.getText().toString());
        double downPayment = Double.parseDouble(etDownPayment.getText().toString());
        double loanLength = Double.parseDouble(etLength.getText().toString());
        double interestRate = Double.parseDouble(etInterest.getText().toString());

        //Calculating the remaining amount
        double principal = homePrice - downPayment;
        //Calculating the percentage value
        double monthlyInterestRate = (interestRate / 100) / 12;
        //Calculating the number of months
        double numberOfPayments = loanLength * 12;
        //Calculating the monthly payment using the formula for loan
        double numerator = principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments);
        double denominator = Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1;
        double monthlyPayment = numerator / denominator;
        @SuppressLint("DefaultLocale")
        //Format the monthly payment to display only two decimal places
        String formattedMonthlyPayment = String.format("%.2f", monthlyPayment);
        //Move to the result screen with the formatted monthly payment
        moveToResultScreen(formattedMonthlyPayment);
    }

    @SuppressLint("DefaultLocale")
    private void updatePercentage() {
        try {
            // Get the home price and down payment values from the input fields
            double homePrice = Double.parseDouble(etHomePrice.getText().toString());
            double downPayment = Double.parseDouble(etDownPayment.getText().toString());
            // Check if the input values are valid
            if (homePrice > 0 && downPayment >= 0 && downPayment <= homePrice) {
                // Calculate the percentage and set it in the percentage field
                double percentage = (downPayment / homePrice) * 100;
                //Format the percentage and displaying on edittext
                etPercentage.setText(String.format("%.2f", percentage));
            }
        } catch (NumberFormatException e) {

        }
    }

    @SuppressLint("SetTextI18n")
    public void moveToResultScreen(String amount) {
        // Create an intent to navigate to the ResultActivity
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        // Pass the calculated result amount as an extra to the intent
        intent.putExtra("result", amount);
        // Start the ResultActivity
        startActivity(intent);

    }

}