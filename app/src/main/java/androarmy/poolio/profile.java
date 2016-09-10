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
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;


public class profile extends android.support.v4.app.Fragment {

    SharedPreferences mSharedPreferences;
    String mobile,name,gender,email,vehicle_name,vehicle_number,driving_license;
    TextView mobileET,nameET,genderET,emailET,vehicle_nameET,vehicle_numberET,driving_licenseET;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        mSharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        mobile = mSharedPreferences.getString("mobile", "null");
        name=mSharedPreferences.getString("name","name");
        gender=mSharedPreferences.getString("gender","gender");
        email=mSharedPreferences.getString("email","email");
        vehicle_name=mSharedPreferences.getString("vehicle_name","vehicle_name");
        vehicle_number=mSharedPreferences.getString("vehicle_number","vehicle_number");
        driving_license=mSharedPreferences.getString("driving_license","driving_license");
        //Toast.makeText(getContext(),mobile,Toast.LENGTH_LONG).show();
        mobileET = (TextView) v.findViewById(R.id.mobileTV);
        nameET = (TextView) v.findViewById(R.id.user_profile_name);
        genderET = (TextView) v.findViewById(R.id.gender);
        emailET = (TextView) v.findViewById(R.id.email);
        vehicle_nameET = (TextView) v.findViewById(R.id.v_name);
        vehicle_numberET = (TextView) v.findViewById(R.id.v_no);
        driving_licenseET = (TextView) v.findViewById(R.id.dl);

        mobileET.setText(mobile);
        nameET.setText(name);
        genderET.setText(gender);
        emailET.setText(email);
        vehicle_numberET.setText(vehicle_number);
        vehicle_nameET.setText(vehicle_name);
        driving_licenseET.setText(driving_license);



        return v;
    }


}
