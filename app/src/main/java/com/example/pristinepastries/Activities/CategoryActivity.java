package com.example.pristinepastries.Activities;

import android.app.ProgressDialog;
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
import com.example.pristinepastries.models.Categories;
import com.example.pristinepastries.templates.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setTitle("Cake Categories");
        recyclerView = findViewById(R.id.recycler_view);
        loadCategories();
    }

    public void loadCategories(){
        GlobalVariables.categoriesList = new ArrayList<>();
        Log.d(TAG, "loading categories: " + GlobalVariables.CATEGORY_URL);
        pd = new ProgressDialog(CategoryActivity.this);
        pd.setMessage("Loading Categories");
        pd.show();

        StringRequest getCategories = new StringRequest(Request.Method.GET,
                GlobalVariables.CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "response received");

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(CategoryActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray categories = jsonObject.getJSONArray("categories");
                    for (int i = 0; i < categories.length(); i++) {
                        GlobalVariables.categoriesList.add(
                                new Categories(
                                        categories.getJSONObject(i).getString("id"),
                                        categories.getJSONObject(i).getString("name"),
                                        categories.getJSONObject(i).getString("description"),
                                        categories.getJSONObject(i).getString("image")
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
                Toast.makeText(CategoryActivity.this, "Cannot connect to the server",
                        Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(getCategories);

    }

    public void loadRecyclerView(){
        Log.d(TAG, "initializing recyclerview adapter");
        categoryAdapter = new CategoryAdapter(GlobalVariables.categoriesList, this);
        recyclerView.setAdapter(categoryAdapter);
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
