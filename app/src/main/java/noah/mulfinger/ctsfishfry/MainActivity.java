package noah.mulfinger.ctsfishfry;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private EditText etName;
    private EditText etPhone;
    public static Button btnTimePicker;

    private int menuSize;

    private Calendar pickupTime;
    private JSONArray orderArray;
    private int cost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, getString(R.string.key1), getString(R.string.key2));

        initializeValues();

        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        Button btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
        btnTimePicker = (Button) findViewById(R.id.btnTimePicker);

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                if ("".equals(name) || "".equals(phone)) {
                    if ("".equals(name)) {
                        etName.setError("This field cannot be blank.");
                    } else if ("".equals(phone)) {
                        etPhone.setError("This field cannot be blank.");
                    }
                } else {
                    placeOrder();
                }
            }
        });


        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

    }

    private void initializeValues() {

        menuSize = 5;
        orderArray = new JSONArray();
        for (int i = 0; i < menuSize; i++) {
            orderArray.put(0);
        }
        pickupTime = Calendar.getInstance();
        cost = 0;

    }

    private void placeOrder() {

        ParseObject testObject = new ParseObject("Order");
        testObject.put("name", etName.getText().toString());
        testObject.put("phone", etPhone.getText().toString());
        testObject.put("orderList", orderArray);
        testObject.put("cost", cost);
        testObject.put("pickupTime", pickupTime);
        testObject.put("completed", false);
        testObject.saveInBackground();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            btnTimePicker.setText(String.valueOf(hourOfDay % 12)+":"+String.valueOf(minute));
        }


    }
}
