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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class profile extends android.support.v4.app.Fragment {
    public final String PROFILE_URL ="http://192.168.1.13/poolio/profile.php";//Sumit's pc
    SharedPreferences mSharedPreferences;
    String mobile,first_name,last_name,gender,email,vehicle_name,vehicle_number,driving_license;
    EditText mobileET,nameET,genderET,emailET,vehicle_nameET,vehicle_numberET,driving_licenseET;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        mobile = mSharedPreferences.getString("mobile", "null");
        //Toast.makeText(getContext(),mobile,Toast.LENGTH_LONG).show();
//        mobileET = (EditText) getView().findViewById(R.id.mobile);
//        nameET = (EditText) getView().findViewById(R.id.name);
//        genderET = (EditText) getView().findViewById(R.id.gender);
//        emailET = (EditText) getView().findViewById(R.id.email);
//        vehicle_nameET = (EditText) getView().findViewById(R.id.vehicle_name);
//        vehicle_numberET = (EditText) getView().findViewById(R.id.vehicle_number);
//        driving_licenseET = (EditText) getView().findViewById(R.id.driving_license);

        fetchMyRides(mobile);

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void fetchMyRides(final String mobile){
        class fetchRideClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Profile","Please wait while we connect to our server",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject c = result.getJSONObject(0);

                    first_name=c.getString("first_name");
                    last_name=c.getString("last_name");
                    gender=c.getString("gender");
                    email=c.getString("email");
                    vehicle_name=c.getString("vehicle_name");
                    vehicle_number=c.getString("vehicle_number");
                    driving_license=c.getString("driving_license");

//                    mobileET.setText(mobile);
//                    nameET.setText(first_name+" "+last_name);
//                    genderET.setText(gender);
//                    emailET.setText(email);
//                    vehicle_numberET.setText(vehicle_number);
//                    vehicle_nameET.setText(vehicle_name);
//                    driving_licenseET.setText(driving_license);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("mobile",params[0]);

                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(PROFILE_URL,data);
                return result;
            }
        }
        fetchRideClass frc = new fetchRideClass();
        frc.execute(mobile);
    }










}
