package androarmy.poolio;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kjaganmohan on 17/07/16.
 */
public class Recycler_View_Adapter  extends RecyclerView.Adapter<Recycler_View_Adapter.View_Holder>  {
    public final String MESSAGE_URL="http://www.poolio.in/pooqwerty123lio/messages.php";//Sumit's pc

    List<Data> list = Collections.emptyList();
    Context context;
    private ItemClickListener clickListener;
    public View view;
    String message;
//    String mobile_number,vehicleType,vehicleName,vehicleNo,gender,seats;

    public Recycler_View_Adapter(final List<Data> list, Context context)
    {
        this.list = list;
        this.context = context;

    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_list_row, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }


    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
          Log.d("onbind","RVA");
//        holder.id.setText(list.get(position).getId());
        holder.name.setText(list.get(position).getFirst_name()+" "+ list.get(position).getLast_name());
//        holder.gender.setText(list.get(position).getGender());
//        holder.mobile.setText(list.get(position).getMobile());
        holder.source.setText(list.get(position).getSource());
        holder.destination.setText(list.get(position).getDestination());
//        holder.type.setText(list.get(position).getType());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());
        holder.vehicleName=list.get(position).getVehicle_name();
        holder.vehicleType=list.get(position).getType();
        holder.mobile_number=list.get(position).getMobile();

        Log.d("in onBindViewHolder",": mobile_number"+holder.mobile_number);
        holder.vehicleNo=list.get(position).getVehicle_number();
        holder.device_id=list.get(position).getDevice_id();
//        Log.d("onBindViewHolder","method called in RVA_Find");

        //button disable code:
         SharedPreferences mSharedPreferences = holder.v.getContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        final String mob = mSharedPreferences.getString("mobile", "null");
//        final String username = mSharedPreferences.getString("name" , "null");
//        final String gender = mSharedPreferences.getString("gender" , "null");
//        Log.d("book button: ",""+mobile_number+"~"+mob);
        if(mob.equals(holder.mobile_number))
        {   Log.d("book button: ","disabled"+mob);
            Log.d("disabled:",""+holder.mobile_number);
            holder.openDialog.setVisibility(View.GONE);
        }

    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);


    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;



    }
    public  class View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView id;
        public TextView name;
        public TextView mobile;
        public TextView source;
        public TextView destination;
        public TextView date;
        public TextView time;
        public CardView cv;
        public ImageView openDialog;
        public String vehicleName,vehicleNo,vehicleType,mobile_number,device_id;
        public View v;

        public View_Holder(final View view) {
            super(view);
            view.setOnClickListener(this);
            v=view;
             Log.d("constructor","RVA");
            cv=(CardView) view.findViewById(R.id.cv);
            name=(TextView)view.findViewById(R.id.name_tv);
//            gender=(TextView)view.findViewById(R.id.gender_tv);
//            mobile=(TextView)view.findViewById(R.id.mobile);
            date=(TextView)view.findViewById(R.id.date_tv) ;
            time=(TextView)view.findViewById(R.id.time_tv);
            source=(TextView)view.findViewById(R.id.source_tv);
            destination=(TextView)view.findViewById(R.id.destination_tv);
            openDialog=(ImageView) view.findViewById(R.id.btn_book);
            SharedPreferences mSharedPreferences = view.getContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
            final String mob = mSharedPreferences.getString("mobile", "null");
            final String username = mSharedPreferences.getString("name" , "null");
            final String gender = mSharedPreferences.getString("gender" , "null");
            openDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(view.getContext(), "Hello World", Toast.LENGTH_SHORT).show();
                    final Dialog dialog=new Dialog(view.getContext());
                    dialog.setContentView(R.layout.find_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView tv_name=(TextView)dialog.findViewById(R.id.driver_name);
                    TextView tv_number=(TextView)dialog.findViewById(R.id.driver_number);
                    TextView tv_location=(TextView)dialog.findViewById(R.id.driver_location);
                    TextView tv_wait=(TextView)dialog.findViewById(R.id.driver_waitingTime);
                    TextView tv_vehicleNo=(TextView)dialog.findViewById(R.id.vehicle_number);
                    TextView tv_vehicleName=(TextView)dialog.findViewById(R.id.vehicle_name);
                    TextView tv_vehicleColor=(TextView)dialog.findViewById(R.id.vehicle_color);
                    tv_name.setText(name.getText());
                    tv_number.setText(mobile_number);
                    tv_location.setText("TBA");
                    tv_wait.setText("TBA");
                    tv_vehicleNo.setText(vehicleNo);
                    tv_vehicleColor.setText("TBA");
                    tv_vehicleName.setText(vehicleName);
                    Button book=(Button)dialog.findViewById(R.id.book_button);
                    book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           // Toast.makeText(view.getContext(),device_id,Toast.LENGTH_SHORT).show();
                            String hh;
                            if(gender.equalsIgnoreCase("male"))
                            {
                                hh="him";
                            }
                            else if(gender.equalsIgnoreCase("female"))
                            {
                                hh="her";
                            }
                            else{
                                hh="him/her";
                            }

                            message= username +  " has booked your ride from "+source.getText().toString()+" to "+destination.getText().toString()+". Contact "+hh+" on " +mob;

                            saveMessage(view,message,mobile_number,username,mob);
                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en': '"  + message+"'  }, 'include_player_ids': ['" + device_id + "']}"),
                                        new OneSignal.PostNotificationResponseHandler() {
                                            @Override
                                            public void onSuccess(JSONObject response) {
                                                Log.i("OneSignalExample", "postNotification Success: " + response.toString());
                                            }

                                            @Override
                                            public void onFailure(JSONObject response) {
                                                Log.e("OneSignalExample", "postNotification Failure: " + response.toString());
                                            }

                                        });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialog.cancel();

                        }
                    });
                    Button cancel=(Button)dialog.findViewById(R.id.cancel_button);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            });

//            type=(TextView)view.findViewById(R.id.type_tv);
//            date=(TextView)view.findViewById(R.id.date_tv);
//            time=(TextView)view.findViewById(R.id.time_tv);
//            vehicle_name=(TextView)view.findViewById(R.id.vehiclename_tv);
//            vehicle_number=(TextView)view.findViewById(R.id.vehicleno_tv);
//            seats=(TextView)view.findViewById(R.id.seats_tv);

        }

        @Override
        public void onClick(View v) {
      //  clickListener.onClick(v, getAdapterPosition());
        }
    }

    void saveMessage(final View view, String message, String mobile, String name_book,String mobile_book)
    {
        //Toast.makeText(view.getContext(),"Message saved in db",Toast.LENGTH_SHORT).show();
        class saveMessageClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc=new RegisterUserClass();


            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Saving Your Details","Thanks for offering ride", true, true);
            }
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                if("".equals(s))
                {
                    s="Server error, Please try again after some time!";
                }
                else if("successfully saved".equalsIgnoreCase(s)){

                    Toast.makeText(view.getContext(),"Message Saved",Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(view.getContext(),s,Toast.LENGTH_LONG).show();


            }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("message",params[0]);
                data.put("mobile",params[1]);
                data.put("name_book",params[2]);
                data.put("mobile_book",params[3]);
                String result = ruc.sendPostRequest(MESSAGE_URL,data);
                //Log.i("@doinBackground:", result);
                return  result;

            }
        }
         saveMessageClass smc = new saveMessageClass();
         smc.execute(message,mobile,name_book,mobile_book);
    }

}


