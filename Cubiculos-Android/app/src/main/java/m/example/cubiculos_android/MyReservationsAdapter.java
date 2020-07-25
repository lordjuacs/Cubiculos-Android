package m.example.cubiculos_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyReservationsAdapter extends RecyclerView.Adapter<MyReservationsAdapter.ViewHolder>{
    public JSONArray reservations;
    private Context context;

    public MyReservationsAdapter(JSONArray reservations, Context context){
        this.reservations = reservations;
        this.context = context;
    }

    public MyReservationsAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_view2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            final JSONObject reservation = reservations.getJSONObject(position);
            String room = reservation.getString("room");
            String time = reservation.getString("time");
            String date = reservation.getString("res_date");
                holder.room.setText(room);
                holder.time.setText(time);
                holder.date.setText(date);
                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            cancelReservation(reservation.getInt("res_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(int res_id){

    }


    @Override
    public int getItemCount() {
        return reservations.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView room;
        TextView time;
        TextView date;
        RelativeLayout container;
        public ViewHolder(View itemView){
            super(itemView);
            room = itemView.findViewById(R.id.element_view_room);
            time = itemView.findViewById(R.id.element_view_time);
            date = itemView.findViewById(R.id.element_view_date);
            container = itemView.findViewById(R.id.element_view_container);
        }
    }

}
