package com.example.pristinepastries.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.example.pristinepastries.engine.MySingleton;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

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

    EditText control;
    EditText sender;


    TextView noteLabel;
    Button checkout;
    Button updateControl;

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
        noteLabel = findViewById(R.id.textView20);
        checkout = findViewById(R.id.button10);
        name.setText(GlobalVariables.selectedOrder.product_name);
        size.setText("Size: " + GlobalVariables.selectedOrder.size_label);
        size_price.setText("Item price: " + GlobalVariables.selectedOrder.size_price);
        no_of_items.setText("No of Items" + GlobalVariables.selectedOrder.no_of_items);
        total_price.setText(String.valueOf(sPrice));
        status.setText("Status: " + GlobalVariables.selectedOrder.status);
        note.setText(GlobalVariables.selectedOrder.note.equalsIgnoreCase("null") ? "" : GlobalVariables.selectedOrder.note );
        payment_method.setText("Payment Method: " + GlobalVariables.selectedOrder.payment_method);

        updateControl = findViewById(R.id.button4);
        sender = findViewById(R.id.editText11);
        control = findViewById(R.id.editText10);

        updateControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateControl();
            }
        });

        if (GlobalVariables.selectedOrder.cart.equalsIgnoreCase("true")){
            status.setVisibility(GONE);
            note.setVisibility(GONE);
            payment_method.setVisibility(GONE);
            noteLabel.setVisibility(GONE);
        }else{
            checkout.setVisibility(GONE);
        }

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutCart();
            }
        });

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

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderInformationActivity.this, ImageZoomActivity.class));
            }
        });
    }

    public void checkOutCart(){
        GlobalVariables.isCart = true;
        startActivity(new Intent(this, PaymentMethods.class));

    }

    public void updateControl(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalVariables.UPDATE_CONTROL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OrderInformationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderInformationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", GlobalVariables.selectedOrder.id);
                params.put("control", control.getText().toString());
                params.put("sender", sender.getText().toString());

                return params;

            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        
    }
}
