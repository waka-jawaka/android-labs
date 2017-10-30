package algie.lab3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import algie.lab3.Note;

/**
 * Created by me on 13.10.17.
 */

public class DatabaseAPI {

    private NoteDbHelper dbHelper;

    public DatabaseAPI(Context context) {
        dbHelper = new NoteDbHelper(context);
    }

    public long createNote(String name, String desc, int imp, String imagePath, String datetime) {
        ContentValues values = new ContentValues();
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_NAME, name);
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_DESC, desc);
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_IMP, imp);
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_IMAGE, imagePath);
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_DATETIME, datetime);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(NoteDBContract.NoteEntry.TABLE_NAME, null, values);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                NoteDBContract.NoteEntry.COLUMN_NAME_ID,
                NoteDBContract.NoteEntry.COLUMN_NAME_NAME,
                NoteDBContract.NoteEntry.COLUMN_NAME_DESC,
                NoteDBContract.NoteEntry.COLUMN_NAME_IMP,
                NoteDBContract.NoteEntry.COLUMN_NAME_IMAGE,
                NoteDBContract.NoteEntry.COLUMN_NAME_DATETIME
        };

        Cursor c = db.query(NoteDBContract.NoteEntry.TABLE_NAME, projection,
                null, null, null, null, null);
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(NoteDBContract.NoteEntry.COLUMN_NAME_ID));
            String name = c.getString(c.getColumnIndex(NoteDBContract.NoteEntry.COLUMN_NAME_NAME));
            String desc = c.getString(c.getColumnIndex(NoteDBContract.NoteEntry.COLUMN_NAME_DESC));
            int imp = c.getInt(c.getColumnIndex(NoteDBContract.NoteEntry.COLUMN_NAME_IMP));
            String image = c.getString(c.getColumnIndex(NoteDBContract.NoteEntry.COLUMN_NAME_IMAGE));
            String datetime = c.getString(c.getColumnIndex(NoteDBContract.NoteEntry.COLUMN_NAME_DATETIME));
            notes.add(new Note(id, name, desc, image, imp, datetime));
        }
        c.close();
        return notes;
    }

    public void deleteNote(Note note) {
        String selection = NoteDBContract.NoteEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(note.getId()) };
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(NoteDBContract.NoteEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void updateNote(Note note) {
        deleteNote(note);
        ContentValues values = new ContentValues();
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_ID, note.getId());
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_NAME, note.getName());
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_DESC, note.getDescription());
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_IMP, note.getImportance());
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_IMAGE, note.getImagePath());
        values.put(NoteDBContract.NoteEntry.COLUMN_NAME_DATETIME, note.getDatetime());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(NoteDBContract.NoteEntry.TABLE_NAME, null, values);
    }
}
