package algie.lab_2_1;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by me on 03.10.17.
 */

class NoteHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    CardView cardView;
    TextView nameTextView;
    TextView descriptionTextView;
    TextView datetimeTextView;
    ImageView importanceImageView;
    ImageView imageView;

    NoteHolder(View v) {
        super(v);
        v.setOnCreateContextMenuListener(this);

        cardView = (CardView) v.findViewById(R.id.card_view);
        nameTextView = (TextView) v.findViewById(R.id.name_text_view);
        descriptionTextView = (TextView) v.findViewById(R.id.description_text_view);
        datetimeTextView = (TextView) v.findViewById(R.id.datetime_text_view);
        importanceImageView = (ImageView) v.findViewById(R.id.importance_image_view);
        imageView = (ImageView) v.findViewById(R.id.image_view);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Resources r = v.getResources();
        menu.setHeaderTitle(r.getString(R.string.context_menu_title));
        menu.add(0, v.getId(), 0, R.string.delete);
        menu.add(0, v.getId(), 0, R.string.edit);
    }
}
