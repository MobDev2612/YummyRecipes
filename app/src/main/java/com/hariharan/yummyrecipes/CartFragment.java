package com.hariharan.yummyrecipes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CartFragment extends Fragment {

    RecipeViewModel viewModel;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel = new ViewModelProvider(getActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RecipeViewModel.class);

    }
}