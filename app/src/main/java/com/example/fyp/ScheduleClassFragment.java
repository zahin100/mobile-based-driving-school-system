package com.example.fyp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class ScheduleClassFragment extends Fragment {

    String[] descriptionData = {"KPP01","KPP02", "KPP03", "QTI" , "JPJ Test"};
    TextView tv_countdown;
    View v;
    long diff;
    long oldLong;
    long NewLong;
    TextView txtDay, txtHours, txtMinutes, txtSeconds, txtNextClassDate, txtScheduleDate, txtScheduleTime;
    String nearestDate, selectedDate="", selectedTime="", selectedDateTime="";
    Button btnDatePicker, btnTimePicker, btnConfirmSchedule;
    private ArrayList<Integer> scheduleIDList = new ArrayList<>();
    private ArrayList<Integer> staffIDList = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> statusList = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_schedule_class, container, false);

        StateProgressBar stateProgressBar = (StateProgressBar) v.findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);
        txtDay = v.findViewById(R.id.txtDay);
        txtHours = v.findViewById(R.id.txtHour);
        txtMinutes = v.findViewById(R.id.txtMinute);
        txtSeconds = v.findViewById(R.id.txtSecond);
        txtNextClassDate = v.findViewById(R.id.textViewNextClassDate);
        txtScheduleDate = v.findViewById(R.id.textViewScheduleDate);
        txtScheduleTime = v.findViewById(R.id.textViewScheduleTime);
        btnDatePicker = v.findViewById(R.id.buttonDatePicker);
        btnTimePicker = v.findViewById(R.id.buttonTimePicker);
        btnConfirmSchedule = v.findViewById(R.id.buttonScheduleClass);

        int progress = Student.getInstance().getProgress();

        if(progress == 1){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
        }

        else if(progress == 2){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        }

        else if(progress == 3){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        }

        else if(progress == 4){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
        }

        else if(progress == 5){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
        }

        if(Schedule.getInstance().isScheduleEmpty() == false) {

            SimpleDateFormat formatterNearestDate = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            nearestDate = "";
            Date nearest_date;
            for (int i = 0; i < Schedule.getInstance().getDateList().size(); i++) {

                try {
                    nearest_date = formatterNearestDate.parse(Schedule.getInstance().getDateList().get(i));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (i == 0) {
                    nearestDate = Schedule.getInstance().getDateList().get(i);
                } else {
                    try {
                        if (nearest_date.compareTo(formatterNearestDate.parse(Schedule.getInstance().getDateList().get(i - 1))) < 0) {
                            nearestDate = Schedule.getInstance().getDateList().get(i);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            //String oldTime = "04.06.2023, 00:00";//Timer date 1
            //String NewTime = nearestDate;//Timer date 2
            Date oldDate, newDate;
            try {

                oldDate = new Date();
                newDate = formatter.parse(nearestDate);
                oldLong = oldDate.getTime();
                NewLong = newDate.getTime();
                diff = NewLong - oldLong;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            MyCount counter = new MyCount(diff, 1000);
            counter.start();

            displayDate(nearestDate);
        }

        else{

            txtNextClassDate.setText("Please schedule for your next class.");
            txtDay.setText("-");
            txtMinutes.setText("-");
            txtHours.setText("-");
            txtSeconds.setText("-");
        }

        // on below line we are adding click listener for our pick date button
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                txtScheduleDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                                selectedDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        // on below line we are adding click
        // listener for our pick date button
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);


                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String updateMinute;

                                // on below line we are setting selected time
                                // in our text view.
                                if(minute < 10){
                                    updateMinute = "0" + minute;
                                }
                                else{
                                    updateMinute = String.valueOf(minute);
                                }

                                txtScheduleTime.setText(hourOfDay + ":" + updateMinute);
                                selectedTime = hourOfDay + ":" + updateMinute;


                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        //inside button confirm schedule ->
        btnConfirmSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedDateTime = selectedDate + ", " + selectedTime;
                String studentID = String.valueOf(Student.getInstance().getStudentID());

                if(selectedDate.equals("") || selectedTime.equals("")){
                    Toast.makeText(getContext(), "Date or time cannot be empty." , Toast.LENGTH_SHORT).show();
                }

                else{

                    new AlertDialog.Builder(getContext()).setIcon(R.drawable.baseline_logout_24)
                            .setTitle("Schedule Confirmation").setMessage("Are you sure you want to confirm your schedule request?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    fnInsertSchedule(selectedDateTime, studentID);
                                    fnGetStudentSchedule();
                                    Intent intent = new Intent(getContext(),StudentActivity.class);
                                    startActivity(intent);

                                }
                            }).setNegativeButton("No", null).show();


                }

            }
        });

        return v;
    }

    private void displayDate(String date) {

        int progress = Student.getInstance().getProgress();

        if(progress == 1){
            txtNextClassDate.setText("KPP01: " + date);
        }

        else if(progress == 2){
            txtNextClassDate.setText("KPP02: " + date);
        }

        else if(progress == 3){
            txtNextClassDate.setText("KPP03: " + date);
        }

        else if(progress == 4){
            txtNextClassDate.setText("QTI: " + date);
        }

        else if(progress == 5){
            txtNextClassDate.setText("JPJ Test: " + date);
        }

    }

    // countdowntimer is an abstract class, so extend it and fill in methods
    public class MyCount extends CountDownTimer {
        MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            txtDay.setText("-");
            txtMinutes.setText("-");
            txtHours.setText("-");
            txtSeconds.setText("-");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = (TimeUnit.MILLISECONDS.toDays(millis)) + " Days : "
                    + (TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)) + " Hours : ")
                    + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)) + " Minutes : "
                    + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))) + " Seconds";

           String days = String.valueOf(TimeUnit.MILLISECONDS.toDays(millis));
           String hours = String.valueOf(TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)));
           String minutes = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
           String seconds = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes(millis))));

            txtDay.setText(days);
            txtHours.setText(hours);
            txtMinutes.setText(minutes);
            txtSeconds.setText(seconds);
        }
    }

    private void fnInsertSchedule(String date_time, String studentID) {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/updateScheduleStatus.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("anyText",response);

                    /*if (response.equals("Error! Username has been taken in Database"))
                    {
                        Toast.makeText(getApplicationContext(),"Error! Username has been taken in Database! Use other username.", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        confirmPassword.setText("");
                        name.setText("");
                        address.setText("");
                        phoneNumber.setText("");
                        ICNumber.setText("");
                    }

                    else if(response.equals("Password Do not Match!"))
                    {
                        Toast.makeText(getApplicationContext(),"Error! Password Do not Match!", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        confirmPassword.setText("");
                        name.setText("");
                        address.setText("");
                        phoneNumber.setText("");
                        ICNumber.setText("");
                    }

                    //else if(response.equals("Error: Invalid email address format"))
                    // {
                    //  Toast.makeText(getApplicationContext(),"Error: Invalid email address format", Toast.LENGTH_SHORT).show();
                    //email.setText("");
                    // }*/

                    //else{

                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("respond");

                        if(success.equals("Schedule Request Success!")){

                            Toast.makeText(getContext(), "Schedule requested. Wait for approval from your instructor." , Toast.LENGTH_SHORT).show();


                        }

                   // }




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
                params.put("selectFn", "fnInsertSchedule");
                params.put("studentID", studentID);
                params.put("date", date_time);


                return params;

            }

        };
        requestQueue.add(stringRequest);
    }

    private void fnGetStudentSchedule() {

        String strURL = "http://"+ GetIP.getInstance().getIp() +"/fyp/scheduleClass.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

}

