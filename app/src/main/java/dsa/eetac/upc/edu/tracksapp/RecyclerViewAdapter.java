package dsa.eetac.upc.edu.tracksapp;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Track> data;
    Context context;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView text2;
        public TextView text3;
        private LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            //text = (TextView) v.findViewById(android.R.id.text1);
            text = (TextView) v.findViewById(R.id.text_view_id);
            text2 = (TextView) v.findViewById(R.id.text_view_id2);
            text3 = (TextView) v.findViewById(R.id.text_view_id3);
            linearLayout = v.findViewById(R.id.linearLayout);
            //text2 = (TextView) v.findViewById(android.R.id.text2);
        }
    }
    public RecyclerViewAdapter(List<Track> data) {
        this.data = data;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        context = parent.getContext();
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //v = inflater.inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Track Track = ((Track) data.get(position));
        holder.text.setText("ID: "+ Track.id);
        holder.text2.setText("Title: "+Track.title);
        holder.text3.setText("Singer: "+Track.singer);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackInfo.class);
                TextView textId = v.findViewById(R.id.text_view_id);
                TextView textTitle = v.findViewById(R.id.text_view_id2);
                TextView textSinger = v.findViewById(R.id.text_view_id3);
                String messageId = textId.getText().toString();
                String[] messageIdparts = messageId.split(":");
                String id = messageIdparts[1];
                String messageTitle = textTitle.getText().toString();
                String messageSinger = textSinger.getText().toString();
                intent.putExtra("TRACK ID", id);
                intent.putExtra("TRACK TITLE", messageTitle);
                intent.putExtra("TRACK SINGER", messageSinger);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
