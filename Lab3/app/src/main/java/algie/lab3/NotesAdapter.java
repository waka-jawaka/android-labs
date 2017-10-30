package algie.lab3;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by me on 03.10.17.
 */

class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {

    private static final String TAG = "ERRPR";
    private Context context;

    private List<Note> notesBackup;

    public List<Note> notes = new ArrayList<>();
    private int position;

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

    Note getTappedNote() {
        return notes.get(getPosition());
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

    void addNote(Note note) {
        notes.add(note);
        notifyDataSetChanged();
    }

    public NotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(final NoteHolder holder, int position) {
        Note note = notes.get(position);
        holder.nameTextView.setText(note.getName());
        holder.descriptionTextView.setText(note.getDescription());
        holder.datetimeTextView.setText(note.getDatetime());

        if (note.getImagePath() != null) {
            Uri imgUri = Uri.parse(note.getImagePath());
            holder.imageView.setImageURI(null);
            holder.imageView.setImageURI(imgUri);
        }

        Integer id = notes.get(position).getImpImageId();
        if (id != null) {
            holder.importanceImageView.setImageResource(id);
        } else {
            holder.importanceImageView.setImageResource(android.R.color.transparent);
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
