package rontikeky.beraspakone;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.Adapter.BannerAdapter;
import rontikeky.beraspakone.Fragment.fragment_help;
import rontikeky.beraspakone.Fragment.fragment_home;
import rontikeky.beraspakone.Fragment.fragment_option_menu;
import rontikeky.beraspakone.Fragment.fragment_status_pembayaran;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.ListCart;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.konfirmasi.main_container_konfirmasi;
import rontikeky.beraspakone.utils.BottomNavigationViewHelper;

public class main_container_home extends AppCompatActivity {

    Toolbar toolbar;
    Boolean kondisi = true;

    Context mCtx;
    Activity mActivity;
    Fragment fragment;
    RelativeLayout mRelativeLayout;
    FragmentManager fragmentManager;

    CarouselView carouselViewBanner;
    int[] banner = {R.drawable.vacum, R.drawable.nonvacum};
    ViewPager vpBanner;

    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container_home);

        carouselViewBanner = findViewById(R.id.carousel);
        carouselViewBanner.setPageCount(banner.length);
        carouselViewBanner.setImageListener(imageListener);

        mCtx = getApplication();
        mActivity = main_container_home.this;
        alertDialogBuilder = new AlertDialog.Builder(this);

        prefHandler.init(main_container_home.this);
        Log.d("Email", prefHandler.getUserEmail());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beras Pak One");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        fragmentManager =   getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_home, new fragment_home(), "Home").commit();
        //Call BottomNavigationView
        final BottomNavigationView btmNavView = (BottomNavigationView) findViewById(R.id.btmNav);
        BottomNavigationViewHelper.disableShiftMode(btmNavView);
        /* SET SELECTED ITEM */
        btmNavView.setSelectedItemId(R.id.action_home);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Fragment mHome = getSupportFragmentManager().findFragmentByTag("Home");
                        if (mHome != null && mHome.isVisible()) {
                            Log.d("Fragment Home", String.valueOf(mHome));
                        }else{
                            setSupportActionBar(toolbar);
                            getSupportActionBar().setTitle("Kiosk Beras");
                            if (getSupportActionBar() != null) {
                                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            }

                        }
                        fragment_home fHome = new fragment_home();
                        FragmentManager mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.frame_home, fHome, "Home");
//                                .addToBackStack(null);
                        mFragmentTransaction.commit();
                        break;
                    case R.id.action_help:
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setTitle("Bantuan");

                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        }
                        fragment_help fHelp = new fragment_help();
                        FragmentManager mFragmentManagerHelp = getSupportFragmentManager();
                        FragmentTransaction mFragmentTransactionHelp = mFragmentManagerHelp.beginTransaction();
                        mFragmentTransactionHelp.replace(R.id.frame_home, fHelp, "Help");
//                                .addToBackStack(null);
                        mFragmentTransactionHelp.commit();
                        break;
                    case R.id.action_status:
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setTitle("Status Belanja");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        }
                        fragment_status_pembayaran fStatusPembayaran = new fragment_status_pembayaran();
                        FragmentManager mFragmentManagerStatusPembayaran = getSupportFragmentManager();
                        FragmentTransaction mFragmentTransactionStatusPembayaran = mFragmentManagerStatusPembayaran.beginTransaction();
                        mFragmentTransactionStatusPembayaran .replace(R.id.frame_home, fStatusPembayaran, "Status");
//                                .addToBackStack(null);
                        mFragmentTransactionStatusPembayaran .commit();
                        break;
//                    case R.id.action_history:
//                        Intent intentHistory = new Intent(Home.this, HistoryPembelian.class);
//                        finish();
//                        startActivity(intentHistory);
//                        break;
                    case R.id.action_option:
                        if (prefHandler.getUserId() != ""){
                            Intent intentOption = new Intent(mCtx, MainOption.class);
                            startActivity(intentOption);
                            finish();
                            break;
                        }else{
                            Toast.makeText(mCtx, "Maaf Anda Belum Login", Toast.LENGTH_SHORT).show();
                            Intent intentLogin = new Intent(mCtx, Login.class);
                            startActivity(intentLogin);
                            finish();

                            break;
                        }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        if (prefHandler.getUserId() == "") {
            MenuItem logout = menu.findItem(R.id.logout);
            logout.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";

        switch (item.getItemId()) {
            case R.id.toolbarShare:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String message = "https://drive.google.com/open?id=19vANlHTvGpAi1E1QKbp3POKsgkhsqOUC";
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
                break;
            case R.id.toolbarCart:
                if (prefHandler.getUserId() == "") {
                    Intent Login = new Intent(this, Login.class);
                    startActivity(Login);
                    finish();
                }else{
                    checkCart(prefHandler.getUserId());
                }
                break;
            case R.id.logout:
                prefHandler.setLogout();
                if (prefHandler.getUserId() == ""){
                    Toast.makeText(mCtx, "Sudah Logout", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Fragment mHome = getSupportFragmentManager().findFragmentByTag("Home");
                if (mHome != null && mHome.isVisible()) {
                    finish();
                } else {
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Kiosk Beras");
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    }
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_home, new fragment_home(), "Home")
                            .addToBackStack("")
                            .commit();
                }

        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d("Session Id: ", prefHandler.getUserId());
//        if (prefHandler.getUserId() != "") {
//            checkCart(prefHandler.getUserId());
//        } else {
//
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(mCtx, "KELUAR", Toast.LENGTH_SHORT).show();
        return true;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(banner[position]);
        }
    };


    private void checkCart(String userId) {
        ApiService client = appControllerRetro.createService(ApiService.class);
        Call<ListCart> call = client.checkCart(userId);
        call.enqueue(new Callback<ListCart>() {
            @Override
            public void onResponse(Call<ListCart> call, Response<ListCart> response) {
                if(response.isSuccessful()) {
                    Log.d("RESPONSE", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getCart().size()));
                    prefHandler.setQuantity(String.valueOf(response.body().getCart().size()));
                    if (response.body().getCart().size() == 0) {
                        Toast.makeText(main_container_home.this, "Cart Kosong", Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(new Intent(mCtx, main_container_konfirmasi.class));
                        finish();
                    }
                }

            }
            @Override
            public void onFailure(Call<ListCart> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        alertDialogBuilder
                .setTitle("Tutup Aplikasi")
                .setMessage("Ingin Menutup Aplikasi?")
                .setCancelable(false)
                .setPositiveButton("YA",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //YA
                                main_container_home.this.finish();
                            }
                        })
                .setNegativeButton("TIDAK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();


    }

}
