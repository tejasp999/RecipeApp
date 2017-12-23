/*Assignment : Inclass08
Yash Ghia
Prabhakar Teja Seeda*/


package com.example.teja.inclass08;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by teja on 10/30/17.
 */

public class RecipeResults {
    String title,href,ingredients,thumbnail;

    static public RecipeResults createResults(JSONObject jsonObject) throws JSONException {
        RecipeResults recipeResults = new RecipeResults();
        recipeResults.setTitle(jsonObject.getString("title"));
        recipeResults.setHref(jsonObject.getString("href"));
        recipeResults.setIngredients(jsonObject.getString("ingredients"));
        recipeResults.setThumbnail(jsonObject.getString("thumbnail"));
        return recipeResults;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {

        return title;
    }

    public String getHref() {
        return href;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
