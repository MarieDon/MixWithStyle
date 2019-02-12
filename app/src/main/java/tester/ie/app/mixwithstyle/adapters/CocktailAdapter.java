package tester.ie.app.mixwithstyle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import tester.ie.app.mixwithstyle.R;
import tester.ie.app.mixwithstyle.model.Cocktail;

/**
 * Created by marie on 07/02/2019.
 */

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.ViewHolder> {
    public List<Cocktail> cocktailList;
    public Context ctx;

    public CocktailAdapter(List<Cocktail> cocktailList, Context ctx) {
        this.cocktailList = cocktailList;
        this.ctx = ctx;
    }

    @Override
    public CocktailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).
               inflate(R.layout.fruitycocktails, parent,false);
       return new ViewHolder(view, ctx);
    }

    @Override
    public void onBindViewHolder(CocktailAdapter.ViewHolder holder, int position) {
        Cocktail cocktails = cocktailList.get(position);
        String cocktailImage = cocktails.getImage();
        holder.title.setText(cocktails.getTitle());
        holder.desc.setText(cocktails.getDescription());
        Picasso.get().load(cocktailImage).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return cocktailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView desc;
        public ImageView image;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            image = itemView.findViewById(R.id.cocktail_image);

        }


    }
}
