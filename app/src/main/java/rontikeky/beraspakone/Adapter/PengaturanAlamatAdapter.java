package rontikeky.beraspakone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.TambahAlamat;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.DeleteCartRes;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.konfirmasi.main_container_konfirmasi;
import rontikeky.beraspakone.users.User;

public class PengaturanAlamatAdapter extends RecyclerView.Adapter<PengaturanAlamatAdapter.PengaturanAlamatViewHolder> {
    private Context mCtx;
    private List<User.ListAddress> addressList;
    //Selected Alamat
    private static int selectedItem = -1;
    private static RadioButton rbAlamat = null;
    MapView mMapView;
    GoogleMap mGoogleMap;

    //getting the context and product list with constructor
    public PengaturanAlamatAdapter(Context mCtx, List<User.ListAddress> addressList) {
        this.mCtx = mCtx;
        this.addressList = addressList;
    }

    @Override
    public PengaturanAlamatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_pengaturan_alamat, null);



        return new PengaturanAlamatViewHolder(view);
    }

    @Override
    public int getItemCount() {
         if (addressList == null ) return 0; return addressList.size();
    }

    class PengaturanAlamatViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        Button editAlamat, deleteAlamat;
        TextView txtAlamat;
        TextView txtNoTelp;
        ImageButton btnDelete;
        GoogleMap gMap;
        MapView map;

        public PengaturanAlamatViewHolder(View itemView) {
            super(itemView);
            editAlamat      = itemView.findViewById(R.id.btnUbahAlamat);
            deleteAlamat    = itemView.findViewById(R.id.deleteAlamat);
            txtAlamat       = itemView.findViewById(R.id.txtALamat);
            btnDelete       = itemView.findViewById(R.id.imageDelete);
            txtNoTelp       = itemView.findViewById(R.id.txtPhone);
            map             = itemView.findViewById(R.id.mapsAlamat);
            if(map != null) {
                map.onCreate(null);
                map.onResume();
                map.getMapAsync(this);
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(mCtx);
            gMap = googleMap;
            //you can move map here to item specific 'location'
            int pos = getPosition();
        }
    }

    @Override
    public void onBindViewHolder(final PengaturanAlamatViewHolder holder, int position) {
        final User.ListAddress listAddress = addressList.get(position);

        //binding the data with the viewholder views
        Double latitude = listAddress.getLat();
        Double longitude = listAddress.get_long();

        LatLng loc = new LatLng(latitude, longitude);

            Log.d("gMap: ", String.valueOf(holder.gMap));
            Log.d("Latitude1", String.valueOf(latitude));
            Log.d("Longitude1", String.valueOf(longitude));
        float zoom = 16.0f;

        if (holder.map != null){
            holder.map.onCreate(null);
            holder.map.onResume();
            holder.map.getMapAsync(mGoogleMap ->{
                MapsInitializer.initialize(mCtx);
                holder.gMap = mGoogleMap;

                LatLng sydney = new LatLng(longitude, latitude);

                mGoogleMap.addMarker(new MarkerOptions().position(sydney).title(listAddress.getAddressName()));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));
            });
        }
            Log.d("This map:", String.valueOf(holder.map));


        holder.txtAlamat.setText(listAddress.getAddressName());
        holder.txtNoTelp.setText(listAddress.getPhone());
        holder.editAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "id ALamat "+listAddress.getAddress(), Toast.LENGTH_SHORT).show();
                String idAlamat = listAddress.getAddress();
                Intent editAlamat = new Intent(mCtx, TambahAlamat.class);
                editAlamat.putExtra("idAlamat", idAlamat);
                mCtx.startActivity(editAlamat);
            }
        });
        holder.deleteAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlamat(prefHandler.getUserId(), listAddress.getAddress());
            }
        });

//        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteAlamat(prefHandler.getUserId(), listAddress.getAddress());
//            }
//        });
    }

    @Override
    public void onViewRecycled(PengaturanAlamatViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.gMap != null) {
            holder.gMap.clear();
            holder.gMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
    }

    private void deleteAlamat(String userId, String idAddress) {
        String action = "delete_address";
        Log.i("Sukses Delete Alamat", idAddress);
        ApiService client        = appControllerRetro.createService(ApiService.class);
        Call<DeleteCartRes> call = client.deleteAlamat(action, userId, idAddress);
        call.enqueue(new Callback<DeleteCartRes>() {
            @Override
            public void onResponse(Call<DeleteCartRes> call, Response<DeleteCartRes> response) {
                if (response.isSuccessful()) {
                    Log.i("Sukses Delete Alamat", String.valueOf(response.body()));
                    Toast.makeText(mCtx, "Berhasil Hapus Alamat", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mCtx, "Gagal Hapus Alamat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteCartRes> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }


}
