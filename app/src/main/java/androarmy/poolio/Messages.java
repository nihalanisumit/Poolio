package androarmy.poolio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import java.util.HashMap;
import java.util.List;

import app.AppController;


public class Messages extends Fragment {
    View view;

    private String MESSAGE_URL = "http://www.poolio.in/pooqwerty123lio/messagefetch.php";
    SharedPreferences mSharedPreferences;
    RecyclerView recyclerView;
    private String jsonResponse;
    private static String TAG = Messages.class.getSimpleName();
    private TextView message;
    ProgressDialog loading;
    String[] id ,messages , timestamp ,mobile_book; // booked mobile no.
    String mobile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_messages, container, false);
        // Inflate the layout for this fragment

        mSharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        mobile = mSharedPreferences.getString("mobile", "null");
        fetchMessages(mobile);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




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

    private void fetchMessages(final String mobile){
        class fetchMessageClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Fetching Your messages","Please wait while we connect to our server",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    // Parsing json array response
                    // loop through each json object
                    jsonResponse = "";
                    Log.d("***JSON***",s);

                    JSONObject job =  new JSONObject(s);
                    JSONArray result = job.getJSONArray("result");
                    definearray(result.length());
                    for (int i = 0; i < result.length(); i++)
                    {

                        JSONObject c = result.getJSONObject(i);

                        messages[i] = c.getString("message");
                        mobile_book[i] = c.getString("mobile_book");
                        timestamp[i] = c.getString("timestamp");
                    }

                        List<Data> data = fill_with_data();
                        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
                        final Recycler_View_Adapter_Message adapter  = new Recycler_View_Adapter_Message(data , getContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));







                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("mobile",params[0]);

                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(MESSAGE_URL,data);
                return result;
            }
        }
        fetchMessageClass fmc = new fetchMessageClass();
        fmc.execute(mobile);
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
