package com.example.pristinepastries.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.example.pristinepastries.engine.MySingleton;
import com.example.pristinepastries.models.Order;
import com.example.pristinepastries.templates.CakeAdapter;
import com.example.pristinepastries.templates.OrderTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private static final String TAG = "OrderListActivity";

    RecyclerView recyclerView;
    List<Order> orderList = new ArrayList<>();
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = findViewById(R.id.order_list_recycler_view);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        loadOrders();
    }

    public void loadOrders(){
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                GlobalVariables.GET_ORDER_URL + "?id=" + GlobalVariables.currentUser.id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        orderList = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                orderList.add(new Order(
                                        jsonArray.getJSONObject(i).getString("id"),
                                        jsonArray.getJSONObject(i).getString("ordered_at"),
                                        jsonArray.getJSONObject(i).getString("customer_id"),
                                        jsonArray.getJSONObject(i).getString("product_id"),
                                        jsonArray.getJSONObject(i).getString("size_id"),
                                        jsonArray.getJSONObject(i).getString("no_of_items"),
                                        jsonArray.getJSONObject(i).getString("payment_method"),
                                        jsonArray.getJSONObject(i).getString("status"),
                                        jsonArray.getJSONObject(i).getString("delivery_location"),
                                        jsonArray.getJSONObject(i).getString("delivered_at"),
                                        jsonArray.getJSONObject(i).getString("created_at"),
                                        jsonArray.getJSONObject(i).getString("updated_at"),
                                        jsonArray.getJSONObject(i).getString("note"),
                                        jsonArray.getJSONObject(i).getString("product_name"),
                                        jsonArray.getJSONObject(i).getString("size_label"),
                                        jsonArray.getJSONObject(i).getString("size_price"),
                                        jsonArray.getJSONObject(i).getString("image"),
                                        jsonArray.getJSONObject(i).getString("cart")
                                ));

                            }
                            loadRecyclerView();
                            pd.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void loadRecyclerView(){
        Log.d(TAG, "initializing recyclerview adapter");

        OrderTemplate orderTemplate = new OrderTemplate(orderList, this);
        recyclerView.setAdapter(orderTemplate);
        Log.d(TAG, "adapter initialized ");

        Log.d(TAG, "Displaying Data");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.mdivider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
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
