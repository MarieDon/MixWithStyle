package tester.ie.app.mixwithstyle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tester.ie.app.mixwithstyle.adapters.CocktailAdapter;
import tester.ie.app.mixwithstyle.adapters.FavouritesAdapter;
import tester.ie.app.mixwithstyle.model.Cocktail;
import tester.ie.app.mixwithstyle.model.FavouriteCocktails;
import tester.ie.app.mixwithstyle.utils.Constants;
import tester.ie.app.mixwithstyle.utils.IngredientsList;

import static tester.ie.app.mixwithstyle.MainActivity.INGREDIENTS;

public class FavouriteActivity extends BaseActivity implements FavouritesAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private List<FavouriteCocktails> favouriteslList;
    private FavouritesAdapter favouritesAdapter;
    private Button saveFavouritesBtn;
    private FirebaseDatabase database;
    private DatabaseReference favourites;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_favourite, null, false);
        drawer.addView(view, 0);
        recyclerView = findViewById(R.id.favRecycler);
        //saveFavouritesBtn = findViewById(R.id.saveToFavBtn);
        database = FirebaseDatabase.getInstance();
        favourites = database.getReference();
        favouriteslList = new ArrayList<>();
        getFavouritesCocktailList();

    }

    public void getFavouritesCocktailList() {
        database.getReference("Favourites Cocktails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                  favouriteslList.clear();
                  for(DataSnapshot myfaves : dataSnapshot.getChildren()){
                      FavouriteCocktails favecocktails = myfaves.getValue(FavouriteCocktails.class);
                      favecocktails.setId(myfaves.getKey());
                      favouriteslList.add(favecocktails);
                      recyclerView.setLayoutManager(new GridLayoutManager(FavouriteActivity.this, 2));
                      favouritesAdapter = new FavouritesAdapter(favouriteslList, FavouriteActivity.this);
                      favouritesAdapter.setClickListener(FavouriteActivity.this);
                      recyclerView.setAdapter(favouritesAdapter);
                      favouritesAdapter.notifyDataSetChanged();
                  }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view, int position) {
        DatabaseReference deleteItem = FirebaseDatabase.getInstance().getReference("Favourites Cocktails").child(favouriteslList.get(position).getId());
        deleteItem.removeValue();
        favouritesAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Deleted item with the key of " + favouriteslList.get(position).getId() + " Successfully", Toast.LENGTH_SHORT).show();
    }
}

