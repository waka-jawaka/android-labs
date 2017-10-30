package algie.lab3;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.List;

import algie.lab3.db.DatabaseAPI;

/**
 * Created by me on 30.10.17.
 */

public class AsyncNotesGetter extends AsyncTask<DatabaseAPI, Integer, List<Note>> {

    private NotesAdapter adapter;
    private ProgressBar progressBar;

    public AsyncNotesGetter(NotesAdapter adapter, ProgressBar progressBar) {
        super();
        this.adapter = adapter;
        this.progressBar = progressBar;
    }

    @Override
    protected List<Note> doInBackground(DatabaseAPI... params) {
        try {
            for (int i = 1; i < 101; i++) {
                Thread.sleep(50);
                publishProgress(i);
            }
        } catch (InterruptedException e) { }
        return params[0].getAllNotes();
    }

    @Override
    protected void onPostExecute(List<Note> notes) {
        progressBar.setVisibility(ProgressBar.GONE);
        adapter.notes = notes;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }
}
