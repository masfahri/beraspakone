package rontikeky.beraspakone.users;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.Home;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.DeleteCartRes;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.konfirmasi.main_container_konfirmasi;
import rontikeky.beraspakone.konfirmasi.stepTwo;
import rontikeky.beraspakone.products.Product;

public class AlamatAdapter extends RecyclerView.Adapter<AlamatAdapter.AlamatViewHolder> implements OnMapReadyCallback {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<User.ListAddress> addressList;

    //Selected Alamat
    private static int selectedItem = -1;
    private static RadioButton rbAlamat = null;




    //getting the context and product list with constructor
    public AlamatAdapter(Context mCtx, List<User.ListAddress> addressList) {
        this.mCtx = mCtx;
        this.addressList = addressList;

    }

    @Override
    public AlamatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_alamat, null);

        return new AlamatViewHolder(view);
    }

    @Override
    public int getItemCount() {
         if (addressList == null ) return 0; return addressList.size();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    class AlamatViewHolder extends RecyclerView.ViewHolder {
        TextView txtAlamat, txtNoTelp, txtAddress, idAlamat;
        ImageButton btnDelete;
        RadioButton selectedAlamat;

        public AlamatViewHolder(View itemView) {
            super(itemView);
            idAlamat        = itemView.findViewById(R.id.idAlamat);
            txtAlamat       = itemView.findViewById(R.id.txtALamat);
            txtAddress      = itemView.findViewById(R.id.txtAddress);
            selectedAlamat  = itemView.findViewById(R.id.rbAlamatTujuan);
            btnDelete       = itemView.findViewById(R.id.imageDelete);
            txtNoTelp       = itemView.findViewById(R.id.txtPhone);
            selectedAlamat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, addressList.size());
                    prefHandler.setIdAddressCheckout(String.valueOf(idAlamat.getText()));
                    Toast.makeText(mCtx, "selected is"+idAlamat.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(final AlamatViewHolder holder, int position) {
        final User.ListAddress listAddress = addressList.get(position);

        //binding the data with the viewholder views
        holder.idAlamat.setText(listAddress.getAddress());
        holder.selectedAlamat.setChecked(selectedItem == position);
        holder.selectedAlamat.setText(listAddress.getFirstName()+" "+listAddress.getLastName());
        holder.txtAddress.setText(listAddress.getIdAddress());
        holder.txtAlamat.setText(listAddress.getAddressName());
        holder.txtNoTelp.setText(listAddress.getPhone());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlamat(prefHandler.getUserId(), listAddress.getAddress());
            }
        });
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
                    Toast.makeText(mCtx, "Berhasil Delete Alamat", Toast.LENGTH_SHORT).show();
                }else {
                    mCtx.startActivities(new Intent[]{new Intent(new Intent(mCtx, main_container_konfirmasi.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK))});
                }
            }

            @Override
            public void onFailure(Call<DeleteCartRes> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }


}
