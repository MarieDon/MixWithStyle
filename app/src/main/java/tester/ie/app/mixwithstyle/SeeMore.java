package tester.ie.app.mixwithstyle;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import tester.ie.app.mixwithstyle.model.Ingredients;
import tester.ie.app.mixwithstyle.utils.Constants;

public class SeeMore extends AppCompatActivity
{
    public static final String DETAILSURL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
    public String cocktailID;
    public String desc = "";
    public Bundle bundle;
    private Cocktail cocktails;
    private RequestQueue queue;
    private ImageView cocktailDetailsImg;
    private TextView cocktailDetailsTitle;
    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        bundle = new Bundle();

        myRef.setValue("Praise the lord!!");

        cocktailDetailsImg = findViewById(R.id.cocktail_details_img);
        cocktailDetailsTitle = findViewById(R.id.cocktail_details_title);
        tablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        queue = Volley.newRequestQueue(this);
        cocktails = (Cocktail) getIntent().getSerializableExtra("cocktail");
        String id = cocktails.getDrinkID();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setText(getResources().getString(R.string.category_ingredients));
        tablayout.getTabAt(1).setText(getResources().getString(R.string.category_method));
        tablayout.setTabTextColors(R.color.colorBlack, R.color.colorPrimary);

        getCocktailDetails(id);


    }

    private void getCocktailDetails(String id) {
        final ArrayList<String> ingredients = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.DETAILSURL + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray drink = response.getJSONArray("drinks");
                    JSONObject drinkObj = drink.getJSONObject(0);
                    Picasso.get().load(drinkObj.getString("strDrinkThumb")).into(cocktailDetailsImg);
                    cocktailDetailsTitle.setText(drinkObj.getString("strDrink"));
                    ingredients.add(drinkObj.getString("strIngredient1"));
                    ingredients.add(drinkObj.getString("strIngredient2"));
                    ingredients.add(drinkObj.getString("strIngredient3"));
                    ingredients.add(drinkObj.getString("strIngredient4"));
                    ingredients.add(drinkObj.getString("strIngredient5"));
                    ingredients.add(drinkObj.getString("strIngredient6"));
                    desc = drinkObj.getString("strInstructions");

                    bundle.putString("DESC", desc);

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
        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS", MODE_PRIVATE).edit();
        editor.putString("DESC",description);
        editor.apply();
    }
}
