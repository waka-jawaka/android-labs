package algie.lab1.calc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import algie.lab1.R;

public class CalculatorActivity extends AppCompatActivity {

    Calculus calculus = new Calculus();

    private ArrayList<Button> numberButtons = new ArrayList<>();
    private ArrayList<Button> calculusButtons = new ArrayList<>();
    private TextView outputPanel;
    private TextView clearButton;
    private Button dotButton;
    private Button equalButton;

    private void initializeCalcButtons() {
        Integer[] buttonIds = new Integer[] {
                R.id.button_slash, R.id.button_mul, R.id.button_minus, R.id.button_plus
        };

        for (int id: buttonIds) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    String input = outputPanel.getText().toString();

                    if (calculus.isInitialized()) {
                        String new_x = String.valueOf(calculus.calculate());
                        calculus.updateArguments(new_x);
                        outputPanel.setText(new_x + "" + button.getText());
                        calculus.updateOperation(button.getText().toString());
                        return;
                    }

                    if (input.isEmpty() && button.getText().equals("-")) {
                        outputPanel.setText("-");
                        calculus.updateArguments("-");
                        return;
                    }

                    if (input.matches(".*\\d$")) {
                        outputPanel.setText(input + "" + button.getText());
                        calculus.updateOperation(button.getText().toString());
                    }
                }
            });
            calculusButtons.add(button);
        }
    }

    private void initializeNumberButtons() {
        Integer[] buttonIds = new Integer[] {
            R.id.button_zero, R.id.button_one, R.id.button_two, R.id.button_three,
            R.id.button_four, R.id.button_five, R.id.button_six,
            R.id.button_seven, R.id.button_eight, R.id.button_nine
        };

        for (int id: buttonIds) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    calculus.updateArguments(button.getText().toString());
                    outputPanel.setText(outputPanel.getText() + "" + button.getText());
                }
            });
            numberButtons.add(button);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        outputPanel = (TextView) findViewById(R.id.output);

        initializeNumberButtons();
        initializeCalcButtons();

        clearButton = (TextView) findViewById(R.id.button_cancel);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputPanel.setText("");
                calculus.restart();
            }
        });

        dotButton = (Button) findViewById(R.id.button_dot);
        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = outputPanel.getText().toString();
                if (input.isEmpty() || !input.matches(".*\\d$")) {
                    return;
                }
                if (input.matches("\\d+\\.\\d+$") || input.matches("\\d+\\.?\\d+[\\+\\-\\*\\/]\\d+\\.\\d+$")) {
                    return;
                }
                outputPanel.setText(outputPanel.getText() + "" + dotButton.getText());
                calculus.updateArguments(dotButton.getText().toString());
            }
        });

        equalButton = (Button) findViewById(R.id.button_equal);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = String.valueOf(calculus.calculate());
                outputPanel.setText(result);

                calculus.updateArguments(result);
            }
        });
    }
}
