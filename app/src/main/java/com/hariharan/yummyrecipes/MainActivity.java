package com.hariharan.yummyrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hariharan.yummyrecipes.model.RecipeModel;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class MainActivity extends AppCompatActivity {

    SwipePlaceHolderView swipePlaceHolderView;

    RecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipePlaceHolderView = findViewById(R.id.recipe_view);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RecipeViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipePlaceHolderView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_message_view_right)
                        .setSwipeOutMsgLayoutId(R.layout.simple_message_view_left));

        viewModel.getRecipeList().observe(this, recipeModels -> {
            swipePlaceHolderView.removeAllViews();
            for (RecipeModel recipeModel : recipeModels) {
                swipePlaceHolderView.addView(new RecipeCard(recipeModel, MainActivity.this.getApplicationContext(), swipePlaceHolderView, new OnRecipeSelectedImpl()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private class OnRecipeSelectedImpl implements RecipeCard.OnRecipeSelected {

        @Override
        public void onAddedToCart(RecipeModel recipeName) {
            viewModel.addRecipe(recipeName);
        }

        @Override
        public void onMovedToLast(RecipeModel recipeModel) {
            swipePlaceHolderView.addView(new RecipeCard(recipeModel, MainActivity.this.getApplicationContext(), swipePlaceHolderView, new OnRecipeSelectedImpl()));
        }
    }
}