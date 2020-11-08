package com.hariharan.yummyrecipes.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hariharan.yummyrecipes.model.RecipeModel;

import java.util.List;

/**
 * DAO class for recipe table.
 */
@Dao
public interface RecipeDao {

    /**
     * Method to insert the recipes to database.
     *
     * @param recipe recipe to be inserted into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeModel recipe);

    /**
     * Method to get recipes list from data base
     *
     * @return list of recipes
     */
    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    LiveData<List<RecipeModel>> getRecipeList();
}
