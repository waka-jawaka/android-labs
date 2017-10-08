package algie.lab1.notes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import algie.lab1.R;

/**
 * Created by me on 03.10.17.
 */

class NoteLoader {
    static List<Note> loadNotes() {
        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note("Django", "Django Django Django Django Django Django Django Django Django", R.drawable.note_image, 1, new Date()));
        notes.add(new Note("Scott Pilgrim", "Scott Pilgrim Scott Pilgrim Scott Pilgrim Scott Pilgrim", R.drawable.note_image, 0, new Date()));
        notes.add(new Note("ZBrush", "ZBrush ZBrush ZBrush ZBrush ZBrush ZBrush ZBrush ZBrush ZBrush ", R.drawable.note_image, 2, new Date()));
        return notes;
    }
}
