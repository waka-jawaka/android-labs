package algie.lab1.notes;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import algie.lab1.R;

public class NotesActivity extends AppCompatActivity implements
        ImportanceFilterDialog.ImpFilterDialogListener,
        NameFilterDialog.NameFilterDialogListener {

    public static int CREATE_NOTE = 1;
    public static int EDIT_NOTE = 2;

    NotesAdapter adapter;
    RecyclerView recyclerView;

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

        recyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotesAdapter(this);
        recyclerView.setAdapter(adapter);
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
            Note note = new Note(name, desc, imagePath, imp, new Date());
            adapter.addNote(note);
        }
        if (requestCode == EDIT_NOTE && resultCode == RESULT_OK && null != data) {
            String name = data.getStringExtra(NoteWriterActivity.NAME_TAG);
            String desc = data.getStringExtra(NoteWriterActivity.DESC_TAG);
            int imp = data.getIntExtra(NoteWriterActivity.IMP_TAG, 0);
            String imagePath = data.getStringExtra(NoteWriterActivity.IMAGE_TAG);

            Note note = adapter.getTappedNote();
            note.setName(name);
            note.setDescription(desc);
            note.setImportance(imp);
            note.setImagePath(imagePath);
            adapter.notifyDataSetChanged();
        }
    }
}
