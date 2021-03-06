package com.example.android.populatmovies.utilities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.populatmovies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by krypt on 20/03/2017.
 */

public class MovieCursorAdapter extends CursorRecyclerViewAdapter<MovieCursorAdapter.ViewHolder> {

    final private PosterCursorItemClickListener mOnClickListener;

    private Context context;

    // Add an interface called osterCursorItemClickListener
    // Within that interface, define a void method called onListItemClick that takes an int as a parameter
    public interface PosterCursorItemClickListener {
        void onPosterItemCursorClick(int clickedPosterCursorIndex);
    }

    // Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener
    /*
       ADAPTER
    */
    public MovieCursorAdapter(Context context, Cursor cursor, PosterCursorItemClickListener cursorListener) {
        super(context, cursor);
        mOnClickListener = cursorListener;
    }


    // Override our 3 functions
    // onCreateViewHolder()
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;

        // Inflate our new item view using a LayoutInflater. It takes the ID of layout in xml.
        // Then --> inflates or converts this collection of view groups and views.
        LayoutInflater inflater = LayoutInflater.from(context);


        // Set to false, so that the inflated layout will not be
        // immediately attached to its parent viewgroup.
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //onBindViewHolder()
    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        Movie movies = null;
        movies = Movie.fromCursor(cursor);

        // Set item views based on your views and data model
        ImageView imageView = holder.posterView;

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w342/" + movies.getPoster())
                .into(imageView);

    }


    //getItemCount() : returns the mNumberItems var
    @Override
    public int getItemCount() {
        Cursor cursor = getCursor();
        return cursor.getCount();
    }

    /*
        VIEW HOLDER
    */

    /**
     * Cache of the children views for a list item.
     */

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private String mItem;
        // Create a ImageView variable called posterView
        ImageView posterView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         */
        public ViewHolder(View itemView) {
            super(itemView);

            // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            posterView = (ImageView) itemView.findViewById(R.id.movie_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onPosterItemCursorClick(clickedPosition);
        }
    }
}
