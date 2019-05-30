package com.example.pristinepastries.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class COD extends AppCompatActivity {

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod);
        TextView name = findViewById(R.id.ro_name);
        TextView size_name = findViewById(R.id.ro_size);
        TextView size_price = findViewById(R.id.ro_price);
        TextView amount = findViewById(R.id.ro_items);
        TextView total = findViewById(R.id.ro_total);
        TextView pm = findViewById(R.id.ro_payment_method);
        Button confirm = findViewById(R.id.button5);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Checking out");

        String totalPrice = String.valueOf(GlobalVariables.selectedAmount * Double.parseDouble(GlobalVariables.selectedSize.price));

        name.setText(GlobalVariables.selectedCake.name);
        size_name.setText(GlobalVariables.selectedSize.label);
        size_price.setText(GlobalVariables.selectedSize.price);
        amount.setText("" + GlobalVariables.selectedAmount);
        pm.setText("" + GlobalVariables.selectedPaymentMethod);
        total.setText(totalPrice);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });

    }

    public void checkout(){
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalVariables.CREATE_COD_ORDER_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(COD.this, "Success", Toast.LENGTH_SHORT).show();

                    pd.dismiss();
                    startActivity(new Intent(COD.this, OrderListActivity.class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(COD.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("customer_id", GlobalVariables.currentUser.id);
                params.put("payment_method", GlobalVariables.selectedPaymentMethod);
                params.put("size_id", GlobalVariables.selectedSize.id);
                params.put("items", String.valueOf(GlobalVariables.selectedAmount));
                params.put("delivery_location", GlobalVariables.delivery_location);
                return  params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.orders_item:
                startActivity(new Intent(this, OrderListActivity.class));
                return true;
            case R.id.profile_item:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;

        }


        return super.onOptionsItemSelected(item);

    }
}
