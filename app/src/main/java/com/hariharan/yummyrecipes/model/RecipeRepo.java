package com.hariharan.yummyrecipes.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.hariharan.yummyrecipes.model.retrofit.RecipesList;
import com.hariharan.yummyrecipes.model.room.RecipeDao;
import com.hariharan.yummyrecipes.model.room.RecipeDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Repository to provide recipes list to view. This class will handle data base and web api calls.
 */
public class RecipeRepo {

    private RecipeDao recipeDao;

    private LiveData<List<RecipeModel>> recipeList;

    public RecipeRepo(Application application) {
        RecipeDatabase database = RecipeDatabase.getDatabase(application);
        recipeDao = database.getRecipeDao();
        getRecipeListFromWeb();
        recipeList = recipeDao.getRecipeList();
    }

    public LiveData<List<RecipeModel>> getRecipeList() {
        return recipeList;
    }

    /**
     * Method to insert data to database.
     *
     * @param recipeList list of recipes
     */
    private void insertRecipeListIntoDataBase(List<RecipeModel> recipeList) {
        RecipeDatabase.databaseWriteExecutor.execute(() -> {
            for (RecipeModel recipe : recipeList) {
                recipeDao.insert(recipe);
            }
        });
    }

    /**
     * Method to fetch recipes from web.
     */
    private void getRecipeListFromWeb() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://s3-ap-southeast-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipesList service = retrofit.create(RecipesList.class);

        Call<List<RecipeModel>> jsonCall = service.getRecipesList();
        jsonCall.enqueue(new Callback<List<RecipeModel>>() {

            @Override
            public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {
                if (response.isSuccessful()) {
                    insertRecipeListIntoDataBase(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
            }
        });
    }
}
