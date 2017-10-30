package algie.lab3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by me on 08.10.17.
 */

public class NameFilterDialog extends DialogFragment {

    interface NameFilterDialogListener {
        void filterByName(String name);
    }

    NameFilterDialogListener dialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogListener = (NameFilterDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement NameFilterDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialogue_filter_name, null))
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = NameFilterDialog.this.getDialog();

                        EditText editText = (EditText) d.findViewById(R.id.filter_by_name_edit_text);
                        dialogListener.filterByName(editText.getText().toString());
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NameFilterDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
