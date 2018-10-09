package rontikeky.beraspakone.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.Adapter.PengaturanAlamatAdapter;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.TambahAlamat;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.users.AlamatAdapter;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_pengaturan_alamat extends Fragment{

    GoogleMap mGoogleMap;
    MapView mMapView;
    View view;
    LinearLayoutManager mPengaturanAddressLinearLayoutManager;
    PengaturanAlamatAdapter mPengaturanAlamatAdapter;
    RecyclerView rvPengaturanAlamat;
    Context mCtx;
    ProgressBar pBar;
    TextView tvKosong;
    FloatingActionButton fabAddAlamaat;

    List<User.ListAddress> addressList2 = new ArrayList<>();

    public fragment_pengaturan_alamat() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context mCtx) {
        super.onAttach(mCtx);
        this.mCtx = mCtx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pengaturan_alamat, container, false);
        rvPengaturanAlamat = view.findViewById(R.id.rv_pengaturan_alamat);
        pBar = view.findViewById(R.id.pCircle);
        tvKosong = view.findViewById(R.id.tv_kosong);
        fabAddAlamaat = view.findViewById(R.id.fabAddAlamaat);

        fabAddAlamaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tambahAlamat = new Intent(mCtx, TambahAlamat.class);
                startActivity(tambahAlamat);
            }
        });

        prefHandler.init(mCtx);
//        mMapView = view.findViewById(R.id.mapsAlamat);
//        if (mMapView != null) {
//            mMapView.onCreate(null);
//            mMapView.onResume();
//            mMapView.getMapAsync(this);
//        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPengaturanAddressLinearLayoutManager = new LinearLayoutManager(mCtx);
        mPengaturanAlamatAdapter = new PengaturanAlamatAdapter(mCtx, addressList2 );
        rvPengaturanAlamat.setLayoutManager(mPengaturanAddressLinearLayoutManager);
        rvPengaturanAlamat.setAdapter(mPengaturanAlamatAdapter);
        loadData(prefHandler.getUserId());
    }

    private void loadData(String userId) {
        pBar.setVisibility(View.VISIBLE);
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        String action = "user_profile";
        Call<LoginRes> call = client.getUserProfile(action, userId);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                pBar.setVisibility(View.GONE);
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
                    if (mPengaturanAlamatAdapter.getItemCount() > 0) {
                        rvPengaturanAlamat.setVisibility(View.VISIBLE);
                    }else{
                        rvPengaturanAlamat.setVisibility(View.GONE);
                        tvKosong.setVisibility(View.VISIBLE);

                    }
                    mPengaturanAlamatAdapter.notifyDataSetChanged();
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

    //    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        MapsInitializer.initialize(getContext());
//
//        mGoogleMap = googleMap;
//        LatLng sydney = new LatLng(-6.257442499999989, 106.69948828124998);
//        mGoogleMap.addMarker(new MarkerOptions()
//        .position(sydney)
//                .title("Rumah Ane").snippet("Ane Dimarih"));
//        float zoom = 16.0f;
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));
//
//
//    }
}
