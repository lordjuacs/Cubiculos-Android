package m.example.cubiculos_android;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MakeReservationsAdapter extends RecyclerView.Adapter<MakeReservationsAdapter.ViewHolder>{
    public JSONArray rooms_available;
    private Context context;
    private int user;
    private String date;

    public MakeReservationsAdapter(JSONArray rooms_available, Context context, int user, String date){
        this.rooms_available = rooms_available;
        this.context = context;
        this.user = user;
        this.date = date;
        System.out.println(this.user);
    }

    public MakeReservationsAdapter(){}

    public void goMainReservations(String room, String date, int user){
        Intent intent = new Intent(this.context, MainReservations.class);
        intent.putExtra("date", date);
        intent.putExtra("room", room);
        intent.putExtra("id", user);
        this.context.startActivity(intent);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            final JSONObject room = rooms_available.getJSONObject(position);
            String roomName = room.getString("room");
            final String roomTime = room.getString("time");
            final int roomID = room.getInt("id");
            holder.roomName.setText(roomName);
            holder.roomTime.setText(roomTime);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeReservation(roomID, date, user);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void makeReservation(int room_id, final String res_date, final int user){
        //2. Build JSON Message
        Map<String, String> message = new HashMap<String, String>();
        message.put("room", Integer.toString(room_id));
        message.put("date", res_date);
        message.put("user", Integer.toString(user));
        JSONObject jsonMessage = new JSONObject(message);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8080/make-reservation",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO when OK Response
                        try {
                            goMainReservations(response.getString("room"), res_date,  user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO when Error Response
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    @Override
    public int getItemCount(){return rooms_available.length();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView roomName;
        TextView roomTime;
        RelativeLayout container;
        public ViewHolder(View itemView){
            super(itemView);
            roomName = itemView.findViewById(R.id.element_view_room);
            roomTime = itemView.findViewById(R.id.element_view_time);
            container = itemView.findViewById(R.id.element_view_container);
        }
    }






}
