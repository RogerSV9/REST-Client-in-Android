package dsa.eetac.upc.edu.tracksapp;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.Call;

public interface APIService {
    String BASE_URL = "http://147.83.7.155:8080/dsaApp/";

    @GET("tracks")
    Call<List<Track>> getTracks();

}
