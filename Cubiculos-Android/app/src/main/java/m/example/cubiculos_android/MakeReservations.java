package m.example.cubiculos_android;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

public class MakeReservations  extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    public int id; //id del usuario

    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservations);
        String username = getIntent().getExtras().getString("username");
        this.id = getIntent().getExtras().getInt("id");
        setTitle(username);
        mRecyclerView = findViewById(R.id.make_reservations_recycler_view);

    }

}
