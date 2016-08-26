package androarmy.poolio;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by kjaganmohan on 17/07/16.
 */
public class Recycler_View_Adapter  extends RecyclerView.Adapter<Recycler_View_Adapter.View_Holder>  {

    List<Data> list = Collections.emptyList();
    Context context;
    private ItemClickListener clickListener;
    public View view;
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
        holder.vehicleNo=list.get(position).getVehicle_number();
// problem       mobile_number=list.get(position).getMobile();
//        vehicleName=list.get(position).getVehicle_name();
//        vehicleType=list.get(position).getType();
//        vehicleNo=list.get(position).getVehicle_number(); problem

//        holder.vehicle_name.setText(list.get(position).getVehicle_name());
//        holder.vehicle_number.setText(list.get(position).getVehicle_number());
//        holder.seats.setText(list.get(position).getSeats());



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
        public Button openDialog;
        public String vehicleName,vehicleNo,vehicleType,mobile_number;


        public View_Holder(final View view) {
            super(view);
            view.setOnClickListener(this);

            cv=(CardView) view.findViewById(R.id.cv);
            name=(TextView)view.findViewById(R.id.name_tv);
//            gender=(TextView)view.findViewById(R.id.gender_tv);
//            mobile=(TextView)view.findViewById(R.id.mobile);
            date=(TextView)view.findViewById(R.id.date_tv) ;
            time=(TextView)view.findViewById(R.id.time_tv);
            source=(TextView)view.findViewById(R.id.source_tv);
            destination=(TextView)view.findViewById(R.id.destination_tv);
            openDialog=(Button)view.findViewById(R.id.btn_book);
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

}
