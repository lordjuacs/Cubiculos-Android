package m.example.cubiculos_android;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeReservationsAdapter extends RecyclerView.Adapter<MakeReservationsAdapter.ViewHolder>{
    public JSONArray rooms_available;
    private Context context;
    private int userFromId;
    private String date;

    public MakeReservationsAdapter(){

    }
    public MakeReservationsAdapter(JSONArray rooms_available, Context context, int userFromId, String date){
        this.rooms_available = rooms_available;
        this.context = context;
        this.userFromId = userFromId;
        this.date = date;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_view, parent, false);
        return new ViewHolder(view);
    }


    public void goMainReservations(){
        Intent intent = new Intent(this.context, MainReservations.class);
        this.context.startActivity(intent);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.print("Hola. ");
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
                        makeReservation(roomID, date, userFromId);
                    }
                });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void makeReservation(int roomID, String date, final int userFromId){
        //2. Build JSON Message
        Map<String, String> message = new HashMap<String, String>();
        message.put("room", Integer.toString(roomID));
        message.put("date", date);
        message.put("user", Integer.toString(userFromId));
        JSONObject jsonMessage = new JSONObject(message);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8080/make-reservation",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO when OK Response
                        goMainReservations();
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
    public int getItemCount() {
        rooms_available.length();
        return 0;
    }
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