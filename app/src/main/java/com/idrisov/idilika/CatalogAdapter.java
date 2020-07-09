package com.idrisov.idilika;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    Context context;
    ArrayList<Item> arrayList;

    public CatalogAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new CatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {
        Item item = arrayList.get(position);

        holder.title.setText(item.getTitle());
        holder.text.setText(item.getBrand());
        holder.price.setText(String.valueOf(item.getPrice()));

        Picasso.with(context)
                .load(item.getImageLink())
                .into(holder.imageView);

        holder.favorites.setChecked(load(String.valueOf(position)));
        holder.favorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                save(String.valueOf(position), true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class CatalogViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CheckBox favorites;
        TextView title, text, price;

        public CatalogViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);
            title = itemView.findViewById(R.id.titleItem);
            text = itemView.findViewById(R.id.textItem);
            price = itemView.findViewById(R.id.priceItem);
            favorites = itemView.findViewById(R.id.checkbox_favorite_item);
        }

    }

    public void save(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean load(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Values", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
}
