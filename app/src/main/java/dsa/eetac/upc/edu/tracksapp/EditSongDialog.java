package dsa.eetac.upc.edu.tracksapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

public class EditSongDialog extends DialogFragment {
    EditText idEditText;
    EditText nameEditText;
    EditText singerEditText;
    EditSongDialog.IEditSongDialogListener listener;

    public interface IEditSongDialogListener {
        void onDialogEditPositiveClick(String id, String name, String singer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof EditSongDialog.IEditSongDialogListener) {
            listener = (EditSongDialog.IEditSongDialogListener) getActivity();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.edit_song, null);
        idEditText = (EditText) view.findViewById(R.id.id_edittext);
        nameEditText = (EditText) view.findViewById(R.id.name_edittext);
        singerEditText = (EditText) view.findViewById(R.id.singer_edittext);

        idEditText.setText(getArguments().getString("id"));
        nameEditText.setText(getArguments().getString("name"));
        singerEditText.setText(getArguments().getString("singer"));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Song information")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDialogEditPositiveClick(idEditText.getText().toString(), nameEditText.getText().toString(), singerEditText.getText().toString());
                        }
                    }
                });
        return builder.create();
    }
}
