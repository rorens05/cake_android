package com.example.pristinepastries.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.squareup.picasso.Picasso;

public class ImageZoomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        ImageView image = findViewById(R.id.cakeZoom);

        try{
            Picasso.get()
                    .load(GlobalVariables.selectedCake.image)
                    .resize(1080, 1080)
                    .centerCrop()
                    .into(image);
        }catch (Exception e){
            Picasso.get()
                    .load("https://cake-reservation.herokuapp.com/images/image_not_found.png")
                    .resize(1080, 1080)
                    .centerCrop()
                    .into(image);
        }
    }
}
