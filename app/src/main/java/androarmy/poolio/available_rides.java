package androarmy.poolio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class available_rides extends AppCompatActivity {
    String [] id,mobile, source, destination, type, date, time, vehicle_name,vehicle_number, seats, first_name, last_name, gender,device_id,msg;
    RecyclerView recyclerView;
//    SwipeRefreshLayout mSwipeRefreshLayout;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    public final String FIND_URL="http://www.poolio.in/pooqwerty123lio/find.php";//Sumit's pc
    int refreshing=0;
    AVLoadingIndicatorView  avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rides);
        Intent intent = getIntent();
        final String pickup= intent.getStringExtra("pickup");
        String drop= intent.getStringExtra("drop");
        final String date= intent.getStringExtra("date");
        final String time= intent.getStringExtra("time");
       // Log.i("**PREVIOUS ACTIVITY**",pickup+" "+drop);
         avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
        findRide(pickup,date,time);
        //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();

      //  mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                //Log.d("**REFRESHING**","reffreshing");
                refreshing=1;
                findRide(pickup,date,time);



            }
        });
    }
    public List<Data>fill_with_data(){

        List<Data> data  = new ArrayList<>();
        for (int i = 0 ; i<id.length ; i++){
            if (id[i]!=null) {
                //Log.e("**CHECKING**",source[0]+" "+ destination[0]+vehicle_name[0]);
                data.add(new Data(id[i],first_name[i] ,last_name[i],mobile[i],gender[i],source[i], destination[i],type[i],date[i],time[i],vehicle_name[i],vehicle_number[i],seats[i],device_id[i],msg[i]));
            }

        }
        return data;

    }
    void findRide(String pickup,String date, String time)
    {
        if(!InternetConnectionClass.isConnected(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_LONG).show();
            return;
        }
        else
            fetchRides(pickup,date,time);



    }

    private void fetchRides(final String pickup,final String date, final String time){
        class fetchRideClass extends AsyncTask<String,Void,String> {
//            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if(refreshing!=1)
                {
//                    loading = ProgressDialog.show(available_rides.this,"Finding Your Rides" ,"Please wait while we connect to server",true,true);
                  avi.show();
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(refreshing!=1)
                {
                      avi.hide();
//                    loading.dismiss();

                }

                mWaveSwipeRefreshLayout.setRefreshing(false);

                //Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                //Log.i("***JSON***",s);
                showRides(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("pickup",params[0]);
                data.put("date",params[1]);
                data.put("time",params[2]);

                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(FIND_URL,data);
                return result;
            }
        }
        fetchRideClass frc = new fetchRideClass();
        frc.execute(pickup,date,time);
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
                device_id[i]=c.getString("device_id");
                msg[i]=c.getString("msg");
                //Toast.makeText(getApplicationContext(),id[i]+mobile[i],Toast.LENGTH_SHORT).show();
            }
            List<Data> data = fill_with_data();
            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            final Recycler_View_Adapter adapter  = new Recycler_View_Adapter(data , getApplication());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));

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
        device_id=new String[len];
        msg=new String[len];


    }
}
