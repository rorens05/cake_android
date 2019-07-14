package com.example.pristinepastries.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pristinepastries.R;
import com.example.pristinepastries.engine.GlobalVariables;
import com.example.pristinepastries.engine.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPassword = findViewById(R.id.editText222);
        newPassword = findViewById(R.id.editText522);
        confirmPassword = findViewById(R.id.editText6100);

        Button save = findViewById(R.id.btnsave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(ChangePasswordActivity.this);
                pd.setMessage("Changing Password");
                pd.setCancelable(false);
                pd.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalVariables.CHANGE_PASS_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getString("status").equalsIgnoreCase("success")){
                                        Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(ChangePasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                pd.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangePasswordActivity.this, "unable to connect to the server", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("old_password", oldPassword.getText().toString());
                        params.put("new_password", newPassword.getText().toString());
                        params.put("confirm_password", confirmPassword.getText().toString());
                        params.put("id", GlobalVariables.currentUser.id);
                        return  params;
                    }
                };
                MySingleton.getInstance(ChangePasswordActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }
}
