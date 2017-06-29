package encore.kz.movieapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import encore.kz.movieapp.model.Movie;

/**
 * Created by hg1zadr on 6/29/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieItemViewHolder> {

    private List<Movie> items;
    private OnMovieClickListener callback;

    public MoviesAdapter(List <Movie> items, OnMovieClickListener listener){
        this.callback = listener;
        this.items = items;
    }

    @Override
    public MoviesAdapter.MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MovieItemViewHolder(inflater.inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MovieItemViewHolder holder, int position) {
        final Movie movie = items.get(position);

        Glide.with(holder.imgCover.getContext())
                .load("http://image.tmdb.org/t/p/w185" + movie.getPosterPath())
                .into(holder.imgCover);
        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(callback != null){
                    callback.onItemClick(movie);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private ImageView imgCover;

        public MovieItemViewHolder(View itemView){
            super(itemView);
            view = itemView;
            imgCover = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public interface OnMovieClickListener{
        void onItemClick(Movie movie);
    }
}
