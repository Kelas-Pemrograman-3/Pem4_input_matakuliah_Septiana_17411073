package com.anaaa.mongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.configurl;
import sesion.sesionmanager;

public class loginactivity extends AppCompatActivity {


        private RequestQueue mRequestQueue;
        private ProgressDialog pDialog;
        private sesionmanager sesion;

        private EditText npm, password;
        private Button login, linkregis;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_loginactivity);
            getSupportActionBar().hide();

            pDialog = new ProgressDialog(this);
            pDialog .setCancelable(false);

            sesion = new sesionmanager(this);

            if(sesion.isLoggedIn()){
                Intent i = new Intent(loginactivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

            mRequestQueue = Volley.newRequestQueue(this);

            npm = (EditText) findViewById(R.id.editnpm) ;
            password = (EditText) findViewById(R.id.editpassword) ;

            linkregis = findViewById(R.id.btnlinkregis);

            linkregis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(loginactivity.this, regisactivity.class);
                    startActivity(a);
                    finish();
                }
            });

            login = (Button) findViewById(R.id.btnlogin) ;

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String strnpm = npm.getText().toString();
                    String strpassword = password.getText().toString();

                    if (strnpm.isEmpty()){
                        Toast.makeText(getApplicationContext(), "NPM Tidak Boleh Kosong",
                                Toast.LENGTH_LONG).show();
                    } else if (strpassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong",
                                Toast.LENGTH_LONG).show();
                    } else {
                        login(strnpm, strpassword);

                    }

                }
            });

        }


        private void login(String npm, String password){

//        final String URL = "/volley/resource/12";
// Post params to be sent to the server
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("npm", npm);
            params.put("password", password);

            JsonObjectRequest req = new JsonObjectRequest(configurl.login, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hideDialog();
                            try {
                                boolean status = response.getBoolean("error");
                                String msg;
                                if(status == true){
                                    msg = response.getString("pesan");
                                } else {
                                    sesion.setLogin(true);
                                    msg = response.getString("pesan");
                                    Intent a = new Intent(loginactivity.this, MainActivity.class);
                                    startActivity(a);
                                    finish();
                                }
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                                VolleyLog.v("Response:%n %s", response.toString(4));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideDialog();
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

// add the request object to the queue to be executed
//        ApplicationController.getInstance().addToRequestQueue(req);
            mRequestQueue.add(req);
        }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    }

