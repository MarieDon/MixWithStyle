# MIX WITH STYLE
Student name: Marie Donoval (SSD3)
Student number: 20075884

Mix With Style is an app that will give you the
ingredients and the steps to prepare the perfect cocktail
of your choice
    Some of the apps features include login and register, if users would like to use the app then they need
    to register there details and once registerd they will be automatically logged in
Another feature is that users have the ability to save their favourite cocktails to a list on the app
which they can rate from 1-5 stars depending how much they liked it, the stars are then sorted by a rating of highest to lowest stars.
    From their favourites then can delete some cocktails that they do not like anymore

Along with this this app also give users the ingredients and the method to make the cocktail, all this information comes from the [API](https://www.thecocktaildb.com/api.php)
Another cool feature of the app, is that you can search the cocktails by their ingredients.
    this give you a list of cocktails back that has the ingredient that you typed in the search bar.

### Search Function
````java
 searchBtn.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(final View view) {
    if (!search.getText().toString().equals("")) {
    mySearch = "i=" + search.getText().toString();
    recyclerView.setLayoutManager(new LinearLayoutManager(CocktailRecyclerView.this));
    cocktailList = new ArrayList<>();
    cocktailList = getCocktailList(mySearch);
    cocktailAdapter = new CocktailAdapter(cocktailList, CocktailRecyclerView.this);
    recyclerView.setAdapter(cocktailAdapter);
    cocktailAdapter.notifyDataSetChanged();

    }
    }
    });
````
    
### Hiding my ingredients and methods button hack
    ````java
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

    ````












## DEMO
[Youtube Demo](https://youtu.be/9cAR3IYOUOc)

## REFERENCES

[Firebase](https://console.firebase.google.com/u/0/project/mixwithstyle-ea35d/database/mixwithstyle-ea35d/data)

[Android Docs](https://developer.android.com/docs)

[Stackoverflow](https://stackoverflow.com/)

[Recycler View Click](https://www.youtube.com/watch?v=ZXoGG2XTjzU)

[Cocktails API](https://www.thecocktaildb.com/api.php)

[Firebase Sorting](https://stackoverflow.com/questions/40198291/how-to-sort-by-children-key-value-in-firebase)

[Canva Logo Maker] ()

[Navigation Drawer] (https://code.tutsplus.com/tutorials/how-to-code-a-navigation-drawer-in-an-android-app--cms-30263)
