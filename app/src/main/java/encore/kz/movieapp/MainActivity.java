package encore.kz.movieapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import encore.kz.movieapp.model.Movie;
import encore.kz.movieapp.model.MovieWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMovieClickListener{

    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.content);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        Prefs.putString("test", "Hi");

        Log.d("n17r!", Prefs.getString("test", ""));

        progressDialog.show();
        PrefsApplication.get().getApi().getMovies().enqueue(new Callback<MovieWrapper>() {
            @Override
            public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("test", String.valueOf(response.body().getMovies().size()));

                    recyclerView.setAdapter(new MoviesAdapter(response.body().getMovies(), MainActivity.this));
                }
            }

            @Override
            public void onFailure(Call<MovieWrapper> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void onItemClick(Movie movie){
        Toast.makeText(this,movie.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
