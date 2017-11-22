package com.android.sparhim.patients;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;


public class MainActivity extends AppCompatActivity {
    public JSONArray patients = null;
    public HashMap<String,String> patientMap = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        String url ="http://10.0.2.2:8077/Patients/Patients";
        final Context th = this;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("info",response);
                        List<String> listContents = null;
                        try {
                            patients = new JSONArray(response);

                            int length = patients.length();
                            listContents = new ArrayList<String>(length);
                            for (int i = 0; i < length; i++) {
                                String name = patients.getJSONObject(i).get("name").toString();
                                String id = patients.getJSONObject(i).get("id").toString();
                                Log.i("patient", name + " | " + id);
                                listContents.add(name);
                                patientMap.put(name, id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final ListView myListView = (ListView) findViewById(R.id.listView);
                        myListView.setAdapter(new ArrayAdapter<String>(th, android.R.layout.simple_list_item_1, listContents));
                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Log.i("clicked",myListView.getItemAtPosition(i).toString());
                                Intent intent = new Intent(th,ListItem.class);
                                String name = myListView.getItemAtPosition(i).toString();
                                String id = patientMap.get(name).toString();
                                intent.putExtra("name",name);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                List<String> listContents = null;
                try {
                    patients = new JSONArray("[{\"name\": \"John\",\"id\": \"D11232\"}, {\"name\": \"Pete\",\"id\": \"R21237\"}, {\"name\": \"Jane\",\"id\": \"Y5325\"}, {\"name\": \"Bob\",\"id\": \"K99\"}, {\"name\": \"Lee\",\"id\": \"M912\"}]");

                    int length = patients.length();
                    listContents = new ArrayList<String>(length);
                    for (int i = 0; i < length; i++) {
                        String name = patients.getJSONObject(i).get("name").toString();
                        String id = patients.getJSONObject(i).get("id").toString();
                        Log.i("patient", name + " | " + id);
                        listContents.add(name);
                        patientMap.put(name, id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final ListView myListView = (ListView) findViewById(R.id.listView);
                myListView.setAdapter(new ArrayAdapter<String>(th, android.R.layout.simple_list_item_1, listContents));
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("clicked",myListView.getItemAtPosition(i).toString());
                        Intent intent = new Intent(th,ListItem.class);
                        String name = myListView.getItemAtPosition(i).toString();
                        String id = patientMap.get(name).toString();
                        intent.putExtra("name",name);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                });
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }




}
