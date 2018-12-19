package dsa.eetac.upc.edu.tracksapp;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIService {
    String BASE_URL = "http://147.83.7.155:8080/dsaApp/";

    @GET("tracks")
    Call<List<Track>> getTracks();

    @POST("tracks")
    Call<Track> postSong(@Body Track track);

    @PUT("tracks")
    Call<Void> updateTrack(@Body Track track);

    @DELETE("tracks/{id}")
    Call<Void> deleteTrack(@Path("id") int id);

}
