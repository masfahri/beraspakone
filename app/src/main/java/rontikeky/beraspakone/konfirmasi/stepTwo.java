package rontikeky.beraspakone.konfirmasi;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.Home;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.TambahAlamat;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.kurir.KurirAdapter;
import rontikeky.beraspakone.users.AlamatAdapter;
import rontikeky.beraspakone.users.ListAddress;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.User;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class stepTwo extends Fragment implements View.OnClickListener {

    private RecyclerView listAddress, rvKurir;
    private RecyclerView.LayoutManager mKurirLayoutManager;
    private RecyclerView.Adapter mKurirAdapter;

    private LinearLayoutManager mAddressLinearLayoutManager;
    private AlamatAdapter mAlamatAdapter;
    private TextView btnTambahAlamat, tvEstimasi, tvKosong;
    private Button btnNext;
    private Spinner comboKurir, comboTipePengiriman;
    private ImageButton btnDelete;
    protected Context mCtx;

    Boolean kondisi = true;

    List<ListAddress.ListAlamat> addressList = new ArrayList<>();
    List<User.ListAddress> addressList2 = new ArrayList<>();


    private View view;
    ProgressDialog dialog;

    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    public static stepTwo newInstance() {
        return new stepTwo();
    }

    @Override
    public void onAttach(Context mCtx) {
        super.onAttach(mCtx);
        this.mCtx = mCtx;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_two, container, false);
        listAddress = (RecyclerView) view.findViewById(R.id.rv_alamat);

        tvEstimasi = view.findViewById(R.id.tvEstimasi);
        tvKosong = view.findViewById(R.id.tv_kosong);

//        rvKurir = (RecyclerView) view.findViewById(R.id.rvKurir);
//        rvKurir.setHasFixedSize(true);
//
//        mKurirLayoutManager = new GridLayoutManager(mCtx, 2);
//        rvKurir.setLayoutManager(mKurirLayoutManager);
//
//        mKurirAdapter = new KurirAdapter();
//        rvKurir.setAdapter(mKurirAdapter);

        comboKurir = view.findViewById(R.id.spinnerKurir);
        comboTipePengiriman = view.findViewById(R.id.spinnerTipePengiriman);
        String[] pilih_kurir = getResources().getStringArray(R.array.pilih_kurir);
        ArrayAdapter<String> adapterKurir = new ArrayAdapter<String>(mCtx, R.layout.item_kurir, R.id.tvKurir, pilih_kurir);
        comboKurir.setAdapter(adapterKurir);
        comboKurir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String defaultSelected = comboKurir.getSelectedItem().toString();
                if (defaultSelected.equals("Pilih Kurir")){
//                    Toast.makeText(mCtx, "Default Pilih Kurir", Toast.LENGTH_SHORT).show();
                    comboTipePengiriman.setVisibility(View.INVISIBLE);
                }else if (defaultSelected.equals("Gojek")){
                    comboTipePengiriman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String defaultPaket = comboTipePengiriman.getSelectedItem().toString();
                            if (defaultPaket.equals("Pilih Paket Pengiriman")) {
                                tvEstimasi.setText("0");
                            }else if (defaultPaket.equals("Same Day")) {
                                tvEstimasi.setText("10000");
                            }else if (defaultPaket.equals("Instant Courier")){
                                tvEstimasi.setText("15000");
                            }else{
                                tvEstimasi.setText("0");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    Toast.makeText(mCtx, comboKurir.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    comboTipePengiriman.setVisibility(View.VISIBLE);
                    String[] pilih_kurir = getResources().getStringArray(R.array.item_gojek);
                    ArrayAdapter<String> adapterKurir = new ArrayAdapter<String>(mCtx, R.layout.item_paket, R.id.tvKurir, pilih_kurir);
                    comboTipePengiriman.setAdapter(adapterKurir);
                }else if(defaultSelected.equals("JNE")) {
                    Toast.makeText(mCtx, comboKurir.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    comboTipePengiriman.setVisibility(View.VISIBLE);
                    String[] pilih_kurir = getResources().getStringArray(R.array.item_jne);
                    ArrayAdapter<String> adapterKurir = new ArrayAdapter<String>(mCtx, R.layout.item_paket, R.id.tvKurir, pilih_kurir);
                    comboTipePengiriman.setAdapter(adapterKurir);
                }else{
                    Toast.makeText(mCtx, "Silahkan Pilih Kurir", Toast.LENGTH_SHORT).show();
                }

            }

            private void setId() {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnTambahAlamat = (TextView) view.findViewById(R.id.btnTambahAlamat);
        btnTambahAlamat.setOnClickListener(this);

        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAddressLinearLayoutManager = new LinearLayoutManager(mCtx);
        mAlamatAdapter = new AlamatAdapter(mCtx, addressList2);
        listAddress.setLayoutManager(mAddressLinearLayoutManager);
        listAddress.setAdapter(mAlamatAdapter);
        loadData(prefHandler.getUserId());
    }

    private void loadData(String idUser) {
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        String action = "user_profile";
        Call<LoginRes> call = client.getUserProfile(action, idUser);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if (response.isSuccessful()) {
                    int alamat = response.body().user.getListAddress().size();
                    Log.d("Jumlah Alamat: ", String.valueOf(alamat));
                    Log.d("DEBUG ALAMAT",new GsonBuilder().setPrettyPrinting().create().toJson(response.body().user.getListAddress()));
                    int i = 0;
                    while (i < alamat) {
                        User.ListAddress list= new User.ListAddress(response.body().user.getListAddress().get(i).getAddressName(),
                                                                    response.body().user.getListAddress().get(i).getAddress(),
                                                                    response.body().user.getListAddress().get(i).getIdAddress(),
                                                                    response.body().user.getListAddress().get(i).getPhone(),
                                                                    response.body().user.getListAddress().get(i).getLat(),
                                                                    response.body().user.getListAddress().get(i).get_long(),
                                                                    response.body().user.getListAddress().get(i).getFirstName(),
                                                                    response.body().user.getListAddress().get(i).getLastName());
                        addressList2.add(list);
                        i++;
                    }
                    mAlamatAdapter.notifyDataSetChanged();
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


    @Override
    public void onClick(View v) {
        Boolean kondisi = true;
        switch (v.getId()) {
            case R.id.btnTambahAlamat:
                if (kondisi) {
                    Intent tambahAlamat = new Intent(mCtx, TambahAlamat.class);
                    startActivity(tambahAlamat);
                }
                break;
            case R.id.btnNext:
                if (kondisi) {
                    if (prefHandler.getIdAddressCheckout() != ""){
                        stepThree mStepThree = new stepThree();
                        FragmentManager mFragmentManager = getFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.frame_konfirmasi, mStepThree, "stepThree")
                                .addToBackStack(null);
                        mFragmentManager.popBackStack();
                        mFragmentTransaction.commit();
                    }
                    break;
                }
        }
    }
}
