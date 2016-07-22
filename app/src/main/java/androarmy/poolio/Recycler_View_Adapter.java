package androarmy.poolio;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by kjaganmohan on 17/07/16.
 */
public class Recycler_View_Adapter  extends RecyclerView.Adapter<Recycler_View_Adapter.View_Holder>  {

    List<Data> list = Collections.emptyList();
    Context context;
    private ItemClickListener clickListener;

    public Recycler_View_Adapter(List<Data> list, Context context) {
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
        holder.mobile.setText(list.get(position).getMobile());
        holder.source.setText(list.get(position).getSource());
        holder.destination.setText(list.get(position).getDestination());
        holder.type.setText(list.get(position).getType());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());
        holder.vehicle_name.setText(list.get(position).getVehicle_name());
        holder.vehicle_number.setText(list.get(position).getVehicle_number());
        holder.seats.setText(list.get(position).getSeats());











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
        public TextView mobile;
        public TextView source;
        public TextView destination;
        public TextView type;
        public TextView date;
        public TextView time;
        public TextView vehicle_name;
        public TextView vehicle_number;
        public TextView seats;
        public CardView cv;


        public View_Holder(View view) {
            super(view);
            cv=(CardView) view.findViewById(R.id.cv);
            mobile=(TextView)view.findViewById(R.id.mobile);
            source=(TextView)view.findViewById(R.id.source_tv);
            destination=(TextView)view.findViewById(R.id.destination_tv);
            type=(TextView)view.findViewById(R.id.type_tv);
            date=(TextView)view.findViewById(R.id.date_tv);
            time=(TextView)view.findViewById(R.id.time_tv);
            vehicle_name=(TextView)view.findViewById(R.id.vehiclename_tv);
            vehicle_number=(TextView)view.findViewById(R.id.vehicleno_tv);
            seats=(TextView)view.findViewById(R.id.seats_tv);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v ,getAdapterPosition() );

        }
    }

}
