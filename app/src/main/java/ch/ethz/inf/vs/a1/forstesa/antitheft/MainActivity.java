package ch.ethz.inf.vs.a1.forstesa.antitheft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuHelper = menu;
        getMenuInflater().inflate(R.menu.menu_actuator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.settings) :
                Intent myIntent = new Intent(this, SettingsActivity.class);
                this.startActivity(myIntent);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickToggle(View v) {
        Intent intentService = new Intent(this, AntiTheftService.class);
        if (((ToggleButton) v).isChecked()) {
            startService(intentService);
            menuHelper.clear();
        } else {
            stopService(intentService);
            onCreateOptionsMenu(menuHelper);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private Menu menuHelper;
}
