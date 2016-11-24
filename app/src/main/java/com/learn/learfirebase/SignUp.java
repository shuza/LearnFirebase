package com.learn.learfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boka on 20-Nov-16.
 */

public class SignUp extends AppCompatActivity {
    private TextInputLayout lyFirstName, lyLastName, lyEmail, lyPassword;
    private EditText etFirstName, etLastName, etEmail, etPassword;
    private Button btnSignup;
    private ProgressDialog dialog;

    //private String API_LINK = "http://192.168.2.100:8888/API/API_SignUp.php";
    private String API_LINK = "http://ec2-54-186-116-27.us-west-2.compute.amazonaws.com:8888/Mobile_API/API_SignUp.php";
    private String TAG = "Sugn up activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        lyFirstName = (TextInputLayout) findViewById(R.id.input_layout_first_name);
        lyLastName = (TextInputLayout) findViewById(R.id.input_layout_last_name);
        lyEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        lyPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSignup = (Button) findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVaildForm()) {
                    RunSignUp();
                }
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("Sign up");
        dialog.setMessage("Please wait..");
    }

    private void RunSignUp() {
        dialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, API_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Toast.makeText(SignUp.this, "on response..", Toast.LENGTH_SHORT).show();
                if (response.contentEquals("1")){
                    SharedPreferences.Editor user = PreferenceManager.getDefaultSharedPreferences(SignUp.this).edit();
                    user.putBoolean("isRegistered", true);
                    user.commit();

                    Intent intent = new Intent(SignUp.this, ResultActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SignUp.this, "Error: " + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(SignUp.this, "Response Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("first_name", etFirstName.getText().toString().trim());
                params.put("last_name", etLastName.getText().toString().trim());
                params.put("email", etEmail.getText().toString().trim());
                params.put("password", etPassword.getText().toString().trim());
                return params;
            }
        };
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        postRequest.setTag(TAG);
        MySingleton.getInstance(this).addToRequestQueue(postRequest);

    }

    public boolean isVaildForm() {
        if (etFirstName.getText().toString().trim().length() <= 0) {
            lyFirstName.setError("invalid first name");
            requestFocus(lyFirstName);
            return false;
        }
        if (etLastName.getText().toString().trim().length() <= 0) {
            lyLastName.setError("invalid last name");
            requestFocus(lyLastName);
            return false;
        }
        if (etEmail.getText().toString().trim().length() <= 0) {
            lyEmail.setError("invalid email");
            requestFocus(lyEmail);
            return false;
        }
        if (etPassword.getText().toString().trim().length() <= 0) {
            lyPassword.setError("invalid password");
            requestFocus(lyPassword);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
