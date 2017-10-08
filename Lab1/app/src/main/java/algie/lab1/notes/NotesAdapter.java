package algie.lab1.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import algie.lab1.R;

/**
 * Created by me on 03.10.17.
 */

class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {

    private List<Note> notesBackup;

    private List<Note> notes = new ArrayList<>();
    private int position;

    private void loadNotes() {
        notes = NoteLoader.loadNotes();
    }

    int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    void deleteNote() {
        notes.remove(getPosition());
        notifyDataSetChanged();
    }

    private void saveNotesBeforeFilter() {
        if (notesBackup == null) {
            notesBackup = notes;
        }
    }

    void removeFilters() {
        if (notesBackup == null) {
            return;
        }
        notes = new ArrayList<>();
        notes.addAll(notesBackup);
        notesBackup = null;
        notifyDataSetChanged();
    }

    void filterNotesByName(String pattern) {
        saveNotesBeforeFilter();
        List<Note> filtered = new ArrayList<>();
        String patternLower = pattern.toLowerCase();
        for (Note note : notes) {
            if (note.getName().toLowerCase().contains(patternLower)) {
                filtered.add(note);
            }
        }
        notes = filtered;
        notifyDataSetChanged();
    }

    void filterNotesByImportance(int imp) {
        saveNotesBeforeFilter();
        List<Note> filtered = new ArrayList<>();
        for (Note note : notes) {
            if (note.getImportance() == imp) {
                filtered.add(note);
            }
        }
        notes = filtered;
        notifyDataSetChanged();
    }

    void addNote(String name, String desc, String imp) {
        Note note = new Note(name, desc, R.drawable.note_image, 2, new Date());
        notes.add(note);
        notifyDataSetChanged();
    }

    public NotesAdapter(Context context) {
        loadNotes();
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(final NoteHolder holder, int position) {
        holder.nameTextView.setText(notes.get(position).getName());
        holder.descriptionTextView.setText(notes.get(position).getDescription());
        holder.datetimeTextView.setText(notes.get(position).getDatetime());

        Integer id = notes.get(position).getImpImageId();
        if (id != null) {
            holder.importanceImageView.setImageResource(id);
        }

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getLayoutPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
