package androarmy.poolio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class myRides extends android.support.v4.app.Fragment {
    public final String FIND_URL="http://192.168.1.14:8080/poolio/myrides.php";//Sumit's pc
    SharedPreferences mSharedPreferences;
    String mobile;
    String [] id,source, destination, type, date, time, vehicle_name,vehicle_number, seats;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        mobile = mSharedPreferences.getString("mobile", "null");
        //Toast.makeText(getContext(),mobile,Toast.LENGTH_LONG).show();

        fetchMyRides(mobile);

        return inflater.inflate(R.layout.fragment_my_rides, container, false);
    }

    private void fetchMyRides(final String mobile){
        class fetchRideClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Fetching Your Rides","Please wait while we connect to our server",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray("result");
                    definearray(result.length());
                    for (int i = 0 ; i<result.length() ; i++) {
                        JSONObject c = result.getJSONObject(i);

                        id[i]= c.getString("id");
                        source [i]= c.getString("source");
                        destination [i] = c.getString("destination");
                        type [i]= c.getString("type");
                        date  [i]= c.getString("date");
                        time [i] = c.getString("time");
                        vehicle_name [i] = c.getString("vehicle_name");
                        vehicle_number [i] = c.getString("vehicle_number");
                        seats [i]= c.getString("seats");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("mobile",params[0]);

                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(FIND_URL,data);
                return result;
            }
        }
        fetchRideClass frc = new fetchRideClass();
        frc.execute(mobile);
    }

    void definearray(int len)
    {
        id= new String[len];
        source= new String[len];
        destination= new String[len];
        type= new String[len];
        date= new String[len];
        time= new String[len];
        vehicle_name= new String[len];
        vehicle_number= new String[len];
        seats= new String[len];
    }








}
