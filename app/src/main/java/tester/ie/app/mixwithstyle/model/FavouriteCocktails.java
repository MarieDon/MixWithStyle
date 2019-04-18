package tester.ie.app.mixwithstyle.model;

import java.util.ArrayList;

public class FavouriteCocktails {
    public String id;
    public String image;
    public String title;
    public String description;
    public float rating;
    public String glassType;
    public boolean isAlcoholic;

    public FavouriteCocktails() {
    }

    public FavouriteCocktails(String image, String title, String description, float rating) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public FavouriteCocktails(String image, String title, String description, float rating,
                              String glassType, boolean isAlcoholic) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.glassType = glassType;
        this.isAlcoholic = isAlcoholic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getGlassType() { return glassType; }

    public void setGlassType(String glassType) { this.glassType = glassType; }

    public boolean isAlcoholic() { return isAlcoholic; }

    public void setAlcoholic(boolean alcoholic) { isAlcoholic = alcoholic; }

    @Override
    public String toString() {
        return "FavouriteCocktails{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", glassType='" + glassType + '\'' +
                ", isAlcoholic=" + isAlcoholic +
                '}';
    }
}
