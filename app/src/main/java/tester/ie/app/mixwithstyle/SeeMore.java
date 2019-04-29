package tester.ie.app.mixwithstyle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tester.ie.app.mixwithstyle.adapters.IngredientsAdapter;
import tester.ie.app.mixwithstyle.adapters.ViewPagerAdapter;
import tester.ie.app.mixwithstyle.model.Cocktail;
import tester.ie.app.mixwithstyle.model.FavouriteCocktails;
import tester.ie.app.mixwithstyle.model.Ingredients;
import tester.ie.app.mixwithstyle.utils.Constants;
import tester.ie.app.mixwithstyle.utils.IngredientsList;
import tester.ie.app.mixwithstyle.utils.PreferenceHelper;

import static tester.ie.app.mixwithstyle.MainActivity.INGREDIENTS;

public class SeeMore extends BaseActivity
{
    public String cocktailID;
    String id, imageUrl, title, desc, glass;
    public boolean isAlcoholic;
    float rating;
    public Bundle bundle;
    private Cocktail cocktails;
    private RequestQueue queue;
    private ImageView cocktailDetailsImg;
    private TextView cocktailDetailsTitle;
    private FirebaseDatabase database;
    private DatabaseReference favourites;
    private FavouriteCocktails myFavourites;
    private FloatingActionButton favFab;
    private Button ingredBtn;
    private Button methodBtn;
    private TextView methodText;
    private IngredientsAdapter adapter;
    private RecyclerView ingredientsRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_see_more, null, false);
        drawer.addView(view, 0);
        database = FirebaseDatabase.getInstance();
        favourites = database.getReference("Favourites Cocktails");
        favFab = findViewById(R.id.floatingActionButton);
        this.setTitle("Cocktail Ingredients");
        bundle = new Bundle();
        cocktailDetailsImg = findViewById(R.id.cocktail_details_img);
        cocktailDetailsTitle = findViewById(R.id.cocktail_details_title);
        ingredientsRV = findViewById(R.id.ingredients_rv);
        ingredBtn = findViewById(R.id.ingredients_btn);
        methodBtn = findViewById(R.id.method_btn);
        methodText = findViewById(R.id.method_text);
        queue = Volley.newRequestQueue(this);
        cocktails = (Cocktail) getIntent().getSerializableExtra("cocktail");
        id = cocktails.getDrinkID();
        imageUrl = cocktails.getImage();
        title = cocktails.getTitle();
        desc = cocktails.getDescription();
        rating = cocktails.getDrinksRating();
        ingredBtn.setVisibility(View.GONE);
        methodText.setVisibility(View.GONE);

        favFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase(imageUrl, title, desc, rating);
                favFab.setImageResource(R.drawable.ic_favorite_filled);
                Toast.makeText(SeeMore.this, "Added to favourites", Toast.LENGTH_SHORT).show();
            }
        });



        ingredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBtn.setVisibility(View.VISIBLE);
                ingredBtn.setVisibility(View.GONE);
                ingredientsRV.setVisibility(View.VISIBLE);
                methodText.setVisibility(View.GONE);
                SeeMore.this.setTitle("Cocktail Ingredients");

            }
        });

        methodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodBtn.setVisibility(View.GONE);
                ingredBtn.setVisibility(View.VISIBLE);
                ingredientsRV.setVisibility(View.GONE);
                methodText.setVisibility(View.VISIBLE);
                SeeMore.this.setTitle("Method");

            }
        });






    }

    @Override
    protected void onResume() {
        super.onResume();
        getCocktailDetails(id, cocktailDetailsTitle, cocktailDetailsImg);
    }

    private void saveToFirebase(String imageUrl, String title, String desc, float rating){
        String firebaseId = database.getReference("Favourite Cocktails").push().getKey();
        myFavourites = new FavouriteCocktails(imageUrl, title, desc,  rating);
        favourites.child(firebaseId).setValue(myFavourites);
    }

    public void getCocktailDetails(String id, final TextView title, final ImageView img) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.DETAILSURL + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray drink = response.getJSONArray("drinks");
                    JSONObject drinkObj = drink.getJSONObject(0);
                    Picasso.get().load(drinkObj.getString("strDrinkThumb")).into(img);
                    title.setText(drinkObj.getString("strDrink"));
                    IngredientsList.INGREDIENTS.clear();
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient1"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient2"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient3"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient4"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient5"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient6"));
                    String desc = drinkObj.getString("strInstructions");

                    methodText.setText(desc);

                    ingredientsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new IngredientsAdapter(getApplicationContext(), IngredientsList.INGREDIENTS);
                    ingredientsRV.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void logout(View view){

    }

}