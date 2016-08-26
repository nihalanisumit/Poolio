package androarmy.poolio;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by kjaganmohan on 17/07/16.
 */
public class Recycler_View_Adapter2 extends RecyclerView.Adapter<Recycler_View_Adapter2.View_Holder>  {

    List<Data> list = Collections.emptyList();
    Context context;
    private ItemClickListener clickListener;
    public View view;
//    String mobile_number,vehicleType,vehicleName,vehicleNo,gender,seats;

    public Recycler_View_Adapter2(final List<Data> list, Context context)
    {
        this.list = list;
        this.context = context;

    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myride_list_row, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }


    @Override
    public void onBindViewHolder(View_Holder holder, int position) {

//        holder.id.setText(list.get(position).getId());
      //  holder.name.setText(list.get(position).getFirst_name()+" "+ list.get(position).getLast_name());
        holder.id.setText(list.get(position).getid());
        holder.source.append(list.get(position).getSource());
        holder.destination.append(list.get(position).getDestination());
        holder.time.append(list.get(position).getDate()+" "+list.get(position).getDate());
        //holder.timestamp.setText(""); timestamp need to be fetched and value need to be converted in app format





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

        public TextView id,source,destination,time,timestamp;
        public CardView cv;
        public Button button_cancel;


        public View_Holder(final View view) {
            super(view);
            view.setOnClickListener(this);

            cv=(CardView) view.findViewById(R.id.cv);
            id=(TextView)view.findViewById(R.id.ride_id);
            source=(TextView)view.findViewById(R.id.source);
            destination=(TextView)view.findViewById(R.id.destination);
            time=(TextView)view.findViewById(R.id.time);
            timestamp=(TextView)view.findViewById(R.id.timestamp);
        }

        @Override
        public void onClick(View v) {
      //  clickListener.onClick(v, getAdapterPosition());
        }
    }

}
