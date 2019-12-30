package com.anaaa.mongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.configurl;
import sesion.sesionmanager;

public class MainActivity extends AppCompatActivity {

    private sesionmanager sesion;
    private CircularRevealCardView Keluar;
    private CircularRevealCardView tambahdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sesion = new sesionmanager(this);

        tambahdata = (CircularRevealCardView) findViewById(R.id.card);

        tambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, inputMk.class);
                startActivity(i);
                finish();
            }
        });


        Keluar = (CircularRevealCardView) findViewById(R.id.card3);

        Keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sesion.setLogin(false);
                sesion.setSkip(false);
                sesion.getSessid();
                Intent i = new Intent(MainActivity.this, loginactivity.class);
                startActivity(i);
                finish();
            }
        });

    }

//    private void fetchJsonResponse() {
//        // Pass second argument as "null" for GET requests
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, configurl.getAllmahasiswa, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String result = response.getString("data");
////                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
//                            Log.v("ini data dari server", result.toString());
//
//                            JSONArray res = new JSONArray(result);
//                            for (int i = 0; i < res.length(); i++){
//                                JSONObject jObj = res.getJSONObject(i);
//                                txtdata.append("NPM = " + jObj.getString("npm") + "\n" +
//                                        "Nama = " + jObj.getString("nama") + "\n\n");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());
//            }
//        });
//
//        /* Add your Requests to the RequestQueue to execute */
//        mRequestQueue.add(req);
//    }

}

