package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText name, ICNumber, address, phoneNumber, username, password, confirmPassword;
    Button signUpButton;
    boolean isRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set Title Text and Bar Colour
        ActionBar aBar;
        aBar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#000080"));
        aBar.setBackgroundDrawable(cd);
        int white = Color.parseColor("white");
        Spannable spannable = new SpannableString("Student Account Registration");
        spannable.setSpan(new ForegroundColorSpan(white), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        aBar.setTitle(spannable);

        name = findViewById(R.id.editTextName);
        ICNumber = findViewById(R.id.editTextICNum);
        address = findViewById(R.id.editTextAddress);
        phoneNumber = findViewById(R.id.editTextPhoneNum);
        username = findViewById(R.id.editTextUsernameSignUp);
        password = findViewById(R.id.passwordfield);
        confirmPassword = findViewById(R.id.passwordConfirm);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Name cannot be empty.", Toast.LENGTH_SHORT).show();
                }

                else if(ICNumber.getText().toString().length() != 12) {
                    Toast.makeText(getApplicationContext(),"IC Number requires 12 digits.", Toast.LENGTH_SHORT).show();
                }

                else if(address.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Address cannot be empty.", Toast.LENGTH_SHORT).show();
                }

                else if(phoneNumber.getText().toString().length() != 10) {
                    Toast.makeText(getApplicationContext(),"Phone Number requires 10 digits.", Toast.LENGTH_SHORT).show();
                }

                else if(username.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Username cannot be empty.", Toast.LENGTH_SHORT).show();
                }

                else if(password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Password cannot be empty.", Toast.LENGTH_SHORT).show();
                }

                else if(!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Confirm password does not match with password.", Toast.LENGTH_SHORT).show();
                }

                else{
                    fnAddToREST();
                }


            }
        });

    }

    private void fnAddToREST() {

        String strURL = "http://" + GetIP.getInstance().getIp() + "/fyp/database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("anyText",response);

                    if (response.equals("Error! Username has been taken in Database"))
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
                   // }

                    else{

                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("respond");

                        if(success.equals("Sign Up Success!")){

                            Toast.makeText(getApplicationContext(), "Respond from server: " +
                                    jsonObject.getString("respond"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);


                        }

                    }




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

                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                String NAME = name.getText().toString();
                String ADDRESS = address.getText().toString();
                String PHONENUM = phoneNumber.getText().toString();
                String ICNUM = ICNumber.getText().toString();
                isRegister = false;


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnSignUp");
                params.put("Username", userName);
                params.put("Password", passWord);
                params.put("Name", NAME);
                params.put("Address", ADDRESS);
                params.put("PhoneNum", PHONENUM);
                params.put("ICNum", ICNUM);
                params.put("isRegister", String.valueOf(isRegister));

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }
}