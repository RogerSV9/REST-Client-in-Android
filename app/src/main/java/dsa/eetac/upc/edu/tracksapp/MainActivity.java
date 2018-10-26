package dsa.eetac.upc.edu.tracksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AddSongDialog.IAddSongDialogListener, DeleteSongDialog.IDeleteSongDialogListener, EditSongDialog.IEditSongDialogListener{
    private APIService APIservice;
    private Button getTracksButton;

    private RecyclerView recyclerView;

    private int id;
    private String name;
    private String singer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        createAPIService();
        APIservice.getTracks().enqueue(TracksCallback);

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
            case R.id.menu_add_song:
                showAddSongDialog();
                return true;
            case R.id.menu_delete_song:
                showDeleteSongDialog();
                return true;
            case R.id.menu_edit_song:
                showEditSongDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddSongDialog() {
        AddSongDialog dialog = new AddSongDialog();
        Bundle arguments = new Bundle();
        String ids = String.valueOf(this.id);
        arguments.putString("id", ids);
        arguments.putString("name", name);
        arguments.putString("singer", singer);
        dialog.setArguments(arguments);

        dialog.show(getSupportFragmentManager(), "AddSongDialog");
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
    @Override
    public void onDialogAddPositiveClick(String id, String name, String singer) {
        this.name = name;
        this.singer = singer;
        this.id = id.compareTo(id);
        sendPost(this.id, this.name, this.singer);
        APIservice.getTracks().enqueue(TracksCallback);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }
    public void sendPost(int id, String name, String singer){
        Track track = new Track(id, name, singer);
        APIservice.postSong(track).enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void showDeleteSongDialog() {
        DeleteSongDialog dialog = new DeleteSongDialog();
        Bundle arguments = new Bundle();
        String ids = String.valueOf(this.id);
        arguments.putString("id", ids);
        dialog.setArguments(arguments);

        dialog.show(getSupportFragmentManager(), "DeleteSongDialog");
    }
    @Override
    public void onDialogDeletePositiveClick(String id) {
        this.id = id.compareTo(id);
        sendDelete(this.id);
        APIservice.getTracks().enqueue(TracksCallback);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }
    public void sendDelete(int id){
        APIservice.deleteTrack(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MainActivity.this, Integer.toString(response.code()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void showEditSongDialog() {
        EditSongDialog dialog = new EditSongDialog();
        Bundle arguments = new Bundle();
        String ids = String.valueOf(this.id);
        arguments.putString("id", ids);
        arguments.putString("name", name);
        arguments.putString("singer", singer);
        dialog.setArguments(arguments);

        dialog.show(getSupportFragmentManager(), "EditSongDialog");
    }
    @Override
    public void onDialogEditPositiveClick(String id, String name, String singer) {
        this.name = name;
        this.singer = singer;
        this.id = id.compareTo(id);
        sendPut(this.id, this.name, this.singer);
        APIservice.getTracks().enqueue(TracksCallback);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }
    public void sendPut(int id, String name, String singer){

        Track track = new Track(id, name, singer);
        APIservice.editTrack(track).enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
