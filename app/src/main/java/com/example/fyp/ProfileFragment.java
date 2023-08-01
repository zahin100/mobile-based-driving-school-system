package com.example.fyp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    TextView name, icNumber, address, phoneNum, licenceType, expiryDate;
    Button btnLogOut;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_profile, container, false);

        name = v.findViewById(R.id.textViewName);
        icNumber = v.findViewById(R.id.textViewICNumber);
        address = v.findViewById(R.id.textViewAddress);
        phoneNum = v.findViewById(R.id.textViewPhoneNum);
        licenceType = v.findViewById(R.id.textViewLicenceType);
        expiryDate = v.findViewById(R.id.textViewExpiryDate);
        btnLogOut = v.findViewById(R.id.btnLogOut);

        name.setText(Student.getInstance().getName());
        icNumber.setText("IC Number: " + Student.getInstance().getICNum());
        address.setText("Address: " + Student.getInstance().getAddress());
        phoneNum.setText("Phone Number: " + Student.getInstance().getPhoneNum());

        if(Student.getInstance().getLicence().getLicenceID() != 0){
            licenceType.setText("Licence Type: " + Student.getInstance().getLicence().getType());
            expiryDate.setText("Expiry Date: " + Student.getInstance().getExpiryDate().toString());
        }

        else {
            licenceType.setText("Licence Type: - ");
            expiryDate.setText("Expiry Date: - ");
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new AlertDialog.Builder(getContext()).setIcon(R.drawable.baseline_logout_24)
                            .setTitle("Log Out Confirmation").setMessage("Are you sure you want to log out?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(getContext(), "Successfully logged out.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("No", null).show();
                }

        });

        return v;
    }


}