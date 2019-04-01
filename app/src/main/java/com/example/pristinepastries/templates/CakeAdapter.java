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
import com.example.pristinepastries.Activities.ProductInfoActivity;
import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.example.pristinepastries.models.Cake;
import com.example.pristinepastries.models.Categories;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CakeAdapter extends
        RecyclerView.Adapter<CakeAdapter.MyViewHolder> {

    private static final String TAG = "CakeAdapter";
    private List<Cake> cakeList;
    private Context context;

    public CakeAdapter(List<Cake> cakeList, Context context) {
        this.cakeList = cakeList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cake_list, viewGroup,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Cake cake = cakeList.get(i);
        myViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add on click listener
                GlobalVariables.selectedCake = GlobalVariables.cakeList.get(i);
                context.startActivity(new Intent(context, ProductInfoActivity.class));
            }
        });

        try{
            Picasso.get()
                    .load(cake.image)
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

        myViewHolder.name.setText(cake.name);
        myViewHolder.description.setText(cake.description);

    }

    @Override
    public int getItemCount() {
        return cakeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        ImageView image;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cake_list_name);
            description = itemView.findViewById(R.id.cake_list_description);
            image = itemView.findViewById(R.id.imageView3);
            constraintLayout = itemView.findViewById(R.id.cake_container);
        }
    }
}