package tester.ie.app.mixwithstyle.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tester.ie.app.mixwithstyle.R;
import tester.ie.app.mixwithstyle.utils.IngredientsList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    public Context ctx;
    public List<String> ingredients;


    public IngredientsAdapter(final Context ctx, final List<String> ingredients) {
        this.ctx = ctx;
        this.ingredients = ingredients;
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ingredients_list, parent,false);
        return new IngredientsAdapter.ViewHolder(view, ctx);
    }

    @Override
    public void onBindViewHolder(final IngredientsAdapter.ViewHolder holder, final int position) {
        holder.ingredientsText.setText(IngredientsList.INGREDIENTS.get(position));
    }

    @Override
    public int getItemCount() {
        return   IngredientsList.INGREDIENTS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientsText;

        public ViewHolder(final View itemView, final Context ctx) {
            super(itemView);
            ingredientsText = itemView.findViewById(R.id.ingredient_text);
        }
    }
}
