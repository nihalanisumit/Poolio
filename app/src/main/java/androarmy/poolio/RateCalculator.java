package androarmy.poolio;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


public class RateCalculator extends Fragment {


    public RateCalculator() {

    }

    String[] locations ={"SRM Arch Gate","Abode Valley","Estancia","SRM Backgate","Potheri Station","Green Pearl","Safa", "Akshaya","Airport","Central Station","Egmore Station"};//need to make it dynamic
    AutoCompleteTextView sourceTV, destnationTV;
    String source,destination;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_rate_calculator, container, false);
        sourceTV=(AutoCompleteTextView)v.findViewById(R.id.sourcetv);
        destnationTV=(AutoCompleteTextView)v.findViewById(R.id.destinationtv);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,locations);
        sourceTV.setThreshold(1);
        sourceTV.setAdapter(adapter);
        sourceTV.setTextColor(Color.RED);
        destnationTV.setThreshold(1);
        destnationTV.setAdapter(adapter);
        destnationTV.setTextColor(Color.RED);



        destnationTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                source=sourceTV.getText().toString();
                destination=destnationTV.getText().toString();
                if("".equals(source))
                    return;
                getRate(source,destination);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sourceTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                source=sourceTV.getText().toString();
                destination=destnationTV.getText().toString();
                if("".equals(destination))
                    return;
                getRate(source,destination);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    void getRate(String source, String destination)
    {
        //add code here
    }

}
