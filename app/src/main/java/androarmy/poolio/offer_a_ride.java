package androarmy.poolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import android.widget.Toolbar;
import android.app.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class offer_a_ride extends Fragment {
    String[] locations ={"SRM Arch Gate","Abode Valley","Estancia","Backgate","Potheri Station","Guduvancheri"};//need to make it dynamic
    List<String> vehicleType = new ArrayList<String>(); //No need for dynamic i suppose
    public static Spinner spinner;
    AutoCompleteTextView actv,actv2;
    String source, destination, type,mobile,date,time,vname,vnumber;
    int availableSeats, amount=0;
    public boolean chargeable=true; //false for free ride
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
                    chargeable=false;
                }
                else
                {
                    chargeLayout.setVisibility(View.VISIBLE);
                    chargeable=true;
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
        //boolean chargeable will be set automatically depending on radio button selected
        if(chargeableRG.getCheckedRadioButtonId()==R.id.radio_charge)
        {
            amount=Integer.parseInt(amountET.getText().toString());
        }

        //Toast.makeText(getContext(),"Mobile :"+mobile+"\n Source:"+source+"  Destination:"+destination+"\ntype:"+type
              //  +"\nSeats:"+availableSeats+"\n Amount:"+amount,Toast.LENGTH_LONG).show();




    }


}
