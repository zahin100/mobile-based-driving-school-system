package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button registerButton, studentLoginButton, staffLoginButton;
    EditText username, password;
    private ArrayList<Integer> scheduleIDList = new ArrayList<>();
    private ArrayList<Integer> staffIDList = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> statusList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");



        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.passwordLogin);
        studentLoginButton = findViewById(R.id.studentLoginButton);
        staffLoginButton = findViewById(R.id.staffLoginButton);
        registerButton = findViewById(R.id.registerButton1);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        studentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnAddStudentToREST();
            }
        });

        staffLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnAddStaffToREST();
            }
        });

        GetIP.getInstance().setIp("172.20.10.4");

    }

    private void fnAddStudentToREST() {

        String strURL = "http://" + GetIP.getInstance().getIp() +"/fyp/database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    if (response.equals("Username does not exist in Database"))
                    {
                        Toast.makeText(getApplicationContext(),"Username does not exist!", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }
                    else{
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String userName = username.getText().toString();
                            String passWord = password.getText().toString();

                            Student.getInstance().setUsername(userName);


                            String username = obj.getString("username");
                            String password = obj.getString("password");
                            String name = obj.getString("name");
                            String icNum = obj.getString("icNum");
                            String address = obj.getString("address");
                            String phoneNum = obj.getString("phoneNum");
                            int studentID = obj.getInt("studentID");
                            int isRegister = obj.getInt("isRegister");
                            int licenceID = obj.getInt("licenceID");
                            int progress = obj.getInt("progress");

                            Student.getInstance().setName(name);
                            Student.getInstance().setICNum(icNum);
                            Student.getInstance().setAddress(address);
                            Student.getInstance().setPhoneNum(phoneNum);
                            Student.getInstance().setStudentID(studentID);
                            Licence.getInstance().setLicenceID(licenceID);
                            Student.getInstance().setLicence(Licence.getInstance());
                            Student.getInstance().setProgress(progress);

                            if(Student.getInstance().getLicence().getLicenceID() != 0) {

                                String expiryDate = obj.getString("expiryDate");



                            }

                            /*SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            try {

                                Student.getInstance().setExpiryDate(formatter.parse(expiryDate));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*/

                            if(isRegister != 0) {
                                Student.getInstance().setIsRegister(true);
                            }

                            else {
                                Student.getInstance().setIsRegister(false);
                            }


                            if(userName.equals(username) && passWord.equals(password)){
                                Toast.makeText(getApplicationContext(),"Successfully Login!", Toast.LENGTH_SHORT).show();
                                Student.getInstance().setIsStudent(true);

                                if(Student.getInstance().getLicence().getLicenceID() != 0){
                                       fnGetStudentLicenceDetails();
                                }

                                fnUpdateScheduleStatus();
                                fnCheckIfScheduleEmpty();

                                if(Schedule.getInstance().isScheduleEmpty() == false){
                                    fnGetStudentSchedule();
                                }


                                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                                startActivity(intent);

                            }else if(userName != username || passWord != password)
                                Toast.makeText(getApplicationContext(),"Incorrect Username or Password", Toast.LENGTH_SHORT).show();


                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String userName = username.getText().toString();
                String passWord = password.getText().toString();


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnStudentLogin");
                params.put("Username", userName);
                params.put("Password", passWord);

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }

    private void fnAddStaffToREST() {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    if (response.equals("Username does not exist in Database"))
                    {
                        Toast.makeText(getApplicationContext(),"Username does not exist!", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }
                    else{
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String userName = username.getText().toString();
                            String passWord = password.getText().toString();

                            Staff.getInstance().setUsername(userName);


                            String username = obj.getString("username");
                            String password = obj.getString("password");
                            String name = obj.getString("name");
                            String role = obj.getString("role");
                            String address = obj.getString("address");
                            String phoneNum = obj.getString("phoneNum");
                            int staffID = obj.getInt("staffID");


                            Staff.getInstance().setName(name);
                            Staff.getInstance().setRole(role);
                            Staff.getInstance().setAddress(address);
                            Staff.getInstance().setPhoneNum(phoneNum);
                            Staff.getInstance().setStaffID(staffID);

                            if(userName.equals(username) && passWord.equals(password)){
                                Toast.makeText(getApplicationContext(),"Successfully Login!", Toast.LENGTH_SHORT).show();
                                Student.getInstance().setIsStudent(false);

                                if(Staff.getInstance().getRole().equals("0")){
                                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                    startActivity(intent);

                                }

                                else{
                                    Intent intent = new Intent(getApplicationContext(), InstructorActivity.class);
                                   startActivity(intent);

                                }


                            }else if(userName != username || passWord != password)
                                Toast.makeText(getApplicationContext(),"Incorrect Username or Password", Toast.LENGTH_SHORT).show();


                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String userName = username.getText().toString();
                String passWord = password.getText().toString();


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnStaffLogin");
                params.put("Username", userName);
                params.put("Password", passWord);

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }

    private void fnGetStudentLicenceDetails() {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/GetStudentLicenceDetails.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    //if (response.equals("Username does not exist in Database"))
                   // {
                      //  Toast.makeText(getApplicationContext(),"Username does not exist!", Toast.LENGTH_SHORT).show();
                      //  username.setText("");
                        //password.setText("");
                   // }
                    //else{
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);


                            String type = obj.getString("type");
                            double price = obj.getDouble("price");

                            Licence.getInstance().setType(type);
                            Licence.getInstance().setPrice(price);





                        }
                    //}



                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String licence_id = Integer.toString(Licence.getInstance().getLicenceID());

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnGetStudentLicenceDetails");
                params.put("licenceID", licence_id);

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }

    private void fnGetStudentSchedule() {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/scheduleClass.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    scheduleIDList.clear();
                    staffIDList.clear();
                    dateList.clear();
                    statusList.clear();


                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    //if (response.equals("Username does not exist in Database"))
                    // {
                    //  Toast.makeText(getApplicationContext(),"Username does not exist!", Toast.LENGTH_SHORT).show();
                    //  username.setText("");
                    //password.setText("");
                    // }
                    //else{
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);


                        int scheduleID = obj.getInt("scheduleID");
                        //int staffID = obj.getInt("staffID");
                        String date = obj.getString("date");
                        String status = obj.getString("status");

                        scheduleIDList.add(scheduleID);
                        //staffIDList.add(staffID);
                        dateList.add(date);
                        statusList.add(status);




                    }
                    //}
                    Schedule.getInstance().setScheduleIDList(scheduleIDList);
                    Schedule.getInstance().setDateList(dateList);
                    Schedule.getInstance().setStatusList(statusList);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap< >();
                params.put("studentID", String.valueOf(Student.getInstance().getStudentID()));

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }

    private void fnCheckIfScheduleEmpty() {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {



                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    if (response.equals("No schedule"))
                     {
                     Schedule.getInstance().setScheduleEmpty(true);
                     }
                    else{

                        Schedule.getInstance().setScheduleEmpty(false);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);






                    }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnCheckIfScheduleEmpty");
                params.put("studentID", String.valueOf(Student.getInstance().getStudentID()));

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }

    private void fnUpdateScheduleStatus() {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/updateScheduleStatus.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("anyText",response);


                    JSONObject jsonObject = new JSONObject(response);













                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(AccountRegistration.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnUpdateScheduleStatus");


                return params;

            }

        };
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {


        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);


    }

}