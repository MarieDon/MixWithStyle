package tester.ie.app.mixwithstyle;

import android.support.design.widget.TabLayout;
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

import tester.ie.app.mixwithstyle.model.Cocktail;
import tester.ie.app.mixwithstyle.utils.Constants;

public class SeeMore extends AppCompatActivity
{
    public static final String DETAILSURL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
    public String cocktailID;
    private Cocktail cocktails;
    private RequestQueue queue;
    private ImageView cocktail_details_img;
    private TextView cocktail_details_title;
    private TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        cocktail_details_img = findViewById(R.id.cocktail_details_img);
        cocktail_details_title = findViewById(R.id.cocktail_details_title);
        tablayout = findViewById(R.id.tablayout);
        queue = Volley.newRequestQueue(this);
        cocktails = (Cocktail) getIntent().getSerializableExtra("cocktail");
        String id = cocktails.getDrinkID();

        getCardDetails(id);

    }


    private void getCardDetails(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, DETAILSURL + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String imgThumb = response.getString("strDrinkThumb");
                    Picasso.get().load(imgThumb).into(cocktail_details_img);
//                    cock.title = cocktails.setTitle();
//                    cock.description = "Margharita";
//                    cock.drinkID = cocktails.setDrinkID();
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
