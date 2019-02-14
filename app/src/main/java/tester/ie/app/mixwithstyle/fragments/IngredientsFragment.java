package tester.ie.app.mixwithstyle.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tester.ie.app.mixwithstyle.R;

public class IngredientsFragment extends Fragment {

    private View view;
    private TextView ingred1;
    private TextView ingred2;
    private TextView ingred3;
    private TextView ingred4;
    private TextView ingred5;
    private TextView ingred6;
    public IngredientsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ingredients, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ingred1 = view.findViewById(R.id.ingredients_one_text);
        ingred2 = view.findViewById(R.id.ingredients_two_text);
        ingred3 = view.findViewById(R.id.ingredients_three_text);
        ingred4 = view.findViewById(R.id.ingredients_four_text);
        ingred5 = view.findViewById(R.id.ingredients_five_text);
        ingred6 = view.findViewById(R.id.ingredients_six_text);
    }
}
