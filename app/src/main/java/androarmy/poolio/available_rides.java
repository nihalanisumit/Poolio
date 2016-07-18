package androarmy.poolio;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class available_rides extends AppCompatActivity {
    String [] id,mobile, source, destination, type, date, time, vehicle_name,vehicle_number, seats;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rides);

        Intent intent = getIntent();
        String json= intent.getStringExtra("json");
        showRides(json);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        List<Data> data = fill_with_data();

        final Recycler_View_Adapter adapter  = new Recycler_View_Adapter(data , getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

        });


    }
    public List<Data>fill_with_data(){

        List<Data> data  = new ArrayList<>();
        for (int i = 0 ; i<id.length ; i++){
            if (id[i]!=null) {
                data.add(new Data(id[i], mobile[i], source[i], destination[i], type[i], date[i], time[i], vehicle_name[i], vehicle_number[i], seats[i]));
            }

        }
        return data;

    }

    private void showRides(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            for (int i = 0 ; i<result.length() ; i++) {
                JSONObject c = result.getJSONObject(i);


                id[i]= c.getString("id");
                mobile[i] = c.getString("mobile");
                source [i]= c.getString("source");
                destination [i] = c.getString("destination");
                type [i]= c.getString("type");
                date  [i]= c.getString("date");
                time [i] = c.getString("time");
                vehicle_name [i] = c.getString("vehicle_name");
                vehicle_number [i] = c.getString("vehicle_number");
                seats [i]= c.getString("seats");

                Log.d("ID:::",id[i]);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
