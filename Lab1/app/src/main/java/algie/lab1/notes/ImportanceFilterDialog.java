package algie.lab1.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RadioButton;

import algie.lab1.R;

/**
 * Created by me on 08.10.17.
 */

public class ImportanceFilterDialog extends DialogFragment {

    interface ImpFilterDialogListener {
        void filterByImportance(int importance);
    }

    ImpFilterDialogListener dialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogListener = (ImpFilterDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement ImpFilterDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialogue_filter_importance, null))
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = ImportanceFilterDialog.this.getDialog();

                        RadioButton radio1 = (RadioButton) d.findViewById(R.id.radio_1);
                        RadioButton radio2 = (RadioButton) d.findViewById(R.id.radio_2);

                        int imp = 0;
                        if (radio1.isChecked()) {
                            imp = 1;
                        } else if (radio2.isChecked()) {
                            imp = 2;
                        }
                        dialogListener.filterByImportance(imp);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ImportanceFilterDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
