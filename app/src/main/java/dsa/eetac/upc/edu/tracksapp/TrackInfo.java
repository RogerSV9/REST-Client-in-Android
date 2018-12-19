package dsa.eetac.upc.edu.tracksapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackInfo extends AppCompatActivity {
    TextView updateTrackTitle;
    TextView updateTrackSinger;
    Button updateTrackbtn;
    private APIService APIservice;
    public Track updateTrack;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_info);


        updateTrackTitle = findViewById(R.id.update_track_title);
        updateTrackSinger = findViewById(R.id.update_track_singer);
        updateTrackbtn = findViewById(R.id.update_track_btn);

        Intent intent = getIntent();
        String messageId = intent.getStringExtra("TRACK ID");
        String[] messageIdParts = messageId.split(" ");
        int id = Integer.parseInt(messageIdParts[1]);
        String title = intent.getStringExtra("TRACK TITLE");
        String[] titleparts = title.split(":");
        updateTrackTitle.setText(titleparts[1]);
        String singer = intent.getStringExtra("TRACK SINGER");
        String[] singerparts = singer.split(":");
        updateTrackSinger.setText(singerparts[1]);

        updateTrack = new Track(id, title, singer);
        createAPIService();
        //APIservice.getTracks().enqueue(TracksCallback);

        updateTrackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Progress loading
                progressDialog = new ProgressDialog(TrackInfo.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Waiting for the server");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                updateTrack(updateTrack);
            }
        });
    }

    private void createAPIService() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIservice = retrofit.create(APIService.class);
    }
    private void updateTrack(Track updateTrack) {
        Call<Void> trackCall = APIservice.updateTrack(updateTrack);

        trackCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    progressDialog.hide();
                } else {
                    Log.e("No api connection", response.message());

                    progressDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("No api connection: ", t.getMessage());

                progressDialog.hide();

            }
        });
    }
}
