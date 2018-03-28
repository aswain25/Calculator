package com.example.admin.calculator;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScientificActivity extends AppCompatActivity {

    public enum OperatorType
    {
        Add,
        Subtract,
        Multiply,
        Divide,
        Exponent,
        Modulus,
    }

    OperatorType currentOperator;
    boolean displayingCurrentOperand = false;
    double currentOperand = Double.NaN;
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        display = findViewById(R.id.display);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void numberClicked(View view)
    {
        if (displayingCurrentOperand)
        {
            display.setText("");
            displayingCurrentOperand = false;
        }
        Button asButton = (Button)view;
        String bText = asButton.getText().toString().equals("e") ? "2.17" : (asButton.getText().toString().equals("π") ? "3.1415" : asButton.getText().toString());
        if (display.getText().toString().equals("0"))
            display.setText(bText);
        else
            display.setText(display.getText().toString() + bText);
    }
    public void cClicked(View view)
    {
        display.setText("0");
        displayingCurrentOperand = false;
        currentOperand = Double.NaN;
    }
    public void bsClicked(View view)
    {
        String dispayText = display.getText().toString();
        if (dispayText.length() > 1)
            display.setText(dispayText.subSequence(0, dispayText.length() - 1));
        else
            display.setText("0");
    }
    public void negateClicked(View view)
    {
        if (display.getText().toString().charAt(0) != '-')
            display.setText("-" + display.getText().toString());
        else
            display.setText(display.getText().toString().substring(1, display.getText().toString().length()));
    }
    public void operatorClicked(View view)
    {
        Button asButton = (Button)view;
        String buttonText = asButton.getText().toString();

        switch (buttonText)
        {
            case "+":
                currentOperator = OperatorType.Add;
                break;
            case "-":
                currentOperator = OperatorType.Subtract;
                break;
            case "✕":
                currentOperator = OperatorType.Multiply;
                break;
            case "÷":
                currentOperator = OperatorType.Divide;
                break;
            case "x^y":
                currentOperator = OperatorType.Exponent;
                break;
            case "mod":
                currentOperator = OperatorType.Modulus;
                break;
        }
        if (!displayingCurrentOperand)
        {
            if (Double.isNaN(currentOperand))
                currentOperand = Double.parseDouble(display.getText().toString());
            else
                currentOperand = operate();
            displayingCurrentOperand = true;
            display.setText(Double.toString(currentOperand));
        }
    }
    public void unaryOperatorClicked(View view)
    {
        String operator = ((Button)view).getText().toString();
        double currentValue = Double.parseDouble(display.getText().toString());
        if (!display.getText().toString().equals(""))
        {
            switch (operator)
            {
                case "sin":
                    display.setText(String.valueOf(Math.sin(currentValue)));
                    break;
                case "cos":
                    display.setText(String.valueOf(Math.cos(currentValue)));
                    break;
                case "tan":
                    display.setText(String.valueOf(Math.tan(currentValue)));
                    break;
                case "x^2":
                    display.setText(String.valueOf(currentValue *currentValue ));
                    break;
                case "√":
                    display.setText(String.valueOf(Math.sqrt(currentValue)));
                    break;
                case "10^x":
                    display.setText(String.valueOf(Math.pow(10, currentValue) ));
                    break;
                case "log":
                    display.setText(String.valueOf(Math.log(currentValue) ));
                    break;
                case "e^x":
                    display.setText(String.valueOf(Math.pow(Math.E, currentValue) ));
                    break;
                case "n!":
                    display.setText(String.valueOf(frac(currentValue)));
                    break;

            }
            //currentOperand = Double.NaN;
        }
    }
    private static double frac(double n)
    {
        for (int i = (int)Math.floor(n) - 1; i > 0; i--)
            n *= i;
        return n;
    }
    public void equalsClicked(View view)
    {
        if (!displayingCurrentOperand && !Double.isNaN(currentOperand))
        {
            display.setText(String.valueOf(operate()));
            currentOperand = Double.NaN;
        }
    }
    public double operate()
    {
        double operandTwo = Double.parseDouble(display.getText().toString());
        switch (currentOperator)
        {
            case Add:
                return currentOperand + operandTwo;
            case Subtract:
                return currentOperand - operandTwo;
            case Multiply:
                return currentOperand * operandTwo;
            case Divide:
                return currentOperand / operandTwo;
            case Exponent:
                return Math.pow(currentOperand, operandTwo);
            case Modulus:
                return currentOperand % operandTwo;
            default:
                return 0;
        }
    }
}
