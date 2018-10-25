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

public class DeleteSongDialog extends DialogFragment {
    EditText idEditText;
    DeleteSongDialog.IDeleteSongDialogListener listener;

    public interface IDeleteSongDialogListener {
        void onDialogDeletePositiveClick(String id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IDeleteSongDialogListener) {
            listener = (IDeleteSongDialogListener) getActivity();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.delete_song, null);
        idEditText = (EditText) view.findViewById(R.id.id2_edittext);

        idEditText.setText(getArguments().getString("name"));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("ID information")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDialogDeletePositiveClick(idEditText.getText().toString());
                        }
                    }
                });
        return builder.create();
    }
}
