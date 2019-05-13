package com.example.pristinepastries.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    ImageView image;
    TextView name;
    TextView address;
    TextView contact;
    TextView email;
    TextView status;

    Button changePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.textView24);
        address = findViewById(R.id.textView26);
        contact = findViewById(R.id.textView28);
        email = findViewById(R.id.textView29);
        status = findViewById(R.id.textView31);
        image = findViewById(R.id.imageView7);
        changePass = findViewById(R.id.button7);

        name.setText(GlobalVariables.currentUser.name);
        address.setText(GlobalVariables.currentUser.address);
        contact.setText(GlobalVariables.currentUser.contact_no);
        email.setText(GlobalVariables.currentUser.email);
        status.setText(GlobalVariables.currentUser.status);


        try{
            Picasso.get()
                    .load(GlobalVariables.currentUser.image)
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

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
            }
        });
    }


}
