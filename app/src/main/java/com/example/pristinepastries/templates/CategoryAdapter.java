package com.example.pristinepastries.templates;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristinepastries.Activities.CakeListActivity;
import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.example.pristinepastries.models.Categories;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends
        RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{


    private static final String TAG = "SportEventAdapter";
    private List<Categories> categories;
    private Context context;

    public CategoryAdapter(List<Categories> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_list,
                viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Categories category = categories.get(i);
        myViewHolder.name.setText(category.name);
        myViewHolder.description.setText(category.description);
        try{
            Picasso.get()
                    .load(category.image)
                    .resize(80, 80)
                    .centerCrop()
                    .into(myViewHolder.image);
        }catch (Exception e){
            Picasso.get()
                    .load("https://cake-reservation.herokuapp.com/images/image_not_found.png")
                    .resize(80, 80)
                    .centerCrop()
                    .into(myViewHolder.image);
        }
        myViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedCategory = GlobalVariables.categoriesList.get(i);
                context.startActivity(new Intent(context, CakeListActivity.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView description;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView2);
            name = itemView.findViewById(R.id.category_list_name);
            description = itemView.findViewById(R.id.category_list_description);
            constraintLayout = itemView.findViewById(R.id.category_container);
        }
    }

}
