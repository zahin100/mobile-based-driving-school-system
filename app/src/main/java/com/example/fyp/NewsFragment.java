package com.example.fyp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {

    List<News> newss;
    RecyclerView recyclerView;
    View v;
    EditText newsContent;
    Button btnAddNews;
    TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = v.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsContent = v.findViewById(R.id.editTextNewsContent);
        btnAddNews = v.findViewById(R.id.btnAddNews);
        title = v.findViewById(R.id.notes);
        title.setTextColor(Color.WHITE);
        btnAddNews.setTextColor(Color.WHITE);

        newss = new ArrayList<>();

        boolean isStudent = Student.getInstance().isStudent();

        if(isStudent == true){
            btnAddNews.setVisibility(View.GONE);
            newsContent.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) recyclerView.getLayoutParams();
            params.setMargins(params.leftMargin,160,params.rightMargin,params.bottomMargin);
            recyclerView.setLayoutParams(params);
            title.setTextColor(Color.parseColor("navy"));
        }

        btnAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newsContent.getText().toString().equals("")){

                    Toast.makeText(getContext(),"News cannot be empty!",Toast.LENGTH_SHORT).show();
                }

                else {
                    Date currentTime = Calendar.getInstance().getTime();
                    String date = currentTime.toString().trim();
                    News.getInstance().setDate(date);

                    retrieveData();
                    sendData();
                    retrieveData();
                }



            }
        });

        retrieveData();
        return v;
    }

    public void sendData(){


        String url = "http://"+ GetIP.getInstance().getIp() +"/fyp/database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("anyText",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("respond");

                    if(success.equals("Create News Success")) {

                        Toast.makeText(getContext(), "Announcement posted successfully.", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                String newsContents = newsContent.getText().toString();
                String newsDate = News.getInstance().getDate();
                int staffID = Staff.getInstance().getStaffID();



                Map<String, String> params = new HashMap<>();
                params.put("selectFn", "fnNews");
                params.put("newsContents", newsContents);
                params.put("newsDate", newsDate);
                params.put("staffID", String.valueOf(staffID));

                return params;

            }

        };

        requestQueue.add(request);
        retrieveData();
    }

    public void retrieveData() {

        String url = "http://"+ GetIP.getInstance().getIp() +"/fyp/getNews.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("anyText", response);
                    //creating adapter object and setting it to recyclerview
                    NewsAdapter adapter = new NewsAdapter(getContext(), newss);
                    recyclerView.setAdapter(adapter);
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    newss.clear();
                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {


                        //getting product object from json array
                        JSONObject n = array.getJSONObject(i);


                        //adding the product to product list
                        newss.add(new News(

                                n.getString("content"),
                                n.getString("date")

                        ));

                    }


                    adapter.notifyItemInserted(newss.size());




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

           // @Override
           // protected Map<String, String> getParams() throws AuthFailureError {

              //  String username = Staff.getInstance().getUsername();

              //  Map<String, String> params = new HashMap<>();
              //  params.put("staffUsername", username);


             //   return params;

           // }

        };

        requestQueue.add(request);

    }




}