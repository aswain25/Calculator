package com.example.admin.calculator;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public enum OperatorType
    {
        Add,
        Subtract,
        Multiply,
        Divide
    }

    OperatorType currentOperator;
    boolean displayingCurrentOperand = false;
    double currentOperand = Double.NaN;
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(getApplicationContext(), ScientificActivity.class);
            startActivity(intent);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            return;
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
        if (display.getText().toString().equals("0"))
            display.setText(asButton.getText().toString());
        else
            display.setText(display.getText().toString() + asButton.getText().toString());
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
            default:
                return 0;
        }
    }
}
