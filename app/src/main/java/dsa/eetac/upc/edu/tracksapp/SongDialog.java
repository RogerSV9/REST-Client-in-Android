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

public class SongDialog extends DialogFragment {
    EditText nameEditText;
    EditText singerEditText;
    ISongDialogListener listener;

    public interface ISongDialogListener {
        void onDialogPositiveClick(String name, String singer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof ISongDialogListener) {
            listener = (ISongDialogListener) getActivity();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.song_info, null);
        nameEditText = (EditText) view.findViewById(R.id.name_edittext);
        singerEditText = (EditText) view.findViewById(R.id.singer_edittext);

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
                            listener.onDialogPositiveClick(nameEditText.getText().toString(), singerEditText.getText().toString());
                        }
                    }
                });
        return builder.create();
    }
}
