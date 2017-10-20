package algie.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import algie.lab1.calc.CalculatorActivity;
import algie.lab1.color.ColorDisplayActivity;
import algie.lab1.notes.NotesActivity;

public class MainActivity extends AppCompatActivity {

    private Button firstTaskButton;
    private Button secondTaskButton;
    private Button thirdTaskButton;

    private class OnButtonClickListener implements View.OnClickListener {

        private Class activity;

        OnButtonClickListener(Class activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this.getApplicationContext(), activity);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstTaskButton = (Button) findViewById(R.id.first_activity_button);
        secondTaskButton = (Button) findViewById(R.id.second_activity_button);
        thirdTaskButton = (Button) findViewById(R.id.third_activity_button);

        firstTaskButton.setOnClickListener(new OnButtonClickListener(ColorDisplayActivity.class));
        secondTaskButton.setOnClickListener(new OnButtonClickListener(CalculatorActivity.class));
        thirdTaskButton.setOnClickListener(new OnButtonClickListener(NotesActivity.class));
    }
}
