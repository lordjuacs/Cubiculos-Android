package m.example.cubiculos_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainReservations extends AppCompatActivity {

    int user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int user_id = getIntent().getExtras().getInt("id");
        setContentView(R.layout.activity_main_reservations);
        user = user_id;

        String room = getIntent().getExtras().getString("room");
        String date = getIntent().getExtras().getString("date");
        int USER = getIntent().getExtras().getInt("userID");
        if(room != null && date != null){
            user = USER;
            Toast.makeText(this, "Reservation created for " + date + " at " + room + "!",Toast.LENGTH_SHORT).show();
        }

    }

    public void goSelectDay(View view){
        //TODO Implement Login
        Intent intent = new Intent(this, SelectDayActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
    public void goMyReservations(View view){
        //TODO Implement Login
        Intent intent = new Intent(this, MyReservations.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }



    public void goMainActivity(){
        //TODO Implement Login
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onLogoutClicked(){
        //TODO Implement Login
        //3. Build Request Object --> volley
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8080/logout",
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO when OK Response
                        showMessage("Logged Out!");
                        goMainActivity();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO when Error Response
                        showMessage("Failed!");
                    }
                }
        );

        //4. Send Request Message to server
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void onInfoClicked(){
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_buttons, menu);
        return true;
    }


    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dots_logout:
                onLogoutClicked();
                return true;
            case R.id.dots_about_me:
                onInfoClicked();
                return true;

           /* case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;*/
        }
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);

    }
}