package tester.ie.app.mixwithstyle.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import tester.ie.app.mixwithstyle.CocktailRecyclerView;
import tester.ie.app.mixwithstyle.MainActivity;
import tester.ie.app.mixwithstyle.R;
import tester.ie.app.mixwithstyle.SeeMore;
import tester.ie.app.mixwithstyle.model.Cocktail;
import tester.ie.app.mixwithstyle.model.FavouriteCocktails;

/**
 * Created by marie on 07/02/2019.
 */



public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    private ItemClickListener clickListener;
    public List<FavouriteCocktails> favouritesList;
    public Context ctx;


    public FavouritesAdapter(List<FavouriteCocktails> favouritesList, Context ctx) {
        this.favouritesList = favouritesList;
        this.ctx = ctx;

    }

    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.favourites_list, parent, false);
        return new ViewHolder(view, ctx);
    }

    @Override
    public void onBindViewHolder(FavouritesAdapter.ViewHolder holder, int position) {
        FavouriteCocktails favourites = favouritesList.get(position);
        String cocktailImage = favourites.getImage();
        holder.title.setText(favourites.getTitle());
        holder.ratingBar.setRating(favourites.getRating());
        holder.ratingBar.setIsIndicator(true);
        holder.ratingBar.setClickable(false);
        Picasso.get().load(cocktailImage).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView image;
        public RatingBar ratingBar;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);
            ctx = context;
            itemView.setOnClickListener(this); // bind the listener
            title = itemView.findViewById(R.id.favourites_text);
            image = itemView.findViewById(R.id.favourites_image);
            ratingBar = itemView.findViewById(R.id.favouritesRating);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}


