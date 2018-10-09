package rontikeky.beraspakone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.io.IOException;
import java.text.Annotation;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import rontikeky.beraspakone.app.ApiService;

import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.CartRes;
import rontikeky.beraspakone.cart.InsertCartRes;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.users.ListAddress;
import rontikeky.beraspakone.users.LoginReq;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.User;

import static rontikeky.beraspakone.users.LoginRes.*;


/**
 * Created by Acer on 03/24/2017.
 */
public class Login extends AppCompatActivity {



    ProgressDialog pDialog;
    Button btn_register, btn_login;
    EditText txt_username, txt_password;
    Context mCtx;
    ApiService mApiService;

    List<ListAddress> listAddress = new ArrayList<>();

    Intent intent;


    int success;
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            txt_username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email_24dp, 0, 0, 0);
            txt_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_https_white_24dp, 0, 0, 0);
        }else{
            txt_username.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            txt_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        }

        mCtx = this;

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

        btn_login    = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);



        prefHandler.init(Login.this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String email = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                // mengecek kolom yang kosong
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        doLogin(email, password);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(Login.this, Register.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void doLogin(String email, String password) {
        final Integer idUserGroup = 2;
        String action = "login";
        ApiService client = appControllerRetro.createService(ApiService.class, "development", "");
        LoginReq reqLogin = new LoginReq(email, password, idUserGroup, action);
        Call<LoginRes> call = client.cekUser(action, email, password, idUserGroup);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                Log.d("Server Connected at ", String.valueOf(call.request().url()));
                if (response.code() == 200) {

                        prefHandler.setUserEmail(response.body().user.getUserEmail());
                        prefHandler.setToken(response.body().user.getToken());
                        prefHandler.setUserId(response.body().user.getIdUser());
                        Intent home= new Intent(mCtx, main_container_home.class);
                        startActivity(home);
                        finish();
//                    int i = 0;
//                    while (i < response.body().user.getListAddress().size()) {
//                        User.ListAddress list = new User.ListAddress(response.body().user.getListAddress().get(i).getAddressName(), response.body().user.getListAddress().get(i).getAddress());
////                        LoginRes.listAddress.add(list);
//                        i++;
//                        prefHandler.setAddressUser(list.getAddress());
//                        Log.d("VARDUMP", String.valueOf(list.getAddress()));
//
//                    }
                }else{

                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        InsertCartRes errorResponse = gson.fromJson(mJson, InsertCartRes.class);
                        switch (errorResponse.getStatusMessage()){
                            case "not_found":
                                txt_username.setError("Email Salah");
                                break;
                            case "wrong_pass":
                                txt_password.setError("Password Salah");
                                break;
                                default:
                                    break;
                        }
                        Log.d("RESPONSE", errorResponse.getStatusMessage());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                Toast.makeText(mCtx, "DATA GADA", Toast.LENGTH_LONG).show();
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }
}

