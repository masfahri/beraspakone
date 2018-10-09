package rontikeky.beraspakone.konfirmasi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import android.os.Build;
import android.view.Gravity;
import android.widget.ImageButton;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import rontikeky.beraspakone.DetailProduct;
import rontikeky.beraspakone.Home;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.main_container_home;

public class main_container_konfirmasi extends AppCompatActivity {

    Button btnTambahAlamat, btnOtherAddress, btnNext;
    Boolean kondisi = true;
    Toolbar toolbar;

    Context mCtx;
    Activity mActivity;
    RelativeLayout mRelativeLayout;

    PopupWindow mPopupAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container_konfirmasi);

        mCtx = getApplication();
        mActivity = main_container_konfirmasi.this;

        prefHandler.init(main_container_konfirmasi.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Keranjang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stepOne mStepOne = new stepOne();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.frame_konfirmasi, mStepOne, "cart")
                            .addToBackStack(null)
                            .commit();

        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_main_container_pembayaran);


    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment mCart = getSupportFragmentManager().findFragmentByTag("cart");
        Fragment mStepTwo = getSupportFragmentManager().findFragmentByTag("stepTwo");
        Fragment mStepThree = getSupportFragmentManager().findFragmentByTag("stepThree");

        stepOne fStepOne = new stepOne();
        stepTwo fStepTwo = new stepTwo();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();


        Intent Home = new Intent(mCtx, main_container_home.class);

        if (mCart != null && mCart.isVisible()){
            startActivity(Home);
            finish();
        }
        else if (mStepTwo!= null && mStepTwo.isVisible()) {
            mFragmentTransaction.replace(R.id.frame_konfirmasi, fStepOne, "cart")
                    .addToBackStack(null)
                    .commit();
        }
        else {
            mFragmentTransaction.replace(R.id.frame_konfirmasi, fStepTwo, "stepTwo")
                    .addToBackStack(null)
                    .commit();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }


}
