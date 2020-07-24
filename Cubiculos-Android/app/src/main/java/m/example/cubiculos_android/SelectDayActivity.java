package m.example.cubiculos_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SelectDayActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_day_activity);
        Spinner spinner = findViewById(R.id.times_spinner);
        user = getIntent().getExtras().getInt("user");
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);
        //adapter.SetDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }


//    MakeReservationsAdapter(JSONArray rooms_available, Context context, int userFromId, LocalDate date)
    public void goMakeReservations(View view){
        //TODO Implement Login
        DatePicker date = findViewById(R.id.datePicker1);
        System.out.println(date);
        Spinner spinner = (Spinner)findViewById(R.id.times_spinner);
        String time = spinner.getSelectedItem().toString();
        Intent intent = new Intent(this, MakeReservations.class);
        intent.putExtra("time", time);
        intent.putExtra("date", (Parcelable) date);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
