package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class StudentProgressActivity extends AppCompatActivity {

    RadioButton rbStudentID, rbName, rbLicenceType, rbProgress;
    EditText edtTxtSearch;
    Button btnSearch;
    String searchType, searchValue,search_value;


    private ArrayList<Integer> studentIDList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> licenceTypeList = new ArrayList<>();
    private ArrayList<Integer> progressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_progress);

        // Set Title Text and Bar Colour
        ActionBar aBar;
        aBar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#000080"));
        aBar.setBackgroundDrawable(cd);
        int white = Color.parseColor("white");
        Spannable spannable = new SpannableString("Student Progress");
        spannable.setSpan(new ForegroundColorSpan(white), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        aBar.setTitle(spannable);

        rbStudentID = findViewById(R.id.rbStudentID);
        rbName = findViewById(R.id.rbName);
        rbLicenceType = findViewById(R.id.rbLicenceType);
        rbProgress = findViewById(R.id.rbProgress);
        edtTxtSearch = findViewById(R.id.edtTxtSearch);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtTxtSearch.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Please enter your query.", Toast.LENGTH_SHORT).show();

                }

                else{

                    if (rbStudentID.isChecked() ) {

                        searchType = "t1.studentID";
                        searchValue = edtTxtSearch.getText().toString();
                        fnGetSpecificStudentProgress(searchType, searchValue);
                    }
                    else if(rbName.isChecked()) {

                        searchType = "t1.name";
                        searchValue = edtTxtSearch.getText().toString();
                        fnGetSpecificStudentProgress(searchType, searchValue);
                    }
                    else if(rbLicenceType.isChecked()) {

                        searchType = "t3.type";
                        searchValue = edtTxtSearch.getText().toString();
                        fnGetSpecificStudentProgress(searchType, searchValue);
                    }
                    else if(rbProgress.isChecked()) {

                        searchType = "t1.progress";
                        search_value = edtTxtSearch.getText().toString();

                        if(search_value.equalsIgnoreCase("kpp01")){
                            searchValue = "1";
                        }

                        else if(search_value.equalsIgnoreCase("kpp02")) {
                            searchValue = "2";
                        }
                        else if(search_value.equalsIgnoreCase("kpp03")) {
                            searchValue = "3";
                        }
                        else if(search_value.equalsIgnoreCase("qti")) {
                            searchValue = "4";
                        }
                        else if(search_value.equalsIgnoreCase("jpj test")) {
                            searchValue = "5";
                        }

                        else{
                            searchValue = "0";
                        }

                        fnGetSpecificStudentProgress(searchType, searchValue);
                    }

                }


            }
        });

        if(Student.getInstance().isDataEmpty() == false){
            showTableLayout();
        }

        else {
            showTableHeaderOnly();
        }

    }


    public void showTableLayout() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);  //Table layout
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

        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);

        //Below is the Table data with 4 columns
        for (int i = 0; i < Student.getInstance().getStudentIDList().size(); i++) {
            TableRow tbrow = new TableRow(this); //Table row for data

            int studentID = Student.getInstance().getStudentIDList().get(i);
            String name = Student.getInstance().getNameList().get(i);
            String licenceType = Student.getInstance().getLicenceTypeList().get(i);
            int progress = Student.getInstance().getProgressList().get(i);
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

            if(i > 0){
                if(studentID == Student.getInstance().getStudentIDList().get(i-1)){
                    continue;
                }
            }


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
            stk.addView(tbrow);
        }
    }

    public void reshowTableLayout() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);  //Table layout
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

        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);

        //Below is the Table data with 4 columns
        for (int i = 0; i < Student.getInstance().getStudentIDList().size(); i++) {
            TableRow tbrow = new TableRow(this); //Table row for data

            int studentID = Student.getInstance().getStudentIDList().get(i);
            String name = Student.getInstance().getNameList().get(i);
            String licenceType = Student.getInstance().getLicenceTypeList().get(i);
            int progress = Student.getInstance().getProgressList().get(i);
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

            if(i > 0){
                if(studentID == Student.getInstance().getStudentIDList().get(i-1)){
                    continue;
                }
            }


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
            stk.addView(tbrow);
        }
    }

    public void showTableHeaderOnly(){
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);  //Table layout
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

        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);
    }



    private void fnGetSpecificStudentProgress(String typeOfSearch, String valueOfSearch) {

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
                        Toast.makeText(getApplicationContext(),"No data for the search.", Toast.LENGTH_SHORT).show();


                    }
                    else{


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


                        reshowTableLayout();
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
                params.put("selectFn", "fnGetSpecificStudentProgress");
                params.put("instructorID", instructorID);
                params.put("columnName", typeOfSearch);
                params.put("columnValue", valueOfSearch);

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }


}