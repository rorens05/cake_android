package com.example.pristinepastries.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.pristinepastries.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button login;
    EditText email;
    EditText password;
    ProgressDialog pg;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.button);
        signup = findViewById(R.id.textView2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "inside login click, email: " + email.getText().toString() +
                        ", password: " + password.getText().toString());

                login();

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });

    }

    private void login(){
        Log.d(TAG, "inside the login");
        pg = new ProgressDialog(this);
        pg.setMessage("Logging in");
        pg.show();
        StringRequest loginRequest = new StringRequest(Request.Method.POST, GlobalVariables.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "response recieved");
                try {
                    JSONObject jResponse = new JSONObject(response);
                    String status = jResponse.getString("status");
                    String message = jResponse.getString("message");
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (status.equalsIgnoreCase("success")) {
                        Log.d(TAG, "correct username and password");
                        JSONObject user = jResponse.getJSONObject("user");
                        GlobalVariables.currentUser = new User(
                            user.getString("id"),
                            user.getString("name"),
                            user.getString("address"),
                            user.getString("contact_no"),
                            user.getString("email"),
                            user.getString("gender"),
                            user.getString("status"),
                            user.getString("created_at"),
                            user.getString("updated_at"),
                            user.getString("password_digest"),
                            user.getString("image")
                            );
                        Log.d(TAG, GlobalVariables.currentUser.toString());

                        startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                        finish();
                    }else{
                        Log.d(TAG, "incorrect username and password");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pg.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "no response recieved");
                Toast.makeText(MainActivity.this, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                pg.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(loginRequest);
    }


}
