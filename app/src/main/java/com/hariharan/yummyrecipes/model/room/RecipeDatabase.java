package com.hariharan.yummyrecipes.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hariharan.yummyrecipes.model.RecipeModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Data base to store recipes.
 */
@Database(entities = {RecipeModel.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    public abstract RecipeDao getRecipeDao();

    private static volatile RecipeDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RecipeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, "recipe_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
