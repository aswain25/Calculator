package com.example.admin.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public enum OperatorType
    {
        Add,
        Subtract,
        Multiply,
        Divide
    }

    OperatorType currentOperator;
    double currentOperand;
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
    }

    public void numberClicked(View view)
    {
        Button asButton = (Button)view;
        if (display.getText().toString().equals("0"))
            display.setText(asButton.getText().toString());
        else
            display.setText(display.getText().toString() + asButton.getText().toString());
    }
    public void cClicked(View view)
    {
        display.setText("0");
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
        currentOperand = Double.parseDouble(display.getText().toString());
        display.setText("0");
    }
    public void equalsClicked(View view)
    {
        double operandTwo = Double.parseDouble(display.getText().toString());
        switch (currentOperator)
        {
            case Add:
                display.setText(String.valueOf(currentOperand + operandTwo));
                break;
            case Subtract:
                display.setText(String.valueOf(currentOperand - operandTwo));
                break;
            case Multiply:
                display.setText(String.valueOf(currentOperand * operandTwo));
                break;
            case Divide:
                display.setText(String.valueOf(currentOperand / operandTwo));
                break;
        }

    }
}
