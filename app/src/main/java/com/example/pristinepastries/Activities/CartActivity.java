package com.example.pristinepastries.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.example.pristinepastries.engine.MySingleton;
import com.example.pristinepastries.models.Order;
import com.example.pristinepastries.templates.OrderTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";
    RecyclerView recyclerView;
    List<Order> orderList = new ArrayList<>();
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");
        recyclerView = findViewById(R.id.recycler_view_cart);
        pd = new ProgressDialog(this);
        pd.setTitle("Loading cart...");
        pd.setCancelable(false);
        loadOrders();
    }

    public void loadOrders(){
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                GlobalVariables.GET_CART_URL + "?id=" + GlobalVariables.currentUser.id,
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
}
