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

    boolean showingCart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new RecipeFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!showingCart) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new RecipeFragment()).commit();
            showingCart = false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) {
            showingCart = true;
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new CartFragment()).commit();
        }
        return super.onOptionsItemSelected(item);
    }


}