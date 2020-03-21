package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView operation;

    private Double operand1;
    private String pendingOperation = "=";
    private static final String TAG = "MainActivity";
    private static final String RESULT = "Result";
    private static final String PENDING_OPS = "PendingOps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        operation = (TextView) findViewById(R.id.operation);

        Button[] btnArray = new Button[12];

        btnArray[0] = (Button) findViewById(R.id.btn0);
        btnArray[1] = (Button) findViewById(R.id.btn1);
        btnArray[2] = (Button) findViewById(R.id.btn2);
        btnArray[3] = (Button) findViewById(R.id.btn3);
        btnArray[4] = (Button) findViewById(R.id.btn4);
        btnArray[5] = (Button) findViewById(R.id.btn5);
        btnArray[6] = (Button) findViewById(R.id.btn6);
        btnArray[7] = (Button) findViewById(R.id.btn7);
        btnArray[8] = (Button) findViewById(R.id.btn8);
        btnArray[9] = (Button) findViewById(R.id.btn9);
        btnArray[10] = (Button) findViewById(R.id.dot);
        btnArray[11] = (Button) findViewById(R.id.negative);

        Button[] operations = new Button[5];

        operations[0] = (Button) findViewById(R.id.plus);
        operations[1] = (Button) findViewById(R.id.minus);
        operations[2] = (Button) findViewById(R.id.multiply);
        operations[3] = (Button) findViewById(R.id.divide);
        operations[4] = (Button) findViewById(R.id.equals);

        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {
                Button b = (Button) v;

                newNumber.append(b.getText().toString());
                if(b.getId() == R.id.negative)
                {
                    newNumber.setText("-");
                }

            }
        };

        for (Button b : btnArray) {
            b.setOnClickListener(listener);
        }

        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();

                try {
//                    if(value.length() > 0){
                    Double d = Double.valueOf(value);
                    performOperation(d, op);
//                    }

                } catch (NumberFormatException f) {
                    newNumber.setText("");
                }

                pendingOperation = op;
                operation.setText(op);
            }
        };

        for (Button b : operations) {
            b.setOnClickListener(operationListener);
        }

        Log.d(TAG, "onCreate: out");

    }

    private void performOperation(Double value, String operation) {

        if (operand1 == null) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;

                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;

                case "+":
                    operand1 += value;
                    break;

                case "-":
                    operand1 -= value;
            }

        }
        result.setText(operand1.toString());
        newNumber.setText("");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");
        super.onRestoreInstanceState(savedInstanceState);

        String str = savedInstanceState.getString(RESULT);

        if(str.length() > 0)
        {
            operand1 = Double.parseDouble(str);
        }

        pendingOperation = savedInstanceState.getString(PENDING_OPS);

        operation.setText(pendingOperation);
        Log.d(TAG, "onRestoreInstanceState: " + operand1);
        Log.d(TAG, "onRestoreInstanceState: out");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: in");
        outState.putString(RESULT,result.getText().toString());
        outState.putString(PENDING_OPS,pendingOperation);
//        Double value = null;
//        outState.putDouble("Doublevalue",value);
        Log.d(TAG, "onSaveInstanceState: " + result.getText().toString());
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: out");
    }


}
