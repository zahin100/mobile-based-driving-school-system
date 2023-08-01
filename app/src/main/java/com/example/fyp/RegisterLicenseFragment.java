package com.example.fyp;




import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;

import android.widget.Button;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RegisterLicenseFragment extends Fragment {

    View v;
    Spinner spinLicenceType;
    String licenceType;
    TextView textViewPrice;
    Button registerLicence, uploadFile;
    boolean isFileUploaded;
    int licenceID;
    double amountPaid;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_register_license, container, false);

        spinLicenceType = v.findViewById(R.id.spinLicenceType);
        textViewPrice = v.findViewById(R.id.textViewPrice);
        registerLicence = v.findViewById(R.id.btnRegisterLicence);
        uploadFile = v.findViewById(R.id.button_uploadFile);


        spinLicenceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                licenceType = spinLicenceType.getSelectedItem().toString();

                if(licenceType.equals("B2")) {
                    textViewPrice.setText("Total Price: RM 600");
                    licenceID = 1;
                    amountPaid = 600;
                }

                else if(licenceType.equals("B")) {
                    textViewPrice.setText("Total Price: RM 1300");
                    licenceID = 2;
                    amountPaid = 1300;
                }

                else if(licenceType.equals("D")) {
                    textViewPrice.setText("Total Price: RM 1500");
                    licenceID = 3;
                    amountPaid = 1500;
                }

                else if(licenceType.equals("DA")) {
                    textViewPrice.setText("Total Price: RM 1600");
                    licenceID = 4;
                    amountPaid = 1600;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UploadFileActivity.class);
                startActivity(intent);
            }
        });


        registerLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnCheckIfFileUploaded();

                if(isFileUploaded == true){
                    Toast.makeText(getContext(),"Registration is successful!", Toast.LENGTH_SHORT).show();
                    Student.getInstance().setIsRegister(true);
                    fnRegisterLicence();
                    Intent intent = new Intent(getContext(),StudentActivity.class);
                    startActivity(intent);
                }

            }
        });


        return v;
    }

    private void fnCheckIfFileUploaded() {


        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/checkIfFileUploaded.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    if (response.equals("File does not exist in Database"))
                    {
                        Toast.makeText(getContext(),"Please upload your receipt.", Toast.LENGTH_SHORT).show();
                        isFileUploaded = false;

                    }
                    else{


                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);


                            isFileUploaded = true;





                        }
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


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnCheckIfFileUploaded");
                params.put("studentID", String.valueOf(Student.getInstance().getStudentID()));

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }


    private void fnRegisterLicence() {


        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/registerLicence.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);





                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);








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


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnRegisterLicence");
                params.put("licenceID", String.valueOf(licenceID));
                params.put("amountPaid", String.valueOf(amountPaid));
                params.put("studentID", String.valueOf(Student.getInstance().getStudentID()));

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }


}