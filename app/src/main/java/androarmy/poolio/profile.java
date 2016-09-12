package androarmy.poolio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;


public class profile extends android.support.v4.app.Fragment {

    SharedPreferences mSharedPreferences;
    String mobile, name, gender, email, vehicle_name, vehicle_number, driving_license;
    TextView nameET, genderET;
    EditText mobileET, emailET, vehicle_nameET, vehicle_numberET, driving_licenseET;
    ImageView vehicle_nameIV, vehicle_numberIV, dlIV;
    Button add_button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        mSharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        mobile = mSharedPreferences.getString("mobile", "null");
        name = mSharedPreferences.getString("name", "name");
        gender = mSharedPreferences.getString("gender", "gender");
        email = mSharedPreferences.getString("email", "email");
        vehicle_name = mSharedPreferences.getString("vehicle_name", "vehicle_name");
        vehicle_number = mSharedPreferences.getString("vehicle_number", "vehicle_number");
        driving_license = mSharedPreferences.getString("driving_license", "driving_license");
        //Toast.makeText(getContext(),mobile,Toast.LENGTH_LONG).show();
        mobileET = (EditText) v.findViewById(R.id.mobileTV);
        nameET = (TextView) v.findViewById(R.id.user_profile_name);
        genderET = (TextView) v.findViewById(R.id.gender);
        emailET = (EditText) v.findViewById(R.id.email);
        vehicle_nameET = (EditText) v.findViewById(R.id.v_name);
        vehicle_numberET = (EditText) v.findViewById(R.id.v_no);
        driving_licenseET = (EditText) v.findViewById(R.id.dl);
        vehicle_nameIV = (ImageView) v.findViewById(R.id.vnplus);
        vehicle_numberIV = (ImageView) v.findViewById(R.id.vnoplus);
        dlIV = (ImageView) v.findViewById(R.id.dlplus);
        add_button = (Button)v.findViewById(R.id.add_button);

        mobileET.setText(mobile);
        nameET.setText(name);
        genderET.setText(gender);
        emailET.setText(email);
        if (vehicle_number.equals("null")) {
            vehicle_numberET.setText("Add your vehicle number");
            vehicle_numberIV.setVisibility(View.VISIBLE);
            vehicle_numberIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDetails();
                }
            });
        }
        if (vehicle_name.equals("null")) {
            vehicle_nameET.setText("Add your vehicle number");
            vehicle_nameIV.setVisibility(View.VISIBLE);
            vehicle_numberIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDetails();
                }
            });
        }
        if (driving_license.equals("null")) {
            driving_licenseET.setText("Add your license number");
            dlIV.setVisibility(View.VISIBLE);
            vehicle_numberIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDetails();
                }
            });

        }

        return v;
    }


    void addDetails() {
        mobileET.setEnabled(false);
        emailET.setEnabled(false);
        add_button.setVisibility(View.VISIBLE);
        vehicle_numberIV.setVisibility(View.GONE);
        vehicle_nameIV.setVisibility(View.GONE);
        dlIV.setVisibility(View.GONE);
        vehicle_nameET.setText("");
        vehicle_nameET.setHint("Vehicle Name");
        vehicle_nameET.setEnabled(true);
        vehicle_numberET.setText("");
        vehicle_numberET.setHint("Vehicle Number");
        vehicle_numberET.setEnabled(true);
        driving_licenseET.setText("");
        driving_licenseET.setHint("Driving License Number");
        driving_licenseET.setEnabled(true);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDetailsToDB(mobile);
            }
        });
    }

    void addDetailsToDB(String mobile)
    {
        String v_name = vehicle_nameET.getText().toString();
        String v_number = vehicle_numberET.getText().toString();
        String dl = driving_licenseET.getText().toString();

        if(v_name=="" || v_number=="" || dl=="")
        {
            Toast.makeText(getContext(),"One or more fields are emplty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(),v_name+" "+v_number+" "+dl,Toast.LENGTH_SHORT).show();
        }
    }
}
