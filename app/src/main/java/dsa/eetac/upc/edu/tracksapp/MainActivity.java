package dsa.eetac.upc.edu.tracksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private APIService APIservice;
    private Button getTracksButton;

    private RecyclerView recyclerView;

    private String name;
    private String singer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getTracksButton = (Button) findViewById(R.id.gettracks_button);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        createAPIService();
        getTracksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIservice.getTracks().enqueue(TracksCallback);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_song:
                showSongDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSongDialog() {
        SongDialog dialog = new SongDialog();
        Bundle arguments = new Bundle();
        arguments.putString("name", name);
        arguments.putString("singer", singer);
        dialog.setArguments(arguments);

        dialog.show(getSupportFragmentManager(), "credentialsDialog");
    }

    Callback<List<Track>> TracksCallback = new Callback<List<Track>>() {
        @Override
        public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
            if (response.isSuccessful()) {
                List<Track> data = new ArrayList<>();
                data.addAll(response.body());
                recyclerView.setAdapter(new RecyclerViewAdapter(data));
                //List<Track> Tracks = response.body();
                //ArrayAdapter<Track> arrayAdapter = new ArrayAdapter<Track>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, Tracks);
                //TracksSpinner.setAdapter(arrayAdapter);
            } else {
                Log.d("TracksCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Track>> call, Throwable t) {
            t.printStackTrace();
        }
    };
}
