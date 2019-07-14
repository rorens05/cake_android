package com.example.pristinepastries.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class PaymentMethods extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        Button cod = findViewById(R.id.button2);
        ImageButton pal = findViewById(R.id.pal);
        ImageButton ml = findViewById(R.id.ml);
        ImageButton ceb = findViewById(R.id.ceb);
        ImageButton wes = findViewById(R.id.wes);
        ImageButton mg = findViewById(R.id.mg);
        ImageButton lbc = findViewById(R.id.lbc);

        pal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "PALAWAN EXPRESS";
                if(GlobalVariables.isCart){
                    checkout();
                    finish();
                    return;
                }
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "M-LHUILLIER";
                if(GlobalVariables.isCart){
                    checkout();
                    finish();
                    return;
                }
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        ceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "Cebuana Lhuillier";
                if(GlobalVariables.isCart){
                    checkout();
                    finish();
                    return;
                }
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        wes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "Western Union";
                if(GlobalVariables.isCart){
                    checkout();
                    finish();
                    return;
                }
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        mg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "MoneyGram";
                if(GlobalVariables.isCart){
                    checkout();
                    finish();
                    return;
                }
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        lbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "LBC";
                if(GlobalVariables.isCart){
                    checkout();
                    finish();
                    return;
                }
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "COD";
                if(GlobalVariables.isCart){
                    checkout();
                    finish();
                    return;
                }
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });
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

            case R.id.custom_cake:
                startActivity(new Intent(this, CustomCakeActivity.class));
                return true;
            case R.id.cart_item:
                startActivity(new Intent(this, CartActivity.class));
                return true;
            case R.id.orders_item:
                startActivity(new Intent(this, OrderListActivity.class));
                return true;
            case R.id.profile_item:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.logout_item:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to logout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;

        }


        return super.onOptionsItemSelected(item);

    }

    private void checkout(){
        GlobalVariables.isCart = false;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalVariables.CHECKOUT_CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PaymentMethods.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentMethods.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", GlobalVariables.selectedOrder.id);
                params.put("payment_method", GlobalVariables.selectedPaymentMethod);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

        }
}
