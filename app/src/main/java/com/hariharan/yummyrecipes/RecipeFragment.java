package com.hariharan.yummyrecipes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hariharan.yummyrecipes.model.RecipeModel;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class RecipeFragment extends Fragment {

    SwipePlaceHolderView swipePlaceHolderView;

    RecipeViewModel viewModel;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recipe, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        swipePlaceHolderView = view.findViewById(R.id.recipe_view);
        viewModel = new ViewModelProvider(getActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RecipeViewModel.class);
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
                swipePlaceHolderView.addView(new RecipeCard(recipeModel, RecipeFragment.this.getActivity().getApplicationContext(), swipePlaceHolderView, new OnRecipeSelectedImpl()));
            }
        });
    }

    private class OnRecipeSelectedImpl implements RecipeCard.OnRecipeSelected {

        @Override
        public void onAddedToCart(RecipeModel recipeModel) {
            viewModel.addRecipe(recipeModel);
        }

        @Override
        public void onMovedToLast(RecipeModel recipeModel) {
            swipePlaceHolderView.addView(new RecipeCard(recipeModel, RecipeFragment.this.getActivity().getApplicationContext(), swipePlaceHolderView, new OnRecipeSelectedImpl()));
        }
    }
}