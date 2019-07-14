package com.example.pristinepastries.templates;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristinepastries.Activities.OrderInformationActivity;
import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.example.pristinepastries.models.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderTemplate extends RecyclerView.Adapter<OrderTemplate.MyViewHolder> {

    List<Order> orderList;
    Context context;

    public OrderTemplate(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_orders,
                viewGroup, false);

        return new OrderTemplate.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Order order = orderList.get(i);
        double price = Double.parseDouble(order.size_price) * Double.parseDouble(order.no_of_items);
        myViewHolder.name.setText(order.product_name);
        myViewHolder.amount.setText("Amount: " + order.no_of_items);
        myViewHolder.status.setText("Status: " + order.status);
        myViewHolder.price.setText(String.valueOf(price));

        try{
            Picasso.get()
                    .load(order.image)
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

        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedOrder = order;
                context.startActivity(new Intent(context, OrderInformationActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView amount;
        TextView status;
        TextView price;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ol_image);
            name = itemView.findViewById(R.id.ol_name);
            amount = itemView.findViewById(R.id.ol_items);
            status = itemView.findViewById(R.id.ol_status);
            price = itemView.findViewById(R.id.ol_price);
            container = itemView.findViewById(R.id.ol_container);

        }
    }
}
