package rontikeky.beraspakone;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.Fragment.fragment_edit_profile;
import rontikeky.beraspakone.Fragment.fragment_option_menu;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.DeleteCartRes;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.users.AlamatAdapter;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.PasswordReq;
import rontikeky.beraspakone.users.RegistReq;
import rontikeky.beraspakone.users.RegistRes;
import rontikeky.beraspakone.users.User;

import static android.net.Uri.parse;

public class MainOption extends AppCompatActivity {
    List<User.ListAddress> addressList2 = new ArrayList<>();
    private AlamatAdapter mAlamatAdapter;
    ConnectivityManager conMgr;

    private android.support.v4.app.FragmentManager fragmentManager;
    private Fragment fragment;
    private Toolbar toolbar;
    private Context mCtx;
    private TextView tvNamaPembeli;

    private CircleImageView cImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pengaturan");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_option, new fragment_option_menu(), "OptionMenu")
                .addToBackStack("")
                .commit();
        loadUser(prefHandler.getUserId());

//        String imageUrl = "https://dev.kioskberasindonesia.com/uploads/user/default.jpg";
//        cImageView = findViewById(R.id.profile_image);
//        Picasso.get().load(imageUrl)
//                .placeholder(R.drawable.vacum)
//                .into(cImageView);

        tvNamaPembeli = findViewById(R.id.tvNamaPembeli);
    }

    private void loadUser(String userId) {
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        String action = "user_profile";
        Call<LoginRes> call = client.getUserProfile(action, userId);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG ALAMAT",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    tvNamaPembeli.setText(response.body().user.getFirstName()+" "+response.body().user.getLastName());
                    String imageUrl = response.body().user.getImageName();
                    cImageView = findViewById(R.id.profile_image);
                    Picasso.get().load(imageUrl)
                            .placeholder(R.drawable.vacum)
                            .into(cImageView);
                }else{
                    Log.d("DUMP3", new Gson().toJson(response.errorBody()));
                }

            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment mHome = getSupportFragmentManager().findFragmentByTag("OptionMenu");
        if (mHome != null && mHome.isVisible()) {
            Intent Home = new Intent(MainOption.this, main_container_home.class);
            startActivity(Home);
            finish();
        } else {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_option, new fragment_option_menu(), "OptionMenu")
                    .addToBackStack("")
                    .commit();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        Fragment mHome = getSupportFragmentManager().findFragmentByTag("OptionMenu");
        if (mHome != null && mHome.isVisible()) {
            Intent Home = new Intent(MainOption.this, main_container_home.class);
            startActivity(Home);
            finish();
        } else {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_option, new fragment_option_menu(), "OptionMenu")
                    .addToBackStack("")
                    .commit();
        }
        return true;
    }

}
