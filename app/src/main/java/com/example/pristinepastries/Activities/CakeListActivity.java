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
import com.example.pristinepastries.models.Cake;
import com.example.pristinepastries.models.Categories;
import com.example.pristinepastries.templates.CakeAdapter;
import com.example.pristinepastries.templates.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CakeListActivity extends AppCompatActivity {

    private static final String TAG = "CakeListActivity";
    RecyclerView recyclerView;
    CakeAdapter cakeAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_list);

        Toast.makeText(this, GlobalVariables.selectedCategory.name, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recycle_view2);

        loadCakes();
    }

    public void loadCakes(){
        GlobalVariables.cakeList = new ArrayList<>();
        pd = new ProgressDialog(CakeListActivity.this);
        pd.setMessage("Loading Cakes");
        pd.show();

        String url = GlobalVariables.PRODUCT_URL + "?id=" + GlobalVariables.selectedCategory.id;

        Log.d(TAG, "loading categories: " + url);
        StringRequest getCategories = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "response received");

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(CakeListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONArray cake = jsonObject.getJSONArray("categories");

                    for (int i = 0; i < cake.length(); i++) {
                        GlobalVariables.cakeList.add(
                                new Cake(
                                        cake.getJSONObject(i).getString("id"),
                                        cake.getJSONObject(i).getString("name"),
                                        cake.getJSONObject(i).getString("description"),
                                        cake.getJSONObject(i).getString("category_id"),
                                        cake.getJSONObject(i).getString("stock"),
                                        cake.getJSONObject(i).getString("image")
                                        )
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error loading: " + e.getMessage());

                }

                loadRecyclerView();
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "no response received");
                Toast.makeText(CakeListActivity.this, "Cannot connect to the server",
                        Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(getCategories);

    }

    public void loadRecyclerView(){
        Log.d(TAG, "initializing recyclerview adapter");
//        GlobalVariables.cakeList.add(
//                new Cake("1", "name", "desc ", "1", "1", "")
//        );
        cakeAdapter = new CakeAdapter(GlobalVariables.cakeList, this);
        recyclerView.setAdapter(cakeAdapter);
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
