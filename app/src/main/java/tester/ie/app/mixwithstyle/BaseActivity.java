package tester.ie.app.mixwithstyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tester.ie.app.mixwithstyle.utils.Constants;
import tester.ie.app.mixwithstyle.utils.IngredientsList;
import tester.ie.app.mixwithstyle.utils.PreferenceHelper;

public class BaseActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawer;
    public Toolbar toolbar;
    private RequestQueue queue;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public TextView username;
    public TextView email;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        drawer = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView = navigationView.getHeaderView(0);
        username = navHeaderView.findViewById(R.id.username);
        email = navHeaderView.findViewById(R.id.user_email);
        username.setText(user.getDisplayName());
         email.setText(user.getEmail());

        queue = Volley.newRequestQueue(this);

        Log.i("USER", "Hello " + mAuth.getCurrentUser().getDisplayName() + " " + user.getEmail().toString());



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void logout(View view){
        mAuth.signOut();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cocktail_list) {
            startActivity(new Intent(BaseActivity.this, CocktailRecyclerView.class));

        } else if (id == R.id.favourites) {
            startActivity(new Intent(BaseActivity.this, FavouriteActivity.class));

        } else if (id == R.id.logout) {
            mAuth.signOut();
                startActivity(new Intent(BaseActivity.this, LoginActivity.class));

        } else if (id == R.id.search_drink) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     *
     * @param id
     * @param title pass in the textview for title
     * @param img pass in the image for the cocktail
     */
    //Get the details of the cocktail that was clicked on
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
                    PreferenceHelper.putPref("Desc", desc, getApplicationContext());

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

    public void setNavHeaderText(){

    }

}
