package m.example.cubiculos_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyReservations extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    public int user_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        if(getIntent().getExtras().getInt("user") != 0){
            this.user_id = getIntent().getExtras().getInt("user");
        }
        mRecyclerView = findViewById(R.id.my_reservations_recycler_view);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getReservations();
    }

    public Activity getActivity() {return this;}

    public void getReservations() {
            String url = "http://10.0.2.2:8080/my-reservations/" + user_id;
            System.out.println(url);
            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    new JSONArray(),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // TODO Process Response
                            mAdapter = new MyReservationsAdapter(response, getActivity());
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO
                            error.printStackTrace();
                        }
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
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