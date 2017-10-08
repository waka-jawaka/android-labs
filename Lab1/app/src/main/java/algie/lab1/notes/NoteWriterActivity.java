package algie.lab1.notes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import algie.lab1.R;

/**
 * Created by me on 03.10.17.
 */

public class NoteWriterActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;

    Button loadImageButton;
    Button createNoteButton;
    EditText nameEditText;
    EditText descEditText;
    RadioGroup radioGroup;
    String imagePath;

    public static String NAME_TAG = "NAME_TAG";
    public static String DESC_TAG = "DESC_TAG";
    public static String IMP_TAG = "IMP_TAG";
    public static String IMAGE_TAG = "IMAGE_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writer);

        nameEditText = (EditText) findViewById(R.id.writer_name_edit_text);
        descEditText = (EditText) findViewById(R.id.writer_desc_edit_text);
        radioGroup = (RadioGroup) findViewById(R.id.writer_radio_group);

        loadImageButton = (Button) findViewById(R.id.load_image_button);
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        createNoteButton = (Button) findViewById(R.id.note_create_button);
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(NAME_TAG, nameEditText.getText().toString());
                intent.putExtra(DESC_TAG, descEditText.getText().toString());

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                intent.putExtra(IMP_TAG, radioButton.getText().toString());
                intent.putExtra(IMAGE_TAG, imagePath);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

//            ImageView imageView = (ImageView) findViewById(R.id.imgView);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }
}
