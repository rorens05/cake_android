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

import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;

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
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "M-LHUILLIER";
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        ceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "Cebuana Lhuillier";
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        wes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "Western Union";
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        mg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "MoneyGram";
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        lbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "LBC";
                startActivity(new Intent(PaymentMethods.this, COD.class));
            }
        });

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedPaymentMethod = "COD";
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
}
