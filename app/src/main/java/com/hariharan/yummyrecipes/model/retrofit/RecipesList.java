package com.hariharan.yummyrecipes.model.retrofit;

import com.hariharan.yummyrecipes.model.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit interface for API call.
 */
public interface RecipesList {

    @GET("/he-public-data/reciped9d7b8c.json")
    Call<List<RecipeModel>> getRecipesList();
}
