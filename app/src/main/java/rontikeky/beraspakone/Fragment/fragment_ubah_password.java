package rontikeky.beraspakone.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.MainOption;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.DeleteCartRes;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_ubah_password extends Fragment {
    List<User.ListAddress> addressList2 = new ArrayList<>();
    EditText txtPassOld, txtPassNew, txtPassNewConfirm;
    Button btnEdit;

    public fragment_ubah_password() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ubah_password, container, false);
        loadProfile(prefHandler.getUserId());

        txtPassOld = view.findViewById(R.id.etOldPass);
        txtPassNew = view.findViewById(R.id.etPasswordNew);
        txtPassNewConfirm = view.findViewById(R.id.etKonfirmPassword);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            txtPassOld.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_https_white_24dp, 0,0 ,0);
            txtPassNew.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_https_white_24dp, 0,0 ,0);
            txtPassNewConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_https_white_24dp, 0,0 ,0);
        }else{
            txtPassOld.setCompoundDrawablesWithIntrinsicBounds(0, 0,0 ,0);
            txtPassNew.setCompoundDrawablesWithIntrinsicBounds(0, 0,0 ,0);
            txtPassNewConfirm.setCompoundDrawablesWithIntrinsicBounds(0, 0,0 ,0);
        }

        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String etPassLama = txtPassOld.getText().toString();
                final String etPassBaru = txtPassNew.getText().toString().trim();
                final String etPassKonf = txtPassNewConfirm.getText().toString().trim();

                if (!etPassBaru.equals(etPassKonf)) {
                    txtPassNewConfirm.setError("Password Tidak Sama");
                    Log.d("Password Baru", etPassBaru);
                    Log.d("Konfirmasi Password", etPassKonf);
                }else{
                    editPass(prefHandler.getUserId(), etPassLama, etPassBaru );
                }

            }
        });

        return view;
    }

    private void editPass(String userId, String etPassLama, String etPassBaru) {
        String action = "edit_password";
        ApiService client = appControllerRetro.createService(ApiService.class);
        Call<DeleteCartRes> call = client.updatePassword(action, userId, etPassLama, etPassBaru);
        call.enqueue(new Callback<DeleteCartRes>() {
            @Override
            public void onResponse(Call<DeleteCartRes> call, Response<DeleteCartRes> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext().getApplicationContext(), "Sukses Ganti Password", Toast.LENGTH_SHORT).show();
                    Intent mHomeOption = new Intent(getActivity().getApplicationContext(), MainOption.class);
                    startActivity(mHomeOption);
                }else{
                    txtPassOld.setError("password salah");
                    Log.d("Fail: ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<DeleteCartRes> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProfile(String userId) {
        ApiService client = appControllerRetro.createService(ApiService.class);
        Call<LoginRes> call = client.cekAddress(userId);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if (response.isSuccessful()) {
                    int alamat = response.body().user.getListAddress().size();
                    Log.d("DEBUG 1",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    int i = 0;
                    while (i < alamat) {
                        User.ListAddress list= new User.ListAddress(response.body().user.getListAddress().get(i).getAddressName(),
                                response.body().user.getListAddress().get(i).getAddress(),
                                response.body().user.getListAddress().get(i).getAddress(),
                                response.body().user.getListAddress().get(i).getPhone(),
                                response.body().user.getListAddress().get(i).getLat(),
                                response.body().user.getListAddress().get(i).get_long(),
                                response.body().user.getListAddress().get(i).getFirstName(),
                                response.body().user.getListAddress().get(i).getLastName());
                        addressList2.add(list);
                        i++;
                    }
                }else{
                    Log.d("DUMP3", new Gson().toJson(response.errorBody()));
                }

            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {

            }
        });
    }

}
