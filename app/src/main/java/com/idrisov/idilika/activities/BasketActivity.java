package com.idrisov.idilika.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.idrisov.idilika.R;
import com.idrisov.idilika.fragments.EmptyFavoritesFragment;
import com.idrisov.idilika.fragments.FavoritesFragments;

public class BasketActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnStartShopping;
    private Fragment emptyFragment, favoritesFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        toolbar = findViewById(R.id.toolbar_basket);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        showEmptyFragment();

        btnStartShopping = findViewById(R.id.start_shopping);
        btnStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BasketActivity.this, "Такого функционала пока нет", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        //Возврат на предыдущий экран
        {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount > 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //Показ пустого фрагмента
    public void showEmptyFragment(){
        emptyFragment = new EmptyFavoritesFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment, emptyFragment);
        fragmentTransaction.commit();
    }

    //Показ фрагмента с сохранеными
    public void showFavoritesFragment(){
        favoritesFragment = new FavoritesFragments();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, favoritesFragment);
        fragmentTransaction.commit();
    }
}