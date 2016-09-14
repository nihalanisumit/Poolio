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
 * Created by kjaganmohan on 14/09/16.
 */
public class Recycler_View_Adapter_Message extends RecyclerView.Adapter<Recycler_View_Adapter_Message.View_Holder> {


    List<Data> list = Collections.emptyList();
    Context context;
    private ItemClickListener clickListener;
    public View view;

    public Recycler_View_Adapter_Message(final List<Data> list, Context context)
    {
        this.list = list;
        this.context = context;

    }



    @Override
    public Recycler_View_Adapter_Message.View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Recycler_View_Adapter_Message.View_Holder holder, int position) {

        holder.message.setText(list.get(position).getMessage());
        holder.timestamp.setText(list.get(position).getTimestamp());


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  class View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView message , timestamp;
        CardView cv;

        public View_Holder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            timestamp=(TextView)view.findViewById(R.id.timestamp);
            message = (TextView)view.findViewById(R.id.message);



        }

        @Override
        public void onClick(View v) {

        }
    }
     }
