package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;

public class InstructorActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtGreet;
    public CardView cardViewProgress,cardViewScheduleApproval;
    Button btnLogOutInstructor;

    private ArrayList<Integer> studentIDList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> licenceTypeList = new ArrayList<>();
    private ArrayList<Integer> progressList = new ArrayList<>();

    private ArrayList<Integer> studentIDList2 = new ArrayList<>();
    private ArrayList<String> nameList2 = new ArrayList<>();
    private ArrayList<String> licenceTypeList2 = new ArrayList<>();
    private ArrayList<Integer> progressList2 = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<Integer> scheduleIDList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        setTitle("Instructor");

        fnGetStudentProgress();
        fnGetScheduleRequest();

        txtGreet = findViewById(R.id.txtGreetInstructor);
        cardViewProgress = findViewById(R.id.cardViewProgress);
        cardViewScheduleApproval = findViewById(R.id.cardViewScheduleApproval);
        btnLogOutInstructor = findViewById(R.id.btnLogOutInstructor);

        String arr[] = Staff.getInstance().getName().split(" ");
        String name1 = arr[0];
        txtGreet.setText("Hello, " + name1 + "!");

        cardViewProgress.setOnClickListener(this);
        cardViewScheduleApproval.setOnClickListener(this);

        btnLogOutInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(InstructorActivity.this).setIcon(R.drawable.baseline_logout_24)
                        .setTitle("Log Out Confirmation").setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(getApplicationContext(), "Successfully logged out.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("No", null).show();
            }

        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        if(v.getId() == R.id.cardViewProgress){
            intent = new Intent(getApplicationContext(), StudentProgressActivity.class);
            startActivity(intent);
        }

        else if(v.getId() == R.id.cardViewScheduleApproval) {
            intent = new Intent(getApplicationContext(), ScheduleApprovalActivity.class);
            startActivity(intent);
        }
    }

    private void fnGetStudentProgress() {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/getStudentProgress.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    studentIDList.clear();
                    nameList.clear();
                    licenceTypeList.clear();
                    progressList.clear();

                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    if (response.equals("No data."))
                    {

                        Student.getInstance().setDataEmpty(true);

                    }
                    else{

                        Student.getInstance().setDataEmpty(false);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            int studentID = obj.getInt("studentID");
                            int progress = obj.getInt("progress");
                            String licenceType = obj.getString("type");
                            String name = obj.getString("name");

                            studentIDList.add(studentID);
                            progressList.add(progress);
                            licenceTypeList.add(licenceType);
                            nameList.add(name);

                        }

                        Student.getInstance().setStudentIDList(studentIDList);
                        Student.getInstance().setProgressList(progressList);
                        Student.getInstance().setLicenceTypeList(licenceTypeList);
                        Student.getInstance().setNameList(nameList);
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

                String instructorID = String.valueOf(Staff.getInstance().getStaffID());

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnGetStudentProgress");
                params.put("instructorID", instructorID);

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }

    private void fnGetScheduleRequest() {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/getStudentProgress.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    studentIDList2.clear();
                    nameList2.clear();
                    licenceTypeList2.clear();
                    progressList2.clear();
                    dateList.clear();
                    scheduleIDList.clear();

                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    if (response.equals("No data."))
                    {
                        Toast.makeText(getApplicationContext(),"No data is currently available.", Toast.LENGTH_SHORT).show();
                        Student.getInstance().setDataEmpty(true);

                    }
                    else{

                        Student.getInstance().setDataEmpty(false);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            int studentID = obj.getInt("studentID");
                            int progress = obj.getInt("progress");
                            String licenceType = obj.getString("type");
                            String name = obj.getString("name");
                            String date = obj.getString("date");
                            int scheduleID = obj.getInt("scheduleID");

                            studentIDList2.add(studentID);
                            progressList2.add(progress);
                            licenceTypeList2.add(licenceType);
                            nameList2.add(name);
                            dateList.add(date);
                            scheduleIDList.add(scheduleID);

                        }

                        Schedule.getInstance().setStudentIDList(studentIDList2);
                        Schedule.getInstance().setProgressList(progressList2);
                        Schedule.getInstance().setLicenceTypeList(licenceTypeList2);
                        Schedule.getInstance().setNameList(nameList2);
                        Schedule.getInstance().setDateList(dateList);
                        Schedule.getInstance().setScheduleIDList(scheduleIDList);
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

                String instructorID = String.valueOf(Staff.getInstance().getStaffID());

                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnGetScheduleRequest");
                params.put("instructorID", instructorID);

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }
}