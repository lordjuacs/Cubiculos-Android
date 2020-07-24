package m.example.cubiculos_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
}