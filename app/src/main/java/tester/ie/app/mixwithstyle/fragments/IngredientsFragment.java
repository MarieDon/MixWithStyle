package tester.ie.app.mixwithstyle.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tester.ie.app.mixwithstyle.R;
import tester.ie.app.mixwithstyle.adapters.IngredientsAdapter;
import tester.ie.app.mixwithstyle.utils.IngredientsList;

import static tester.ie.app.mixwithstyle.MainActivity.INGREDIENTS;

public class IngredientsFragment extends Fragment {

    private View view;
    private IngredientsAdapter adapter;
    private RecyclerView ingredientsRV;
    private TextView ingredient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ingredients, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ingredient = view.findViewById(R.id.ingredient_text);
        ingredientsRV = view.findViewById(R.id.ingredients_rv);
        ingredientsRV.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new IngredientsAdapter(this.getActivity(), INGREDIENTS);
        ingredientsRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}


