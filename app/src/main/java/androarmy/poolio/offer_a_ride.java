package androarmy.poolio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class offer_a_ride extends Fragment {

    //public final String REGISTER_URL="http://192.168.1.101/poolio/register.php"; //Siddharth's pc
    public final String OFFER_URL="http://192.168.1.14:8080/poolio/offer.php";// Sumit's pc

    String[] locations ={"SRM Arch Gate","Abode Valley","Estancia","Backgate","Potheri Station","Guduvancheri"};//need to make it dynamic
    List<String> vehicleType = new ArrayList<String>(); //No need for dynamic i suppose
    public static Spinner spinner;
    AutoCompleteTextView actv,actv2;
    String source, destination, type,mobile,date,time,vname,vnumber;
    int availableSeats, amount=0;
    public int chargeable=1; //false for free ride
    SharedPreferences pref;
    EditText sourceET,destinationET, dateET, timeET,vnameET,vnumberET,availableET,amountET;
    static RadioGroup chargeableRG;
    Button offer_button;
    static int dayCheck;
    LinearLayout chargeLayout;
    static boolean timeCheck;// if timeCheck is false -> don't go to next screen


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.activity_offer_a_ride, container, false);

        spinner = (Spinner)v.findViewById(R.id.spin);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,locations);

        actv= (AutoCompleteTextView)v.findViewById(R.id.from);
        actv.setThreshold(1);
        actv.setAdapter(adapter);
        actv.setTextColor(Color.RED);
        actv2= (AutoCompleteTextView)v.findViewById(R.id.to);
        actv2.setThreshold(0);
        actv2.setAdapter(adapter);
        actv2.setTextColor(Color.RED);

        vehicleType.add("VEHICLE TYPE");
        vehicleType.add("Bike non-gear");
        vehicleType.add("Bike");
        vehicleType.add("Car");
        vehicleType.add("Auto");
        vehicleType.add("Cab");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_element, vehicleType);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        sourceET = (EditText)v.findViewById(R.id.from);
        destinationET=(EditText)v.findViewById(R.id.to);
        dateET=(EditText)v.findViewById(R.id.date);
        timeET=(EditText)v.findViewById(R.id.time);
        vnameET=(EditText)v.findViewById(R.id.vname);
        vnumberET=(EditText)v.findViewById(R.id.vnumber);
        availableET=(EditText)v.findViewById(R.id.passengers);
        amountET=(EditText)v.findViewById(R.id.money);
        chargeLayout = (LinearLayout)v.findViewById(R.id.layer_charge);
        chargeableRG=(RadioGroup)v.findViewById(R.id.radioGrp);
        chargeableRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radio_free)
                {
                    chargeLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Thanks for being so Generous",Toast.LENGTH_SHORT).show();
                    amount=0;
                    chargeable=0;
                }
                else
                {
                    chargeLayout.setVisibility(View.VISIBLE);
                    chargeable=1;
                }
            }
        });


        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dayCheck = day;
        timeCheck = false;
        if(day < 10 && (month+1) > 10)
            date = "0"+day+"/"+(month+1)+"/"+year;
        else if(day > 10 && (month+1) < 10)
            date = day+"/0"+(month+1)+"/"+year;
        else if(day < 10 && (month+1) < 10)
            date = "0"+day+"/0"+(month+1)+"/"+year;
        else
            date = day+"/"+(month+1)+"/"+year;
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if(minute < 10 && hour > 10)
            time = hour+":0"+minute;
        else if(minute > 10 && hour < 10)
            time = "0"+hour+":"+minute;
        else if(minute < 10 && hour < 10)
            time = "0"+hour+":0"+minute;
        else
            time = hour+":"+minute;

        timeET.setText(time);
        dateET.setText(date);





        offer_button = (Button)v.findViewById(R.id.btn_offer);
        offer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerRides();

            }
        });

        return v;

    }

    void offerRides()
    {
        pref = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        mobile = pref.getString("mobile", null);
        source=sourceET.getText().toString().trim();
        destination=destinationET.getText().toString().trim();
        type = spinner.getSelectedItem().toString();
        date = dateET.getText().toString();
        time=timeET.getText().toString();
        vname=vnameET.getText().toString();
        vnumber=vnumberET.getText().toString();
        availableSeats=Integer.parseInt(availableET.getText().toString());
        //int chargeable will be set automatically depending on radio button selected
        if(chargeableRG.getCheckedRadioButtonId()==R.id.radio_charge)
        {
            amount=Integer.parseInt(amountET.getText().toString());
        }

        //Toast.makeText(getContext(),"Mobile :"+mobile+"\n Source:"+source+"  Destination:"+destination+"\ntype:"+type
              //  +"\nSeats:"+availableSeats+"\n Amount:"+amount,Toast.LENGTH_LONG).show();

        offer(mobile,source,destination,type,date,time,vname,vnumber,availableSeats,chargeable,amount);
    }

    //11 parameters
    public void offer(String mobile,String source,String destination,String type,String date,String time, String vname, String vnumber, final int availableSeats, int chargeable,int amount){
        class OfferTheRide extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc=new RegisterUserClass();


            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Saving Your Details","Thanks for offering ride", true, true);
            }
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                if("".equals(s))
                {
                    s="Server error, Please try again after some time!";
                }
                else if("successfully registered".equalsIgnoreCase(s)){
                    Intent myIntent = new Intent(getContext(), Confirmation.class);//Need to make an activity saying your ride has been offered succesfully and will be displayed soon
                    startActivity(myIntent);
                    //getActivity().overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);
                    Toast.makeText(getContext(),"Ride offered",Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();


            }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("mobile",params[0]);
                data.put("source",params[1]);
                data.put("destination",params[2]);
                data.put("type",params[3]);
                data.put("date",params[4]);
                data.put("time",params[5]);
                data.put("vname",params[6]);
                data.put("vnumber",params[7]);
                data.put("availableSeats",params[8]);
                data.put("chargeable",params[9]);
                data.put("amount",params[10]);
                String result = ruc.sendPostRequest(OFFER_URL,data);
                //Log.i("@doinBackground:", result);
                return  result;

            }
        }
        OfferTheRide otr = new OfferTheRide();
        otr.execute(mobile,source,destination,type,date,time,vname,vnumber,Integer.toString(availableSeats),Integer.toString(chargeable),Integer.toString(amount));
    }


}
