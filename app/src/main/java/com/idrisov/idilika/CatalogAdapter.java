package com.idrisov.idilika;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    Context context;
    ArrayList<Item> arrayList;
    SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();

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

        if (!sparseBooleanArray.get(position, false)) {
            holder.favorites.setChecked(false);
        } else
            holder.favorites.setChecked(true);

        holder.favorites.setChecked(loadSP(String.valueOf(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //Сохраняет флажок в SharedPreferences
    public void saveSP(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value).apply();
    }

    //Загружает нажатый флажок из SharedPreferences
    public boolean loadSP(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Values", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    //Удаляет нажатый флажок из SharedPreferences
    public void deleteSp(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key).apply();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {
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

            favorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (!sparseBooleanArray.get(position, false)) {
                        favorites.setChecked(true);
                        sparseBooleanArray.put(position, true);
                        saveSP(String.valueOf(position), true);
                    } else {
                        favorites.setChecked(false);
                        sparseBooleanArray.put(position, false);
                        deleteSp(String.valueOf(position));
                    }
                }
            });
        }

    }
}
