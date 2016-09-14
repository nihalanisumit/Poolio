package androarmy.poolio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.AppController;


public class Messages extends Fragment {

    private String MESSAGE_URL = "http://www.poolio.in/pooqwerty123lio/messagefetch.php";
    SharedPreferences mSharedPreferences;
    RecyclerView recyclerView;
    private String jsonResponse;
    private static String TAG = Messages.class.getSimpleName();
    private TextView message;
    ProgressDialog loading;
    String[] id ,messages , timestamp ,mobile_book; // booked mobile no.
    String offerMobile;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        offerMobile = mSharedPreferences.getString("mobile", "null");
        makeMessageRequest();



    }

    public List<Data> fill_with_data(){

        List<Data> data  = new ArrayList<>();
        for (int i = 0 ; i<messages.length ; i++){
            if (messages[i]!=null) {
                data.add(new Data(messages[i],mobile_book[i],timestamp[i]));
            }

        }
        return data;

    }

    private void makeMessageRequest(){
        loading = ProgressDialog.show(getContext(),"Fetching Your messages","Please wait while we connect to our server",true,true);

       final  JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,MESSAGE_URL,offerMobile ,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            definearray(response.length());
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject c = (JSONObject) response
                                        .get(i);

                                messages[i] = c.getString("message");
                                mobile_book[i] = c.getString("mobile_book");
                                timestamp[i] = c.getString("timestamp");

                                List<Data> data = fill_with_data();
                                recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
                                final Recycler_View_Adapter_Message adapter  = new Recycler_View_Adapter_Message(data , getContext());
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));



                            }



                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                        loading.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                loading.dismiss();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void definearray(int len)
    {
        messages= new String[len];
        mobile_book = new String[len];

        timestamp=new String[len];
    }



}
