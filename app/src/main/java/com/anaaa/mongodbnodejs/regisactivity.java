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

public class regisactivity extends AppCompatActivity {

        private RequestQueue mRequestQueue;
        private ProgressDialog pDialog;

        private EditText npm, nama, password, prodi;
        private Button input;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_regisactivity);

            pDialog = new ProgressDialog(this);
            pDialog .setCancelable(false);

            mRequestQueue = Volley.newRequestQueue(this);

            npm = (EditText) findViewById(R.id.editnpm) ;
            password = (EditText) findViewById(R.id.editpassword) ;
            nama = (EditText) findViewById(R.id.editnama) ;
            prodi = (EditText) findViewById(R.id.editprodi) ;

            input = (Button) findViewById(R.id.btnregis) ;

            input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String strnpm = npm.getText().toString();
                    String strpassword = password.getText().toString();
                    String strnama = nama.getText().toString();
                    String strprodi = prodi.getText().toString();

                    if (strnpm.isEmpty()){
                        Toast.makeText(getApplicationContext(), "NPM Tidak Boleh Kosong",
                                Toast.LENGTH_LONG).show();
                    } else if (strnama.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong",
                                Toast.LENGTH_LONG).show();
                    } else if (strpassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong",
                                Toast.LENGTH_LONG).show();
                    } else if (strprodi.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Prodi Tidak Boleh Kosong",
                                Toast.LENGTH_LONG).show();
                    } else {
                        inputdata(strnpm, strpassword, strnama, strprodi);

                    }

                }
            });


            fetchJsonResponse();
        }

        private void fetchJsonResponse() {
            // Pass second argument as "null" for GET requests
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, configurl.getAllmahasiswa, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String result = response.getString("data");
//                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                                Log.v("ini data dari server", result.toString());

//                                JSONArray res = new JSONArray(result);
//                                for (int i = 0; i < res.length(); i++){
//                                    JSONObject jObj = res.getJSONObject(i);
//                                    txtdata.append("NPM = " + jObj.getString("npm") + "\n" +
//                                            "Nama = " + jObj.getString("nama") + "\n\n");
//                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

            /* Add your Requests to the RequestQueue to execute */
            mRequestQueue.add(req);
        }

        private void inputdata(String npm, String password, String nama, String prodi){

//        final String URL = "/volley/resource/12";
// Post params to be sent to the server
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("npm", npm);
            params.put("password", password);
            params.put("nama", nama);
            params.put("prodi", prodi);

            pDialog.setMessage("Mohon Tunggu");
            showDialog();

            JsonObjectRequest req = new JsonObjectRequest(configurl.simpanmahasiswa, new JSONObject(params),
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
                                    msg = response.getString("pesan");
                                    Intent a = new Intent(regisactivity.this, loginactivity.class);
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