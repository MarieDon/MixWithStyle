package tester.ie.app.mixwithstyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tester.ie.app.mixwithstyle.adapters.CocktailAdapter;
import tester.ie.app.mixwithstyle.model.Cocktail;
import tester.ie.app.mixwithstyle.utils.Constants;

public class CocktailRecyclerView extends AppCompatActivity {

    private RequestQueue queue;
    private RecyclerView recyclerView;
    private List<Cocktail> cocktailList;
    private CocktailAdapter cocktailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        queue = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cocktailList = new ArrayList<>();
        cocktailList = getCocktailList();
        cocktailAdapter = new CocktailAdapter(cocktailList, this);
        recyclerView.setAdapter(cocktailAdapter);
        cocktailAdapter.notifyDataSetChanged();
    }

    public List<Cocktail> getCocktailList()
    {
        JsonObjectRequest CocktailRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL+"filter.php?c=Cocktail", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("Responses", response.toString());
                    JSONArray Drinks = response.getJSONArray("drinks");

                    for (int i = 0; i < Drinks.length(); i++) {
                        JSONObject drinkArray = Drinks.getJSONObject(i);
                        Cocktail cock = new Cocktail();
                        cock.image = drinkArray.getString("strDrinkThumb");
                        cock.title = drinkArray.getString("strDrink");
                        cock.description = "Margharita";
                        cock.drinkID = drinkArray.getString("idDrink");
                        cocktailList.add(cock);
                    }
                    cocktailAdapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(CocktailRequest);
        return cocktailList;
    }

}
