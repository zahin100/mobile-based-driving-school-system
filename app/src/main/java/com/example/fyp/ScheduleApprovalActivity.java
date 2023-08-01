package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class ScheduleApprovalActivity extends AppCompatActivity {

    String selectedScheduleID;
    String action;
    private ArrayList<Integer> studentIDList2 = new ArrayList<>();
    private ArrayList<String> nameList2 = new ArrayList<>();
    private ArrayList<String> licenceTypeList2 = new ArrayList<>();
    private ArrayList<Integer> progressList2 = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<Integer> scheduleIDList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_approval);

        // Set Title Text and Bar Colour
        ActionBar aBar;
        aBar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#000080"));
        aBar.setBackgroundDrawable(cd);
        int white = Color.parseColor("white");
        Spannable spannable = new SpannableString("Schedule Approval");
        spannable.setSpan(new ForegroundColorSpan(white), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        aBar.setTitle(spannable);


        // Check if table is empty or not
        if(Student.getInstance().isDataEmpty() == false){
            showTableLayout2();
        }

        else {
            showTableHeaderOnly2();
        }


    }


    public void showTableLayout2() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main2);  //Table layout
        TableRow tbrow0 = new TableRow(this); //Table row for headers

        //Table Headers
        TextView tv0 = new TextView(this);
        tv0.setText(" ID ");
        tv0.setTextSize(18);
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundResource(R.color.navy);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(10,10,10,10);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Name ");
        tv1.setTextSize(18);
        tv1.setTextColor(Color.WHITE);
        tv1.setBackgroundResource(R.color.navy);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(10,10,10,10);
        tbrow0.addView(tv1);


        TextView tv2 = new TextView(this);
        tv2.setText(" Licence Type ");
        tv2.setTextSize(18);
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(R.color.navy);
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(10,10,10,10);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Progress ");
        tv3.setTextSize(18);
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(R.color.navy);
        tv3.setTypeface(null, Typeface.BOLD);
        tv3.setPadding(10,10,10,10);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Date, Time ");
        tv4.setTextSize(18);
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundResource(R.color.navy);
        tv4.setTypeface(null, Typeface.BOLD);
        tv4.setPadding(10,10,10,10);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" Approve / Reject ");
        tv5.setTextSize(18);
        tv5.setTextColor(Color.WHITE);
        tv5.setBackgroundResource(R.color.navy);
        tv5.setTypeface(null, Typeface.BOLD);
        tv5.setPadding(10,10,10,10);
        tbrow0.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText(" Approve / Reject ");
        tv6.setTextSize(18);
        tv6.setTextColor(Color.parseColor("#101D6B"));
        tv6.setBackgroundResource(R.color.navy);
        tv6.setTypeface(null, Typeface.BOLD);
        tv6.setPadding(10,10,10,10);
        tbrow0.addView(tv6);

        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);

        //Below is the Table data with 7 columns
        for (int i = 0; i < Schedule.getInstance().getScheduleIDList().size(); i++) {
            TableRow tbrow = new TableRow(this); //Table row for data

            int studentID = Schedule.getInstance().getStudentIDList().get(i);
            String name = Schedule.getInstance().getNameList().get(i);
            String licenceType = Schedule.getInstance().getLicenceTypeList().get(i);
            int progress = Schedule.getInstance().getProgressList().get(i);
            String progressName = "";

            if(progress == 1){
                progressName  = "KPP01";
            }

            else if(progress == 2){
                progressName = "KPP02";
            }

            else if(progress == 3){
                progressName = "KPP03";
            }
            else if(progress == 4){
                progressName = "QTI";
            }
            else if(progress == 5){
                progressName = "JPJ Test";
            }

            String dateTime = Schedule.getInstance().getDateList().get(i);


            TextView t1v = new TextView(this);
            t1v.setText(" " + studentID);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setTextSize(18);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(" " + name);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setTextSize(18);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(" " + licenceType);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            t3v.setTextSize(18);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(" " + progressName);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            t4v.setTextSize(18);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t5v.setText(" " + dateTime + "   ");
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            t5v.setTextSize(18);
            tbrow.addView(t5v);

            Button btnApprove = new Button(this);
            btnApprove.setText(" Approve ");
            btnApprove.setId(i);
            btnApprove.setTextColor(Color.WHITE);
            btnApprove.setGravity(Gravity.CENTER);
            btnApprove.setTextSize(17);
            btnApprove.setBackgroundColor(Color.parseColor("#00D100"));
            btnApprove.setOnClickListener(onClick);
            tbrow.addView(btnApprove);

            // Create unique ID for btnReject by adding 10 000, then at getId(), minus 10 000 so we can get the corresponding scheduleID
            Button btnReject = new Button(this);
            btnReject.setText(" Reject ");
            btnReject.setId(i+10000);
            btnReject.setTextColor(Color.WHITE);
            btnReject.setGravity(Gravity.CENTER);
            btnReject.setTextSize(17);
            btnReject.setBackgroundColor(Color.RED);
            btnReject.setOnClickListener(onClick);
            tbrow.addView(btnReject);

            stk.addView(tbrow);
        }
    }

    public void reshowTableLayout2() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main2);  //Table layout
        stk.removeViews(0, Math.max(0, stk.getChildCount()));
        TableRow tbrow0 = new TableRow(this); //Table row for headers

        //Table Headers
        TextView tv0 = new TextView(this);
        tv0.setText(" ID ");
        tv0.setTextSize(18);
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundResource(R.color.navy);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(10,10,10,10);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Name ");
        tv1.setTextSize(18);
        tv1.setTextColor(Color.WHITE);
        tv1.setBackgroundResource(R.color.navy);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(10,10,10,10);
        tbrow0.addView(tv1);


        TextView tv2 = new TextView(this);
        tv2.setText(" Licence Type ");
        tv2.setTextSize(18);
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(R.color.navy);
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(10,10,10,10);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Progress ");
        tv3.setTextSize(18);
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(R.color.navy);
        tv3.setTypeface(null, Typeface.BOLD);
        tv3.setPadding(10,10,10,10);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Date, Time ");
        tv4.setTextSize(18);
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundResource(R.color.navy);
        tv4.setTypeface(null, Typeface.BOLD);
        tv4.setPadding(10,10,10,10);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" Approve / Reject ");
        tv5.setTextSize(18);
        tv5.setTextColor(Color.WHITE);
        tv5.setBackgroundResource(R.color.navy);
        tv5.setTypeface(null, Typeface.BOLD);
        tv5.setPadding(10,10,10,10);
        tbrow0.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText(" Approve / Reject ");
        tv6.setTextSize(18);
        tv6.setTextColor(Color.parseColor("#101D6B"));
        tv6.setBackgroundResource(R.color.navy);
        tv6.setTypeface(null, Typeface.BOLD);
        tv6.setPadding(10,10,10,10);
        tbrow0.addView(tv6);

        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);

        //Below is the Table data with 7 columns
        for (int i = 0; i < Schedule.getInstance().getScheduleIDList().size(); i++) {
            TableRow tbrow = new TableRow(this); //Table row for data

            int studentID = Schedule.getInstance().getStudentIDList().get(i);
            String name = Schedule.getInstance().getNameList().get(i);
            String licenceType = Schedule.getInstance().getLicenceTypeList().get(i);
            int progress = Schedule.getInstance().getProgressList().get(i);
            String progressName = "";

            if(progress == 1){
                progressName  = "KPP01";
            }

            else if(progress == 2){
                progressName = "KPP02";
            }

            else if(progress == 3){
                progressName = "KPP03";
            }
            else if(progress == 4){
                progressName = "QTI";
            }
            else if(progress == 5){
                progressName = "JPJ Test";
            }

            String dateTime = Schedule.getInstance().getDateList().get(i);


            TextView t1v = new TextView(this);
            t1v.setText(" " + studentID);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setTextSize(18);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(" " + name);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setTextSize(18);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(" " + licenceType);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            t3v.setTextSize(18);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(" " + progressName);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            t4v.setTextSize(18);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t5v.setText(" " + dateTime + "   ");
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            t5v.setTextSize(18);
            tbrow.addView(t5v);

            Button btnApprove = new Button(this);
            btnApprove.setText(" Approve ");
            btnApprove.setId(i);
            btnApprove.setTextColor(Color.WHITE);
            btnApprove.setGravity(Gravity.CENTER);
            btnApprove.setTextSize(17);
            btnApprove.setBackgroundColor(Color.parseColor("#00D100"));
            btnApprove.setOnClickListener(onClick);
            tbrow.addView(btnApprove);

            // Create unique ID for btnReject by adding 10 000, then at getId(), minus 10 000 so we can get the corresponding scheduleID
            Button btnReject = new Button(this);
            btnReject.setText(" Reject ");
            btnReject.setId(i+10000);
            btnReject.setTextColor(Color.WHITE);
            btnReject.setGravity(Gravity.CENTER);
            btnReject.setTextSize(17);
            btnReject.setBackgroundColor(Color.RED);
            btnReject.setOnClickListener(onClick);
            tbrow.addView(btnReject);

            stk.addView(tbrow);
        }
    }

    public void showTableHeaderOnly2(){
        TableLayout stk = (TableLayout) findViewById(R.id.table_main2);  //Table layout
        TableRow tbrow0 = new TableRow(this); //Table row for headers

        //Table Headers
        TextView tv0 = new TextView(this);
        tv0.setText(" ID ");
        tv0.setTextSize(18);
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundResource(R.color.navy);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(10,10,10,10);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Name ");
        tv1.setTextSize(18);
        tv1.setTextColor(Color.WHITE);
        tv1.setBackgroundResource(R.color.navy);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(10,10,10,10);
        tbrow0.addView(tv1);


        TextView tv2 = new TextView(this);
        tv2.setText(" Licence Type ");
        tv2.setTextSize(18);
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(R.color.navy);
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(10,10,10,10);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Progress ");
        tv3.setTextSize(18);
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(R.color.navy);
        tv3.setTypeface(null, Typeface.BOLD);
        tv3.setPadding(10,10,10,10);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Date, Time ");
        tv4.setTextSize(18);
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundResource(R.color.navy);
        tv4.setTypeface(null, Typeface.BOLD);
        tv4.setPadding(10,10,10,10);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" Approve / Reject ");
        tv5.setTextSize(18);
        tv5.setTextColor(Color.WHITE);
        tv5.setBackgroundResource(R.color.navy);
        tv5.setTypeface(null, Typeface.BOLD);
        tv5.setPadding(10,10,10,10);
        tbrow0.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText(" Approve / Reject ");
        tv6.setTextSize(18);
        tv6.setTextColor(Color.parseColor("#101D6B"));
        tv6.setBackgroundResource(R.color.navy);
        tv6.setTypeface(null, Typeface.BOLD);
        tv6.setPadding(10,10,10,10);
        tbrow0.addView(tv6);

        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);
    }

    View.OnClickListener onClick = new View.OnClickListener() {

        @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {

            new AlertDialog.Builder(ScheduleApprovalActivity.this).setTitle("Confirmation").setMessage("Are you sure you want to update the schedule request status?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(ScheduleApprovalActivity.this, "Successfully updated the schedule request status.",Toast.LENGTH_SHORT).show();



                            for(int i = 0; i < Schedule.getInstance().getScheduleIDList().size(); i++){

                                if(view.getId() < 10000){

                                    if(view.getId() == i){
                                        selectedScheduleID = String.valueOf(Schedule.getInstance().getScheduleIDList().get(i));
                                        action = "Approved";
                                        fnUpdateScheduleStatusApprovedOrRejected(selectedScheduleID, action);
                                        Schedule.getInstance().getStudentIDList().remove(i);
                                        Schedule.getInstance().getProgressList().remove(i);
                                        Schedule.getInstance().getLicenceTypeList().remove(i);
                                        Schedule.getInstance().getNameList().remove(i);
                                        Schedule.getInstance().getDateList().remove(i);
                                        Schedule.getInstance().getScheduleIDList().remove(i);

                                        reshowTableLayout2();
                                    }

                                }

                                else {

                                    int btnId = view.getId() - 10000;

                                    if(btnId == i){
                                        selectedScheduleID = String.valueOf(Schedule.getInstance().getScheduleIDList().get(i));
                                        action = "Rejected";
                                        fnUpdateScheduleStatusApprovedOrRejected(selectedScheduleID, action);
                                        Schedule.getInstance().getStudentIDList().remove(i);
                                        Schedule.getInstance().getProgressList().remove(i);
                                        Schedule.getInstance().getLicenceTypeList().remove(i);
                                        Schedule.getInstance().getNameList().remove(i);
                                        Schedule.getInstance().getDateList().remove(i);
                                        Schedule.getInstance().getScheduleIDList().remove(i);

                                        reshowTableLayout2();
                                    }

                                }
                            }
                        }
                    }).setNegativeButton("No", null).show();


        }
    };


    private void fnUpdateScheduleStatusApprovedOrRejected(String scheduleID, String status) {

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
                params.put("selectFn", "fnUpdateScheduleStatusApprovedOrRejected");
                params.put("scheduleID", scheduleID);
                params.put("status", status);

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