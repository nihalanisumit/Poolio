package androarmy.poolio;

import android.content.Context;
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

        holder.id.setText(list.get(position).getId());
        holder.mobile.setText(list.get(position).getId());
        holder.source.setText(list.get(position).getId());
        holder.destination.setText(list.get(position).getId());
        holder.type.setText(list.get(position).getId());
        holder.date.setText(list.get(position).getId());
        holder.time.setText(list.get(position).getId());
        holder.vehicle_name.setText(list.get(position).getId());
        holder.vehicle_number.setText(list.get(position).getId());
        holder.seats.setText(list.get(position).getId());











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


        public View_Holder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v ,getAdapterPosition() );

        }
    }

}
