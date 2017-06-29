package encore.kz.movieapp;

/**
 * Created by hg1zadr on 6/29/2017.
 */


import encore.kz.movieapp.model.MovieWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("movie/popular")
    Call<MovieWrapper> getMovies();

    @POST("register")
    Call<Void> register(@Query("api_key") String key);

}