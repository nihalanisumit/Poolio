package androarmy.poolio;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class offer_a_ride extends Fragment {
    String[] locations ={"SRM Arch Gate","Abode Valley","Estancia","Backgate","Potheri Station","Guduvancheri"};//need to make it dynamic
    List<String> vehicleType = new ArrayList<String>(); //No need for dynamic i suppose
    Spinner spinner;
    AutoCompleteTextView actv,actv2;

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




        return v;


    }
}
