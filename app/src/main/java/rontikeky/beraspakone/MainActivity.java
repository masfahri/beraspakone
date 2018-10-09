package rontikeky.beraspakone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btn_logout;
    TextView txt_id, txt_username;
    String id, username;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, Home.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txt_id = (TextView) findViewById(R.id.txt_id);
//        txt_username = (TextView) findViewById(R.id.txt_username);
//        btn_logout = (Button) findViewById(R.id.btn_logout);
//
////        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
//
//        id = getIntent().getStringExtra(TAG_ID);
//        username = getIntent().getStringExtra(TAG_USERNAME);
//
//        txt_id.setText("ID : " + id);
//        txt_username.setText("USERNAME : " + username);
//
//        btn_logout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                // update login session ke FALSE dan mengosongkan nilai id dan username
//                SharedPreferences.Editor editor = sharedpreferences.edit();
////                editor.putBoolean(Login.session_status, false);
//                editor.putString(TAG_ID, null);
//                editor.putString(TAG_USERNAME, null);
//                editor.commit();
//
//                Intent intent = new Intent(MainActivity.this, Login.class);
//                finish();
//                startActivity(intent);
//            }
//        });

        //Call BottomNavigationView
        final BottomNavigationView btmNavView = (BottomNavigationView) findViewById(R.id.btmNav);
        /* SET SELECTED ITEM */
        btmNavView.setSelectedItemId(R.id.action_help);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intentHome= new Intent(MainActivity.this, Home.class);
                        startActivity(intentHome);
                        break;
                    case R.id.action_help:
                        Intent intentHelp = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intentHelp);
                        break;
                    case R.id.action_status:
                        Intent intentStatus = new Intent(MainActivity.this, status_pembelian.class);
                        startActivity(intentStatus);
                        break;
//                    case R.id.action_history:
//                        Toast.makeText(MainActivity.this, "Ini adalah halaman History", Toast.LENGTH_SHORT).show();
////                        Intent intentHistory = new Intent(Home.this, history_pembelian.class);
////                        finish();
////                        startActivity(intentHistory);
//                        break;
                    case R.id.action_option:
                        Toast.makeText(MainActivity.this, "Ini adalah halaman Option", Toast.LENGTH_SHORT).show();
//                        Intent intentOption= new Intent(Home.this, history_pembelian.class);
//                        finish();
//                        startActivity(intentHistory);
                        break;
                }
                return true;
            }
        });

    }
}
