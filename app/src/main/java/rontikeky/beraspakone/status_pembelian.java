package rontikeky.beraspakone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

public class status_pembelian extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_pembelian);



        //Call BottomNavigationView
        final BottomNavigationView btmNavView = (BottomNavigationView) findViewById(R.id.btmNav);
        /* SET SELECTED ITEM */
        btmNavView.setSelectedItemId(R.id.action_status);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intentHome = new Intent(status_pembelian.this, Home.class);
                        startActivity(intentHome);
                        break;
                    case R.id.action_help:
                        Intent intentHelp = new Intent(status_pembelian.this, MainActivity.class);
                        startActivity(intentHelp);
                        break;
                    case R.id.action_status:
                        Intent intentStatus = new Intent(status_pembelian.this, status_pembelian.class);
                        startActivity(intentStatus);
                        break;
//                    case R.id.action_history:
//                        Intent intentHistory = new Intent(status_pembelian.this, HistoryPembelian.class);
//                        finish();
//                        startActivity(intentHistory);
//                        break;
                    case R.id.action_option:
                        Intent intentOption = new Intent(status_pembelian.this, MainOption.class);
                        startActivity(intentOption);
                        break;
                }
                return true;
            }
        });
    }



    @Override
    public void onBackPressed() {
        Intent inten = new Intent(status_pembelian.this, Home.class);
        finish();
        startActivity(inten);
    }

    public void status(View view) {
        CardView tblStatus= (CardView) findViewById(R.id.cardViewHistoryAll);
        if (tblStatus.getVisibility() == View.VISIBLE) {
            tblStatus.setVisibility(view.INVISIBLE);
        }else{
            tblStatus.setVisibility(view.VISIBLE);
        }
    }
}
