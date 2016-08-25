package androarmy.poolio;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class available_rides extends AppCompatActivity {
    String [] id,mobile, source, destination, type, date, time, vehicle_name,vehicle_number, seats, first_name, last_name, gender;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rides);

        Intent intent = getIntent();
        String json= intent.getStringExtra("json");
        //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
        showRides(json);

        List<Data> data = fill_with_data();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);


        final Recycler_View_Adapter adapter  = new Recycler_View_Adapter(data , getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));



//        adapter.setClickListener(new ItemClickListener() {
//
//            @Override
//            public void onClick(View view, int position) {
//                Log.d("odjj","hdjd");
//
//
//                Intent intent1 = new Intent(getApplicationContext(),details_ride.class);
//                //intent1.putExtra("mobile",mobile[position]);
//                startActivity(intent1);
//
//            }
//
//        });


    }
    public List<Data>fill_with_data(){

        List<Data> data  = new ArrayList<>();
        for (int i = 0 ; i<id.length ; i++){
            if (id[i]!=null) {
                //Log.e("**CHECKING**",source[0]+" "+ destination[0]+vehicle_name[0]);
                data.add(new Data(id[i],first_name[i] ,  source[i], destination[i]));
            }

        }
        return data;

    }

    private void showRides(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            definearray(result.length());
            for (int i = 0 ; i<result.length() ; i++) {
                JSONObject c = result.getJSONObject(i);

                id[i]= c.getString("id");
                first_name[i]= c.getString("first_name");
                last_name[i]= c.getString("last_name");
                gender[i]= c.getString("gender");
                mobile[i] = c.getString("mobile");
                source [i]= c.getString("source");
                destination [i] = c.getString("destination");
                type [i]= c.getString("type");
                date  [i]= c.getString("date");
                time [i] = c.getString("time");
                vehicle_name [i] = c.getString("vehicle_name");
                vehicle_number [i] = c.getString("vehicle_number");
                seats [i]= c.getString("seats");
                //Toast.makeText(getApplicationContext(),id[i]+mobile[i],Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void definearray(int len)
    {
        id= new String[len];
        mobile= new String[len];
        source= new String[len];
        destination= new String[len];
        type= new String[len];
        date= new String[len];
        time= new String[len];
        vehicle_name= new String[len];
        vehicle_number= new String[len];
        seats= new String[len];
        first_name= new String[len];
        last_name= new String[len];
        gender= new String[len];


    }
}
