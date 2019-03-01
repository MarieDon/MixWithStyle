package tester.ie.app.mixwithstyle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import tester.ie.app.mixwithstyle.adapters.ViewPagerAdapter;
import tester.ie.app.mixwithstyle.model.Cocktail;
import tester.ie.app.mixwithstyle.model.FavouriteCocktails;
import tester.ie.app.mixwithstyle.model.Ingredients;
import tester.ie.app.mixwithstyle.utils.Constants;
import tester.ie.app.mixwithstyle.utils.IngredientsList;

import static tester.ie.app.mixwithstyle.MainActivity.INGREDIENTS;

public class SeeMore extends AppCompatActivity
{
    public static final String DETAILSURL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
    public String cocktailID;
    String id, imageUrl, title, desc;
    float rating;
    public Bundle bundle;
    private Cocktail cocktails;
    private RequestQueue queue;
    private ImageView cocktailDetailsImg;
    private TextView cocktailDetailsTitle;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private FirebaseDatabase database;
    private DatabaseReference favourites;
    private FavouriteCocktails myFavourites;
    private FloatingActionButton favFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);
        database = FirebaseDatabase.getInstance();
        favourites = database.getReference("Favourites Cocktails");
        favFab = findViewById(R.id.floatingActionButton);
        bundle = new Bundle();
        cocktailDetailsImg = findViewById(R.id.cocktail_details_img);
        cocktailDetailsTitle = findViewById(R.id.cocktail_details_title);
        tablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        queue = Volley.newRequestQueue(this);
        cocktails = (Cocktail) getIntent().getSerializableExtra("cocktail");
        id = cocktails.getDrinkID();
        imageUrl = cocktails.getImage();
        title = cocktails.getTitle();
        desc = cocktails.getDescription();
        rating = cocktails.getRating();

        favFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase(imageUrl, title, desc, rating);
            }
        });


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setText("Ingredients");
        tablayout.getTabAt(1).setText("Method");
        tablayout.setTabTextColors(R.color.colorBlack, R.color.colorPrimary);

        getCocktailDetails(id);


    }

    private void getCocktailDetails(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.DETAILSURL + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray drink = response.getJSONArray("drinks");
                    JSONObject drinkObj = drink.getJSONObject(0);
                    Picasso.get().load(drinkObj.getString("strDrinkThumb")).into(cocktailDetailsImg);
                    cocktailDetailsTitle.setText(drinkObj.getString("strDrink"));
                    IngredientsList.INGREDIENTS.clear();
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient1"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient2"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient3"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient4"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient5"));
                    IngredientsList.INGREDIENTS.add(drinkObj.getString("strIngredient6"));
                    desc = drinkObj.getString("strInstructions");
                    sharedPrefs(desc);

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

    public void sharedPrefs(String description){
        SharedPreferences sharedPerfs = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPerfs.edit();
        editor.putString("DESC", description);
        editor.apply();
    }

    private void saveToFirebase(String imageUrl, String title, String desc, float rating){
        String firebaseId = database.getReference("Favourite Cocktails").push().getKey();
        myFavourites = new FavouriteCocktails(imageUrl, title, desc,  rating);
        favourites.child(firebaseId).setValue(myFavourites);
        startActivity(new Intent(SeeMore.this, FavouriteActivity.class));
    }
}