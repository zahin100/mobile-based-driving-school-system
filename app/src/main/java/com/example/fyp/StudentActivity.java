package com.example.fyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.example.fyp.databinding.ActivityStudentBinding;

public class StudentActivity extends AppCompatActivity {

    ActivityStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Student.getInstance().isStudent() == true){

            // Set Title Text and Bar Colour
            ActionBar aBar;
            aBar = getSupportActionBar();
            ColorDrawable cd = new ColorDrawable(Color.parseColor("#000080"));
            aBar.setBackgroundDrawable(cd);
            int white = Color.parseColor("white");
            Spannable spannable = new SpannableString("Student");
            spannable.setSpan(new ForegroundColorSpan(white), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            aBar.setTitle(spannable);

        }


        binding.bottomNavigationView.setSelectedItemId(R.id.navigation_scheduleClass);
        if(Student.getInstance().getIsRegister() == false) {
            replaceFragment(new RegisterLicenseFragment());
        }
        else {replaceFragment(new ScheduleClassFragment());}

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {


            if (item.getItemId() == R.id.navigation_news){
                replaceFragment(new NewsFragment());
            }

            else if (item.getItemId() == R.id.navigation_scheduleClass){

                if(Student.getInstance().getIsRegister() == false) {
                    replaceFragment(new RegisterLicenseFragment());
                }
                else {replaceFragment(new ScheduleClassFragment());}
            }

            else if (item.getItemId() == R.id.navigation_profile){
                replaceFragment(new ProfileFragment());
            }


            return true;

        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}