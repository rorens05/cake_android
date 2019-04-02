package com.example.pristinepastries.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.squareup.picasso.Picasso;

public class OrderInformationActivity extends AppCompatActivity {

    TextView name;
    TextView size;
    TextView size_price;
    TextView no_of_items;
    TextView total_price;
    TextView status;
    TextView note;
    TextView payment_method;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);

        double sPrice = Double.parseDouble(GlobalVariables.selectedOrder.size_price) * Double.parseDouble(GlobalVariables.selectedOrder.no_of_items);
        name = findViewById(R.id.textView5);
        size = findViewById(R.id.textView11);
        size_price = findViewById(R.id.textView12);
        no_of_items = findViewById(R.id.textView13);
        total_price = findViewById(R.id.textView16);
        status = findViewById(R.id.textView19);
        note = findViewById(R.id.textView21);
        payment_method = findViewById(R.id.textView22);
        image = findViewById(R.id.imageView5);

        name.setText(GlobalVariables.selectedOrder.product_name);
        size.setText("Size: " + GlobalVariables.selectedOrder.size_label);
        size_price.setText("Item price: " + GlobalVariables.selectedOrder.size_price);
        no_of_items.setText("No of Items" + GlobalVariables.selectedOrder.no_of_items);
        total_price.setText(String.valueOf(sPrice));
        status.setText("Status: " + GlobalVariables.selectedOrder.status);
        note.setText(GlobalVariables.selectedOrder.note.equalsIgnoreCase("null") ? "" : GlobalVariables.selectedOrder.note );
        payment_method.setText("Payment Method: " + GlobalVariables.selectedOrder.payment_method);


        try{
            Picasso.get()
                    .load(GlobalVariables.selectedOrder.image)
                    .resize(80, 80)
                    .centerCrop()
                    .into(image);
        }catch (Exception e){
            Picasso.get()
                    .load("https://cake-reservation.herokuapp.com/images/image_not_found.png")
                    .resize(80, 80)
                    .centerCrop()
                    .into(image);
        }

    }
}
