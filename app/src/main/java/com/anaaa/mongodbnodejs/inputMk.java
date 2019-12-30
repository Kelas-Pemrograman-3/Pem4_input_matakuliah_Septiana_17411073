package com.anaaa.mongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.configurl;

public class inputMk extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private ProgressDialog pDialog;

    private EditText kodemk, namamk, jam, hari, ruangan, nidn, namadosen;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mk);

        pDialog = new ProgressDialog(this);
        pDialog .setCancelable(false);

        mRequestQueue = Volley.newRequestQueue(this);

        kodemk = (EditText) findViewById(R.id.editkodemk) ;
        namamk = (EditText) findViewById(R.id.editnamamk) ;
        jam = (EditText) findViewById(R.id.editjammk) ;
        hari = (EditText) findViewById(R.id.edithari) ;
        ruangan = (EditText) findViewById(R.id.editruang) ;
        nidn = (EditText) findViewById(R.id.editid) ;
        namadosen = (EditText) findViewById(R.id.editdosen) ;

        edit = (Button) findViewById(R.id.btnedit) ;

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strkodemk = kodemk.getText().toString();
                String strnamamk = namamk.getText().toString();
                String strjam = jam.getText().toString();
                String strhari = hari.getText().toString();
                String strruangan = ruangan.getText().toString();
                String strnidn = nidn.getText().toString();
                String strnamadosen = namadosen.getText().toString();

                if (strkodemk.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Kode Mata Kuliah Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strnamamk.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Mata Kuliah Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strjam.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Jam Kuliah Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strhari.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Hari Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strruangan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ruang Kuliah Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strnidn.isEmpty()){
                    Toast.makeText(getApplicationContext(), "NIDN Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strnamadosen.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Dosen Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else {
                    edit(strkodemk, strnamamk, strjam, strhari, strruangan, strnidn, strnamadosen);

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

    private void edit (String kodemk, String namamk, String jam, String hari, String ruangan, String nidn, String namadosen){

//        final String URL = "/volley/resource/12";
// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("kodeMatakuliah", kodemk);
        params.put("namamatakuliah", namamk);
        params.put("jam", jam);
        params.put("ruangan", ruangan);
        params.put("nidn", nidn);
        params.put("nama", namadosen);

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
                                Intent a = new Intent(inputMk.this, loginactivity.class);
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
