package tut.flightbookingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {
    private String [] countries = {"South Africa", "Botswana", "Lesotho", "Swaziland", "Mozambique", "Zimbabwe"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, countries);
        final Spinner countriesSpinner = (Spinner) findViewById(R.id.country_id);
        countriesSpinner.setAdapter(adapter);
    }
}
