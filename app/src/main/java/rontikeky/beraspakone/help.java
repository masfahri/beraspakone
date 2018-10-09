package rontikeky.beraspakone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Call BottomNavigationView
        final BottomNavigationView btmNavView = (BottomNavigationView) findViewById(R.id.btmNav);
        /* SET SELECTED ITEM */
        btmNavView.setSelectedItemId(R.id.action_help);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intentHome= new Intent(help.this, Home.class);
                        startActivity(intentHome);
                        break;
                    case R.id.action_help:
                        Intent intentHelp = new Intent(help.this, MainActivity.class);
                        startActivity(intentHelp);
                        break;
                    case R.id.action_status:
                        Intent intentStatus = new Intent(help.this, status_pembelian.class);
                        startActivity(intentStatus);
                        break;
//                    case R.id.action_history:
//                        Intent intentHistory = new Intent(help.this, HistoryPembelian.class);
//                        finish();
//                        startActivity(intentHistory);
//                        break;
                    case R.id.action_option:
                        Intent intentOption= new Intent(help.this, MainOption.class);
                        startActivity(intentOption);
                        break;
                }
                return true;
            }
        });
    }
}
