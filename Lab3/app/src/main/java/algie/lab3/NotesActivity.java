package algie.lab3;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import algie.lab3.db.DatabaseAPI;

public class NotesActivity extends AppCompatActivity implements
        ImportanceFilterDialog.ImpFilterDialogListener,
        NameFilterDialog.NameFilterDialogListener {

    public static int CREATE_NOTE = 1;
    public static int EDIT_NOTE = 2;

    private static final int MY_PERMISSION = 123;

    DatabaseAPI dbapi;

    RecyclerView recyclerView;
    NotesAdapter adapter;
    ProgressBar progressBar;

    private void checkReadPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSION);
        }
    }

    private Note insertNote(String name, String desc, int imp, String imagePath) {
        SimpleDateFormat sdf = new SimpleDateFormat("d EEE HH:mm", Locale.ENGLISH);
        Date date = new Date();
        String datetime = sdf.format(date);

        long id = dbapi.createNote(name, desc, imp, imagePath, datetime);
        return new Note(id, name, desc, imagePath, imp, datetime);
    }

    private void updateNote(Note note) {
        dbapi.updateNote(note);
    }

    private void getAllNotes() {
        AsyncNotesGetter notesGetter = new AsyncNotesGetter(adapter, progressBar);
        notesGetter.execute(dbapi);
    }

    public void deleteNote(Note note) {
        dbapi.deleteNote(note);
    }

    private void editNote(Note note) {
        Intent intent = new Intent(this, NoteWriterActivity.class);
        intent.putExtra(NoteWriterActivity.NAME_TAG, note.getName());
        intent.putExtra(NoteWriterActivity.DESC_TAG, note.getDescription());
        intent.putExtra(NoteWriterActivity.IMAGE_TAG, note.getImagePath());
        intent.putExtra(NoteWriterActivity.IMP_TAG, note.getImportance());
        startActivityForResult(intent, EDIT_NOTE);
    }

    @Override
    public void filterByImportance(int imp) {
        adapter.filterNotesByImportance(imp);
    }

    @Override
    public void filterByName(String name) {
        adapter.filterNotesByName(name);
    }

    void createNote() {
        Intent intent = new Intent(this, NoteWriterActivity.class);
        startActivityForResult(intent, CREATE_NOTE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        checkReadPermission();

        dbapi = new DatabaseAPI(this);

        recyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        adapter = new NotesAdapter(this, new ArrayList<Note>());
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        getAllNotes();
        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String chosenAction = item.getTitle().toString();

        if (chosenAction.equals(getResources().getString(R.string.delete))) {
            Note note = adapter.getTappedNote();
            deleteNote(note);
            adapter.deleteNote();
        } else {
            editNote(adapter.getTappedNote());
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        DialogFragment dialog;
        Resources r = getResources();

        switch (itemId) {
            case R.id.filter_by_imp_item:
                dialog = new ImportanceFilterDialog();
                dialog.show(getFragmentManager(), r.getString(R.string.filter_by_imp_label));
                break;
            case R.id.filter_by_name_item:
                dialog = new NameFilterDialog();
                dialog.show(getFragmentManager(), r.getString(R.string.filter_by_name_label));
                break;
            case R.id.remove_filters_item:
                adapter.removeFilters();
                break;
            case R.id.create_note_item:
                createNote();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_NOTE && resultCode == RESULT_OK && null != data) {
            String name = data.getStringExtra(NoteWriterActivity.NAME_TAG);
            String desc = data.getStringExtra(NoteWriterActivity.DESC_TAG);
            int imp = data.getIntExtra(NoteWriterActivity.IMP_TAG, 0);
            String imagePath = data.getStringExtra(NoteWriterActivity.IMAGE_TAG);
            Note note = insertNote(name, desc, imp, imagePath);
            adapter.addNote(note);
        }
        else if (requestCode == EDIT_NOTE && resultCode == RESULT_OK && null != data) {
            String name = data.getStringExtra(NoteWriterActivity.NAME_TAG);
            String desc = data.getStringExtra(NoteWriterActivity.DESC_TAG);
            int imp = data.getIntExtra(NoteWriterActivity.IMP_TAG, 0);
            String imagePath = data.getStringExtra(NoteWriterActivity.IMAGE_TAG);

            Note note = adapter.getTappedNote();
            note.setName(name);
            note.setDescription(desc);
            note.setImportance(imp);
            note.setImagePath(imagePath);
            updateNote(note);
            adapter.notifyDataSetChanged();
        }
    }
}
