package rontikeky.beraspakone.Fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.MainOption;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.CartRes;
import rontikeky.beraspakone.cart.DeleteCartRes;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_edit_profile extends Fragment {

    TextView gender;
    EditText etNamaDepan, etNamaBelakang, etNotelp, etTanggalLahir;
    RadioGroup rgGender;
    RadioButton rbPria, rbWanita, rbGender;
    Calendar mCurentDay;
    int day, month, year;
    Button btnSimpan;

    Context mCtx;

    @Override
    public void onAttach(Context mCtx) {
        super.onAttach(mCtx);
        this.mCtx = mCtx;
    }

    public fragment_edit_profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        gender = view.findViewById(R.id.gender);
        etNamaDepan = view.findViewById(R.id.tvNamaDepan);
        etNamaBelakang = view.findViewById(R.id.tvNamaBelakang);
        etNotelp = view.findViewById(R.id.tvPhone);
        etTanggalLahir = view.findViewById(R.id.tvTanggalLahir);

        loadProfile(prefHandler.getUserId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            etNamaDepan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_24dp, 0,0,0);
            etNamaBelakang.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_24dp,0,0,0);
            etNotelp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_local_phone_24dp,0,0,0);
        }else{
            etNamaDepan.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            etNamaBelakang.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            etNotelp.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }

        rgGender = view.findViewById(R.id.rgGender);
        rbPria = view.findViewById(R.id.rbPria);
        rbWanita=view.findViewById(R.id.rbWanita);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbPria:
                        Log.d("Pria", String.valueOf(rbPria.getText()));
                        gender.setText(String.valueOf(rbPria.getText()));
                        break;
                    case R.id.rbWanita:
                        Log.d("Wanita", String.valueOf(rbWanita.getText()));
                        gender.setText(String.valueOf(rbWanita.getText()));
                        break;
                }
            }
        });

        mCurentDay = Calendar.getInstance();

        day = mCurentDay.get(Calendar.DAY_OF_MONTH);
        month = mCurentDay.get(Calendar.MONTH);
        year = mCurentDay.get(Calendar.YEAR);

        month = month + 1;

        etTanggalLahir.setText(year+"-"+month+"-"+day);
        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCtx, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        etTanggalLahir.setText(year+"-"+month+"-"+dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnSimpan = view.findViewById(R.id.btnSimpanProfil);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaDepan = etNamaDepan.getText().toString();
                String namaBelakang = etNamaBelakang.getText().toString();
                String phone = etNotelp.getText().toString();
                String birthDate = etTanggalLahir.getText().toString();

                if (namaDepan.trim().length() > 0 && namaBelakang.trim().length() > 0 && phone.trim().length() > 0 && birthDate.trim().length() > 0 && gender.getText().length() > 0) {
                        updateProfile(namaDepan, namaBelakang, phone, birthDate, gender);
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "Form Tidak Boleh Kosong ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void updateProfile(String namaDepan, String namaBelakang, String phone, String birthDate, TextView gender) {
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        String action = "edit_profile";
        String idUser = prefHandler.getUserId();
        Log.d("Gender: ", String.valueOf(gender.getText()));
        Call<DeleteCartRes> call = client.updateUser(action, idUser, namaDepan, namaBelakang, phone, String.valueOf(gender.getText()), birthDate);
        call.enqueue(new Callback<DeleteCartRes>() {
            @Override
            public void onResponse(Call<DeleteCartRes> call, Response<DeleteCartRes> response) {
                if(response.isSuccessful()) {
                    Log.d("Sukses",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    Toast.makeText(getActivity().getApplicationContext(), "Sukses Update Profile", Toast.LENGTH_SHORT).show();
                    Intent mHomeOption = new Intent(mCtx, MainOption.class);
                    startActivity(mHomeOption);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Gagal update Profile "+ response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteCartRes> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }

    private void loadProfile(String userId) {
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        String action = "user_profile";
        Call<LoginRes> call = client.getUserProfile(action, userId);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG ALAMAT",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    etNamaDepan.setText(response.body().user.getFirstName());
                    etNamaBelakang.setText(response.body().user.getLastName());
                    etTanggalLahir.setText(response.body().user.getBirthDate());
                    etNotelp.setText(response.body().user.getPhone());

                    Log.d("Result", String.valueOf(response.body().user));
                    if (response.body().user.getGender() == null) {
                        rbPria.setChecked(false);
                        rbWanita.setChecked(false);
                    }else if (response.body().user.getGender().equals("Pria") ) {
                        rbPria.setChecked(true);
                    }else if (response.body().user.getGender().equals("Wanita") ){
                        rbWanita.setChecked(true);
                    }else{
                        rbPria.setChecked(false);
                        rbWanita.setChecked(false);
                    }

                }else{
                    Log.d("DUMP3", new Gson().toJson(response.errorBody()));
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
