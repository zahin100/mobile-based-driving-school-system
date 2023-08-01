package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    TextView txtGreetAdmin;
    public CardView cardViewPostAnnouncements,cardViewSalesReport;
    Button btnLogOutAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Admin");

        txtGreetAdmin = findViewById(R.id.txtGreetAdmin);
        cardViewPostAnnouncements = findViewById(R.id.cardViewPostAnnouncements);
        cardViewSalesReport = findViewById(R.id.cardViewSalesReport);
        btnLogOutAdmin = findViewById(R.id.btnLogOutAdmin);


        String arr[] = Staff.getInstance().getName().split(" ");
        String name1 = arr[0];
        txtGreetAdmin.setText("Hello, " + name1 + "!");

        cardViewPostAnnouncements.setOnClickListener(this);
        cardViewSalesReport.setOnClickListener(this);

        btnLogOutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(AdminActivity.this).setIcon(R.drawable.baseline_logout_24)
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

        if(v.getId() == R.id.cardViewPostAnnouncements){
            replaceFragment(new NewsFragment());
        }

        else if(v.getId() == R.id.cardViewSalesReport) {

            showMenu(v);

        }
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutAdmin, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {


            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
            startActivity(intent);


    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) AdminActivity.this);
        popup.inflate(R.menu.sales_report_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

            if(item.getItemId() == R.id.weeklySalesReportMenu){
                Intent intent = new Intent(getApplicationContext(), WeeklySalesReportActivity.class);
                startActivity(intent);
                return true;
            }

            else if(item.getItemId() == R.id.monthlySalesReportMenu) {
                Intent intent = new Intent(getApplicationContext(), MonthlySalesReportActivity.class);
                startActivity(intent);
                return true;
            }

            else if(item.getItemId() == R.id.yearlySalesReportMenu) {
                Intent intent = new Intent(getApplicationContext(), YearlySalesReportActivity.class);
                startActivity(intent);
                return true;
            }

            else{
                return false;
            }
    }
}