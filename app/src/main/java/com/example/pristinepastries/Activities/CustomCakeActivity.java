package com.example.pristinepastries.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomCakeActivity extends AppCompatActivity {
    public static int RESULT_LOAD_IMG = 101;
    String compressed_bitmap = "";

    Button addImage;
    ImageView image;
    Spinner theme;
    Spinner size;
    Spinner flavor;
    Spinner color;
    Spinner shape;
    EditText no_of_items;
    EditText layers;
    TextView price;
    Button submit;

    ProgressDialog pd;

    int Price = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cake);
        pd = new ProgressDialog(this);
        addImage = findViewById(R.id.button11);
        image = findViewById(R.id.imageView8);
        theme = findViewById(R.id.spinner3);
        size = findViewById(R.id.spinner4);
        flavor = findViewById(R.id.spinner5);
        color = findViewById(R.id.spinner6);
        shape = findViewById(R.id.spinner7);
        no_of_items = findViewById(R.id.editText3);
        layers = findViewById(R.id.editText4);
        price = findViewById(R.id.textView42);
        submit = findViewById(R.id.button12);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(compressed_bitmap.equalsIgnoreCase("")){
                        Toast.makeText(CustomCakeActivity.this, "Please choose image", Toast.LENGTH_SHORT).show();
                    }else{
                        save();
                    }
                }catch(Exception e){
                    Toast.makeText(CustomCakeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayList<String> themes = new ArrayList<>();
//        themes.add("Birthday");
//        themes.add("Aniversary");
//        themes.add("Death");
//        themes.add("Wedding");

        for(int i = 0; i < GlobalVariables.categoriesList.size(); i++){
            themes.add(GlobalVariables.categoriesList.get(i).name);
        }

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                themes);

        theme.setAdapter(spinnerArrayAdapter);

        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("Small");
        sizes.add("Medium");
        sizes.add("Large");

        spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                sizes);

        size.setAdapter(spinnerArrayAdapter);

        ArrayList<String> flavors = new ArrayList<>();
        flavors.add("Strawberry");
        flavors.add("Chocolate");
        flavors.add("Mocha");
        flavors.add("Blueberry");
        flavors.add("Cheese");

        spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                flavors);

        flavor.setAdapter(spinnerArrayAdapter);

        ArrayList<String> colors = new ArrayList<>();
        colors.add("white");
        colors.add("black");
        colors.add("red");
        colors.add("yellow");
        colors.add("green");
        colors.add("blue");

        spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                colors);

        color.setAdapter(spinnerArrayAdapter);

        ArrayList<String> shapes = new ArrayList<>();
        shapes.add("circle");
        shapes.add("square");
        shapes.add("rectangle");
        shapes.add("triangle");

        spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                shapes);

        shape.setAdapter(spinnerArrayAdapter);

        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        no_of_items.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                updatePrice();
            }
        });

        layers.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                updatePrice();
            }
        });
        updatePrice();

    }

    public void updatePrice(){
        int layer = 1;
        int items = 1;
        int size_price;

        if (size.getSelectedItemPosition() == 0){
            size_price = 200;
        }else if(size.getSelectedItemPosition() == 1){
            size_price = 400;
        }else{
            size_price = 600;
        }

        try{
            layer = Integer.parseInt(layers.getText().toString());
            items = Integer.parseInt(no_of_items.getText().toString());


        }catch(Exception e){

        }

        int Price = layer * items * size_price;
        price.setText(String.valueOf(Price));
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

    public void save(){

        pd.setTitle("Please wait..");
        pd.setMessage("Loading..");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalVariables.ADDCUSTOMCAKEURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CustomCakeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(CustomCakeActivity.this, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                params.put("theme", theme.getSelectedItem().toString());
                params.put("size", size.getSelectedItem().toString());
                params.put("flavor", flavor.getSelectedItem().toString());
                params.put("color", color.getSelectedItem().toString());
                params.put("shape", shape.getSelectedItem().toString());
                params.put("customer_id", GlobalVariables.currentUser.id);
                params.put("quantity", no_of_items.getText().toString());
                params.put("layers", layers.getText().toString());
                params.put("price", price.getText().toString());
                params.put("image", compressed_bitmap);

                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                final Bitmap resizedImage = getResizedBitmap(selectedImage, 720, 1080);
                compressed_bitmap = imageToString(resizedImage);
                image.setImageBitmap(resizedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }





    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        Log.d("TAG", Base64.encodeToString(imageBytes, Base64.DEFAULT));
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
