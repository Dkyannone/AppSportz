package com.example.yanno.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "https://10.0.0.46:8089/register.php";
    ProgressDialog progressDialog;

    EditText name;
    EditText surname;
    EditText email;
    EditText sport;
    EditText team;
    EditText password;
    private Button btnRegister;
    private Button btnLinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.etName);
        surname = (EditText)findViewById(R.id.etSurname);
        email = (EditText)findViewById(R.id.etEmail);
        sport = (EditText)findViewById(R.id.etSport);
        team = (EditText)findViewById(R.id.etTeam);
        password = (EditText)findViewById(R.id.etPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkLogin = (Button) findViewById(R.id.btnLinkLogin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
                startActivity(new Intent(RegisterActivity.this, Home1Activity.class));

            }
        });
        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void submitForm() {

        registerUser(name.getText().toString(),
                surname.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                sport.getText().toString(),
                team.getText().toString());

    }

    private void registerUser(final String name,  final String surname, final String email, final String password,
                              final String sport, final String team) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Adding you ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("surname", surname);
                params.put("email", email);
                params.put("password", password);
                params.put("sport", sport);
                params.put("team", team);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
