package com.example.pristinepastries.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.pristinepastries.models.Sizes;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoActivity extends AppCompatActivity {

    TextView name;
    ImageView image;
    TextView description;
    TextView category;
    Spinner sizes;
    EditText amount;
    Button checkout;
    Button cart;
    List<Sizes> sizeList = new ArrayList<>();
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading Sizes");
        pd.setCancelable(false);

        name = findViewById(R.id.info_name);
        description = findViewById(R.id.info_desc2);
        category = findViewById(R.id.info_stock2);
        sizes = findViewById(R.id.spinner);
        amount = findViewById(R.id.editText);
        checkout = findViewById(R.id.button3);
        cart = findViewById(R.id.button9);
        image = findViewById(R.id.imageView4);

        name.setText(GlobalVariables.selectedCake.name);
        description.setText(GlobalVariables.selectedCake.description);
        category.setText(GlobalVariables.selectedCake.name);

        try{
            Picasso.get()
                    .load(GlobalVariables.selectedCake.image)
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

        loadSizes();
    }

    public void loadSizes(){
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                GlobalVariables.SIZE_URL + "?id=" + GlobalVariables.selectedCake.id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    sizeList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        sizeList.add(new Sizes(
                                jsonArray.getJSONObject(i).getString("id"),
                                jsonArray.getJSONObject(i).getString("label"),
                                jsonArray.getJSONObject(i).getString("price"),
                                jsonArray.getJSONObject(i).getString("product_id"),
                                jsonArray.getJSONObject(i).getString("created_at"),
                                jsonArray.getJSONObject(i).getString("updated_at")
                        ));
                    }
                    displaySizes();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkout();
                    }
                });
                cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart();
                    }
                });
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ProductInfoActivity.this, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void displaySizes(){
        ArrayList<String> val = new ArrayList<>();
        for (int i = 0; i < sizeList.size(); i++) {
            val.add(sizeList.get(i).label + ": " + sizeList.get(i).price);
        }

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                val);
        sizes.setAdapter(spinnerArrayAdapter);
    }

    public void checkout(){
        GlobalVariables.selectedSize = sizeList.get(sizes.getSelectedItemPosition());
        GlobalVariables.selectedAmount = Integer.parseInt(amount.getText().toString());
        startActivity(new Intent(this, PaymentMethods.class));
    }

    public void addToCart(){
        GlobalVariables.selectedSize = sizeList.get(sizes.getSelectedItemPosition());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalVariables.ADD_TO_CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ProductInfoActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductInfoActivity.this, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("customer_id", GlobalVariables.currentUser.id);
                params.put("product_id", GlobalVariables.selectedCake.id);
                params.put("size_id", GlobalVariables.selectedSize.id);
                params.put("no_of_items", amount.getText().toString());
                return params;
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
