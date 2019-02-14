package tester.ie.app.mixwithstyle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tester.ie.app.mixwithstyle.adapters.ViewPagerAdapter;
import tester.ie.app.mixwithstyle.model.Cocktail;
import tester.ie.app.mixwithstyle.utils.Constants;

public class SeeMore extends AppCompatActivity
{
    public static final String DETAILSURL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
    public String cocktailID;
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.DETAILSURL + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray drink = response.getJSONArray("drinks");
                    JSONObject drinkObj = drink.getJSONObject(0);
                    Picasso.get().load(drinkObj.getString("strDrinkThumb")).into(cocktailDetailsImg);
                    cocktailDetailsTitle.setText(drinkObj.getString("strDrink"));


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
}
