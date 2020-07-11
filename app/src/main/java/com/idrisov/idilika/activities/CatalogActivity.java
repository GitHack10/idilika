package com.idrisov.idilika.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idrisov.idilika.CatalogAdapter;
import com.idrisov.idilika.Item;
import com.idrisov.idilika.NetworkRequest;
import com.idrisov.idilika.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private CatalogAdapter catalogAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        toolbar = findViewById(R.id.toolbar_catalog);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        getData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        //Возврат на предыдущий экран
        {
            finish();
        }
        //Переход в активити корзину
        if (id == R.id.menu_basket) {
            startActivity(new Intent(CatalogActivity.this, BasketActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //Получение данных с сервера
    public void getData() {
        NetworkRequest.getRequest()
                .getTestApi()
                .getTestModel()
                .enqueue(new Callback<ArrayList<Item>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                        ArrayList<Item> item = response.body();
                        catalogAdapter = new CatalogAdapter(CatalogActivity.this, item);
                        catalogAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(catalogAdapter);
                        //recyclerView.setItemViewCacheSize(item.size());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                        Toast.makeText(CatalogActivity.this, "Безуспешно", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}