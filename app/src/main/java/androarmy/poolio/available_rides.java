package androarmy.poolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class available_rides extends AppCompatActivity {
    String id,mobile, source, destination, type, date, time, vehicle_name,vehicle_number, seats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rides);

        Intent intent = getIntent();
        String json= intent.getStringExtra("json");
        showRides(json);


    }

    private void showRides(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);


            id = c.getString("id");
            mobile = c.getString("mobile");
            source = c.getString("source");
            destination = c.getString("destination");
            type = c.getString("type");
            date = c.getString("date");
            time = c.getString("time");
            vehicle_name = c.getString("vehicle_name");
            vehicle_number = c.getString("vehicle_number");
            seats = c.getString("seats");
            Toast.makeText(getApplicationContext(),id+mobile+source+destination,Toast.LENGTH_LONG).show();







        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
