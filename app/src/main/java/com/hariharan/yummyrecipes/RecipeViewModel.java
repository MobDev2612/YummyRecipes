package com.hariharan.yummyrecipes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hariharan.yummyrecipes.model.RecipeModel;
import com.hariharan.yummyrecipes.model.RecipeRepo;

import java.util.List;

/**
 * View model class to handle life cycle events.
 */
public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepo recipeRepo;

    private LiveData<List<RecipeModel>> recipeList;

    private List<RecipeModel> selectedRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeRepo = new RecipeRepo(application);
        recipeList = recipeRepo.getRecipeList();
    }

    public LiveData<List<RecipeModel>> getRecipeList() {
        return recipeList;
    }

    public void addRecipe(RecipeModel recipeName) {
        selectedRecipes.add(recipeName);
    }
}
