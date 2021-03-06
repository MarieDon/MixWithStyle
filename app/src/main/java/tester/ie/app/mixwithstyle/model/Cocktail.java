package tester.ie.app.mixwithstyle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tester.ie.app.mixwithstyle.fragments.IngredientsFragment;

/**
 * Created by marie on 07/02/2019.
 */

public class Cocktail implements Serializable {
    public String image;
    public String title;
    public String description;
    public float rating;
    public String drinkID;



    public Cocktail() {
    }

    public Cocktail(String image, String title, String description, float rating) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getDrinksRating() {
        return rating;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDrinksRating(float rating) {
        this.rating = rating;
    }

    public void setDrinkID(String drinkID) { this.drinkID = drinkID; }

    public String getDrinkID() { return drinkID; }

}
