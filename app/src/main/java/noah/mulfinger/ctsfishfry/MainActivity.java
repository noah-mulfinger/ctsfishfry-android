package noah.mulfinger.ctsfishfry;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etName = (EditText) findViewById(R.id.etName);
        EditText etPhone = (EditText) findViewById(R.id.etPhone);

        Parse.initialize(this, getString(R.string.key1), getString(R.string.key2));
        JSONArray myArray = new JSONArray();
        myArray.put(1);
        myArray.put(2);
        myArray.put(3);
        myArray.put(4);
        myArray.put(5);

        Calendar date = Calendar.getInstance();



        ParseObject testObject = new ParseObject("Order");
        testObject.put("name", etName.getText().toString());
        testObject.put("phone", etPhone.getText().toString());
        testObject.put("orderList", myArray);
        testObject.put("cost", 150);
        testObject.put("pickupTime", date.getTime());
        testObject.put("completed", false);
        //testObject.saveInBackground();
        try {
            testObject.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
}
