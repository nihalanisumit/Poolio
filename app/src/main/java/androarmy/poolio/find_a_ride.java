package androarmy.poolio;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class find_a_ride extends Fragment {
    String[] locations ={"SRM Arch Gate","Abode Valley","Estancia","SRM Backgate","Potheri Station","Green Pearl","Safa Guduvanchery", "Akshaya Guduvanchery"};//need to make it dynamic
    AutoCompleteTextView actv,actv2;
    EditText dateET, timeET;
    Button b;
    String pickup, drop, time, date;
    public final String FIND_URL="http://192.168.1.13/poolio/find.php";//Sumit's pc
    private ImageView dateIv, timeIV;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.activity_find_a_ride, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,locations);

        actv= (AutoCompleteTextView)v.findViewById(R.id.pickup);
        actv.setThreshold(1);
        actv.setAdapter(adapter);
        actv.setTextColor(Color.RED);
        actv2= (AutoCompleteTextView)v.findViewById(R.id.drop);
        actv2.setThreshold(0);
        actv2.setAdapter(adapter);
        actv2.setTextColor(Color.RED);

        dateET = (EditText)v.findViewById(R.id.date);
        timeET = (EditText)v.findViewById(R.id.time);
        b=(Button) v.findViewById(R.id.btn_find);
        dateIv = (ImageView) v.findViewById(R.id.dateIv);
        timeIV = (ImageView) v.findViewById(R.id.img_time);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnectionClass.isConnected(getActivity())){
                    Toast.makeText(getActivity(), "Please connect to the internet!", Toast.LENGTH_LONG).show();
                    return;
                }
                pickup = actv.getText().toString();
                drop= actv2.getText().toString();
                date= dateET.getText().toString();
                time= timeET.getText().toString();
                findRide(pickup);
            }
        });

        dateIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnectionClass.isConnected(getActivity())){
                    Toast.makeText(getActivity(), "Please connect to the internet!", Toast.LENGTH_LONG).show();
                    return;
                }
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext() , new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateET.setText(year + "-" + (monthOfYear + 1)+"-"+dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }

        });
        timeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnectionClass.isConnected(getActivity())){
                    Toast.makeText(getActivity(), "Please connect to the internet!", Toast.LENGTH_LONG).show();
                    return;
                }
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeET.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });
        return v;
    }

    void findRide(String pickup)
    {
        if(!InternetConnectionClass.isConnected(getActivity())){
            Toast.makeText(getActivity(), "Please connect to the internet!", Toast.LENGTH_LONG).show();
            return;
        }

        fetchRides(pickup);

    }

    private void fetchRides(final String pickup){
        class fetchRideClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Finding Your Rides","Please wait while we connect to server",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                //Log.i("***JSON***",s);
                Intent i = new Intent(getActivity(),available_rides.class);
                i.putExtra("json",s);
                startActivity(i);









            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("pickup",params[0]);

                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(FIND_URL,data);
                return result;
            }
        }
        fetchRideClass frc = new fetchRideClass();
        frc.execute(pickup);
    }


}
