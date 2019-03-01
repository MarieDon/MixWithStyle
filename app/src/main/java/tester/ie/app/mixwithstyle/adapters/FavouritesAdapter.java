package tester.ie.app.mixwithstyle.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


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
    public List<FavouriteCocktails> favouritesList;
    public Context ctx;

    public FavouritesAdapter(List<FavouriteCocktails> favouritesList, Context ctx) {
        this.favouritesList = favouritesList;
        this.ctx = ctx;
    }

    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_favourite, parent,false);
        return new ViewHolder(view, ctx);
    }

    @Override
    public void onBindViewHolder(FavouritesAdapter.ViewHolder holder, int position) {
        FavouriteCocktails  favcocktails = favouritesList.get(position);
        String cocktailImage = favcocktails.getImage();
        holder.title.setText(favcocktails.getTitle());
//        holder.moreDetails.setText(cocktails.getDescription());
        Picasso.get().load(cocktailImage).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public Button faves;
        public ImageView image;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);
            title = itemView.findViewById(R.id.title1);
            image = itemView.findViewById(R.id.cocktail_image1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavouriteCocktails favcocktail = favouritesList.get(getAdapterPosition());
                    Intent intent = new Intent(context, SeeMore.class);
                   // intent.putExtra("favourite cocktail",  favcocktail);
                    ctx.startActivity(intent);
                    ((CocktailRecyclerView) ctx).recreate();

                }
            });

//            faves.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    faves.setCompoundDrawablesWithIntrinsicBounds(null, null, v.getResources().getDrawable(android.R.drawable.btn_star_big_on),null);
//                }
//            });

        }


        @Override
        public void onClick(View v) {

        }
    }



}
