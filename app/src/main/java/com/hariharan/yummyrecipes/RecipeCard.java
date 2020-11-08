package com.hariharan.yummyrecipes;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hariharan.yummyrecipes.model.RecipeModel;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.recipe_card)
public class RecipeCard {

    @View(R.id.recipeImageView)
    private ImageView recipeImage;

    @View(R.id.recipe_name)
    private TextView recipeName;

    @View(R.id.recipe_description)
    private TextView recipeDescription;

    @View(R.id.recipe_cost)
    private TextView recipeCost;

    private RecipeModel recipeModel;

    private Context context;

    private SwipePlaceHolderView swipePlaceHolderView;

    private OnRecipeSelected onRecipeSelected;

    public RecipeCard(RecipeModel recipeModel, Context context, SwipePlaceHolderView swipePlaceHolderView, OnRecipeSelected onRecipeSelected) {
        this.recipeModel = recipeModel;
        this.context = context;
        this.swipePlaceHolderView = swipePlaceHolderView;
        this.onRecipeSelected = onRecipeSelected;
    }

    @Resolve
    private void onResolved(){
        Glide.with(context).load(recipeModel.getImageUrl()).into(recipeImage);
        recipeName.setText(recipeModel.getName());
        recipeCost.setText("Rs."+ recipeModel.getPrice());
        recipeDescription.setText(recipeModel.getDescription());
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        swipePlaceHolderView.addView(this);
        onRecipeSelected.onMovedToLast(recipeModel);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        onRecipeSelected.onAddedToCart(recipeModel);
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }

    interface OnRecipeSelected{

        void onAddedToCart(RecipeModel recipe);

        void onMovedToLast(RecipeModel recipe);
    }
}
