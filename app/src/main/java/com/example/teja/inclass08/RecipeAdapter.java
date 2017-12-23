/*Assignment : Inclass08
Yash Ghia
Prabhakar Teja Seeda*/


package com.example.teja.inclass08;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by teja on 10/30/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    ArrayList<RecipeResults> recipeResults;
    Context context;
    public RecipeAdapter(ArrayList<RecipeResults> recipes, Context context) {
        this.recipeResults = recipes;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecipeResults recipe = recipeResults.get(position);
        holder.recipeAdapter = recipe;
        holder.titleTextView.setText("Title:" + recipe.getTitle());
        holder.ingredientsTextView.setText(recipe.getIngredients());
        holder.urlTextView.setText(recipe.getHref());
        if (!recipe.getThumbnail().equals("")){
            Picasso.with(context).load(recipe.getThumbnail()).placeholder(R.drawable.progress_animation)
                    .error(R.drawable.no_image).into(holder.recipeImageView);
        }else {
            holder.recipeImageView.setImageResource(R.drawable.no_image);
        }
    }

    @Override
    public int getItemCount() {
        return recipeResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, ingredientsTextView,urlTextView;
        ImageView recipeImageView;

        RecipeResults recipeAdapter;

        public ViewHolder(final View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            ingredientsTextView = (TextView) itemView.findViewById(R.id.ingredients);
            urlTextView = (TextView) itemView.findViewById(R.id.implicitURLView);
            urlTextView.setClickable(true);
            recipeImageView = (ImageView) itemView.findViewById(R.id.recipeImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
