package rontikeky.beraspakone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.AppController;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.users.LoginReq;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.RegistReq;
import rontikeky.beraspakone.users.RegistRes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_register, btn_login;
    EditText txt_email, txt_password, txt_FirstName, txt_LastName;
    TextView viewSuccess;
    Intent intent;
    TextInputLayout LayoutFirstName, LayoutLastName, LayuotEmail, LayoutPassword;

    int success;
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
        LayoutFirstName = findViewById(R.id.inputLayoutNamaDepan);

//        btn_login       = (Button) findViewById(R.id.btn_login);
        btn_register    = (Button) findViewById(R.id.btn_register);
        txt_FirstName   = (EditText) findViewById(R.id.txt_FirstName);
        txt_LastName    = (EditText) findViewById(R.id.txt_LastName);
        txt_email       = (EditText) findViewById(R.id.txt_email);
        txt_password    = (EditText) findViewById(R.id.txt_password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            txt_FirstName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_24dp, 0, 0, 0);
            txt_LastName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_24dp, 0, 0, 0);
            txt_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email_24dp, 0, 0,0);
            txt_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_https_white_24dp, 0, 0, 0);
        }else{
            txt_FirstName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            txt_LastName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            txt_email.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,0);
            txt_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        viewSuccess     = findViewById(R.id.viewSuccess);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String firstname = txt_FirstName.getText().toString();
                String lastname  = txt_LastName.getText().toString();
                String email     = txt_email.getText().toString();
                String password  = txt_password.getText().toString();

                if (conMgr.getActiveNetworkInfo() != null &&
                    conMgr.getActiveNetworkInfo().isAvailable() &&
                    conMgr.getActiveNetworkInfo().isConnected()) {
                    boolean isValid =  true;
                    if (firstname.isEmpty()) {
                        LayoutFirstName.setErrorEnabled(true);
                        LayoutFirstName.setError("Fill the Form");
                    } else { LayoutFirstName.setErrorEnabled(false); }
                        if (isValid) {
                            checkRegister(firstname, lastname, email, password);
                        }
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkRegister(final String firstname, final String lastname, final String email, final String pass) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);
        pDialog.setMessage("Register ...");
        showDialog();
        String action = "register";
        ApiService client = appControllerRetro.createService(ApiService.class);
        RegistReq reqRegist= new RegistReq (action, firstname, lastname, email, pass);
        Call<RegistRes> call = client.cekRegis(reqRegist);
        call.enqueue(new Callback<RegistRes>() {
            @Override
            public void onResponse(Call<RegistRes> call, retrofit2.Response<RegistRes> response) {
                if (response.isSuccessful()) {
                    Log.d("Sukses: ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response.code() == 201) {
                        viewSuccess.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Register.this, "Gagal Login ", Toast.LENGTH_SHORT).show();
                    hideDialog();
                    Log.d("Err", response.message());
                }
            }

            @Override
            public void onFailure(Call<RegistRes> call, Throwable t) {

            }
        });


        // Adding request to request queue
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(Register.this, Login.class);
        finish();
        startActivity(intent);
    }
}
