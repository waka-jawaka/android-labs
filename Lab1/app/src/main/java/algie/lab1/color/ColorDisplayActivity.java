package algie.lab1.color;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import algie.lab1.R;

public class ColorDisplayActivity extends AppCompatActivity {

    private SeekBar rSeek;
    private SeekBar gSeek;
    private SeekBar bSeek;
    private TextView colorDisplayPalette;

    private void setNewColor() {
        int r = rSeek.getProgress();
        int g = gSeek.getProgress();
        int b = bSeek.getProgress();
        colorDisplayPalette.setBackgroundColor(Color.rgb(r, g, b));
    }

    private void initializeSeekBar(SeekBar seekBar) {
        seekBar.setMax(0);
        seekBar.setMax(255);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setNewColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_display);

        colorDisplayPalette = (TextView) findViewById(R.id.colorDisplayPalette);
        rSeek = (SeekBar) findViewById(R.id.rSeek);
        gSeek = (SeekBar) findViewById(R.id.gSeek);
        bSeek = (SeekBar) findViewById(R.id.bSeek);

        initializeSeekBar(rSeek);
        initializeSeekBar(gSeek);
        initializeSeekBar(bSeek);

        setNewColor();
    }
}
