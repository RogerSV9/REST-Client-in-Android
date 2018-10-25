package dsa.eetac.upc.edu.tracksapp;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Track> data;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView text2;

        public ViewHolder(View v) {
            super(v);
            //text = (TextView) v.findViewById(android.R.id.text1);
            text = (TextView) v.findViewById(R.id.text_view_id);
            text2 = (TextView) v.findViewById(R.id.text_view_id2);

            //text2 = (TextView) v.findViewById(android.R.id.text2);
        }
    }
    public RecyclerViewAdapter(List<Track> data) {
        this.data = data;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //v = inflater.inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Track Track = ((Track) data.get(position));
        holder.text.setText(Track.title);
        holder.text2.setText(Track.singer);
        holder.itemView.setTag(Track.id);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
