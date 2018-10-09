package rontikeky.beraspakone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.List.ListAddress;
import rontikeky.beraspakone.Response.Address;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.DeleteCartRes;
import rontikeky.beraspakone.cart.InsertCartRes;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.users.AddressReq;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.RegistRes;
import rontikeky.beraspakone.users.User;

public class TambahAlamat extends AppCompatActivity {
    TextView tvLatitude, tvLongitude, tvTitle;
    EditText etAlamatTujuan, etNamaDepan, etNamaBelakang, etAlamat, etNotelp, etNopos;
    Button btnRegister;

    private final int PLACE_PICKER_REQUEST = 1;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_alamat);

        final String idUser = prefHandler.getUserId();

        etAlamatTujuan = findViewById(R.id.etAddressName);
        etNamaDepan    = findViewById(R.id.etFirstName);
        etNamaBelakang = findViewById(R.id.etLastName);
        etAlamat       = findViewById(R.id.etAddress);
        etNotelp       = findViewById(R.id.etPhone);
        etNopos        = findViewById(R.id.etPostalCode);

        tvTitle        = findViewById(R.id.TextView1);
        tvLatitude     = findViewById(R.id.etLat);
        tvLongitude    = findViewById(R.id.etLong);

        btnRegister    = findViewById(R.id.btnRegister);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            etAlamatTujuan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_white_home_24dp, 0, 0, 0);
            etNamaDepan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_24dp, 0, 0, 0);
            etNamaBelakang.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_24dp, 0, 0, 0);
            etAlamat.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_location_24dp, 0, 0, 0);
            etNotelp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_local_phone_24dp, 0, 0, 0);
            etNopos.setCompoundDrawablesWithIntrinsicBounds(R.drawable.poscode, 0, 0, 0);
        }else{
            etAlamatTujuan.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            etNamaDepan.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            etNamaBelakang.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            etAlamat.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            etNotelp.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            etNopos.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        final Intent alamatId = getIntent();
        final String idAlamat = alamatId.getStringExtra("idAlamat");
        if (idAlamat != null){
            getAlamat(prefHandler.getUserId(), idAlamat);
            tvTitle.setText("Edit Alamat");
            btnRegister.setText("Update Alamat");
            Log.d("Alamat Id", idAlamat);
        }


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AlamatTujuan = etAlamatTujuan.getText().toString();
                String NamaDepan    = etNamaDepan.getText().toString();
                String NamaBelakang = etNamaBelakang.getText().toString();
                String Alamat       = etAlamat.getText().toString();
                String Notelp       = etNotelp.getText().toString();
                String Nopos        = etNopos.getText().toString();
                String Latitude     = tvLatitude.getText().toString();
                String Longitude    = tvLongitude.getText().toString();
                if (idAlamat == null){
                    Toast.makeText(TambahAlamat.this, "Tidak ada id alamat", Toast.LENGTH_SHORT).show();
                    insertAddress(idUser, AlamatTujuan, NamaDepan, NamaBelakang, Alamat, Notelp, Nopos, Latitude, Longitude);
                } else {
                    Toast.makeText(TambahAlamat.this, "Ada id alamat", Toast.LENGTH_SHORT).show();
                    updateAddress(idUser, idAlamat, AlamatTujuan, NamaDepan, NamaBelakang, Alamat, Notelp, Nopos, Latitude, Longitude);
                }

            }
        });

        etAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(TambahAlamat.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getAlamat(String userId, String idAlamat) {
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        String action = "user_address";
        Call<ListAddress> call = client.getUserAddressDetails(action, userId, idAlamat);
        call.enqueue(new Callback<ListAddress>() {
            @Override
            public void onResponse(Call<ListAddress> call, Response<ListAddress> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG ALAMAT",new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getAddress().get(0)));
                    Log.d("AddressName", response.body().getAddress().get(0).getAddressName());
                    etAlamatTujuan.setText(response.body().getAddress().get(0).getAddressName());
                    etNamaDepan.setText(response.body().getAddress().get(0).getFirstName());
                    etNamaBelakang.setText(response.body().getAddress().get(0).getLastName());
                    etAlamat.setText(response.body().getAddress().get(0).getAddress());
                    etNotelp.setText(response.body().getAddress().get(0).getPhone());
                    etNopos.setText(response.body().getAddress().get(0).getPostalCode());

                    tvLatitude.setText(response.body().getAddress().get(0).getLong());
                    tvLongitude.setText(response.body().getAddress().get(0).getLat());

                }else{
                    Log.d("DUMP3", new Gson().toJson(response.errorBody()));
                }

            }

            @Override
            public void onFailure(Call<ListAddress> call, Throwable t) {
                Log.d("ERROR", "onFailure: ", t);
            }
        });
    }

    private void insertAddress(String idUser, String alamatTujuan, String namaDepan, String namaBelakang, String alamat, String notelp, String nopos, String latitude, String longitude) {
        String action = "insert_address";
        String id_District = "1";
        String place_id = "1";
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        Call<DeleteCartRes> call = client.insertAddress
                (action, idUser, id_District, alamatTujuan, namaDepan, namaBelakang,
                 alamat, notelp, nopos, latitude, longitude, place_id);
        call.enqueue(new Callback<DeleteCartRes>() {
            @Override
            public void onResponse(Call<DeleteCartRes> call, Response<DeleteCartRes> response) {
                Log.d("Server Connected at ", String.valueOf(call.request().url()));
                Log.d("Response", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    Log.d("Sukses: ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    Toast.makeText(TambahAlamat.this, "Sukses Tambah Alamat "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    Intent konfirmasi = new Intent(TambahAlamat.this, main_container_home.class);
                    startActivity(konfirmasi);
                    finish();
                }

            }
            @Override
            public void onFailure(Call<DeleteCartRes> call, Throwable t) {

            }
        });

    }

    private void updateAddress(String idUser,  String idAlamat, String alamatTujuan, String namaDepan, String namaBelakang, String alamat, String notelp, String nopos, String latitude, String longitude) {
        String action = "edit_address";
        String id_District = "1";
        String place_id = "1";
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        Call<DeleteCartRes> call = client.updateAddress
                (action, idUser, idAlamat, id_District, alamatTujuan, namaDepan, namaBelakang,
                 alamat, notelp, nopos, latitude, longitude, place_id);
        call.enqueue(new Callback<DeleteCartRes>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<DeleteCartRes> call, Response<DeleteCartRes> response) {
                if (response.isSuccessful()) {
                    btnRegister.setBackgroundColor(R.color.disabled);
                    btnRegister.setText("Berhasil Ubah Alamat");
                    onBackPressed();
                }else{
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        InsertCartRes errorResponse = gson.fromJson(mJson, InsertCartRes.class);
                        switch (errorResponse.getStatusMessage()){
                            case "not_found":
                                Log.d("Not Found", String.valueOf(mJson));
                                Log.d("Id Alamat", idAlamat);
                                break;
                            case "no_affected_row":
                                Log.d("no_affected_row", String.valueOf(mJson));
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
            public void onFailure(Call<DeleteCartRes> call, Throwable t) {
                Log.d("Fail Update onFailure", String.valueOf(t));
                Log.d("Update", idUser+" "+alamatTujuan+" "+namaDepan+" "+namaBelakang+" "+alamat+" "+notelp+" "+nopos);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,this);
                etAlamat.setText(place.getAddress());

                LatLng queriedLocation = place.getLatLng();
//                tvLangLong.setText(""+queriedLocation.latitude+"/"+queriedLocation.longitude);
                Log.d(TAG, "onActivityResult: "+ queriedLocation.latitude+"/"+queriedLocation.longitude);
                tvLongitude.setText(String.valueOf(queriedLocation.latitude));
                tvLatitude.setText(String.valueOf(queriedLocation.longitude));
                Log.d("Result", queriedLocation.latitude+"/"+queriedLocation.longitude);
            }
        }
    }
}
