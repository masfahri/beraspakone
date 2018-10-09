package rontikeky.beraspakone.cart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.Home;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.konfirmasi.main_container_konfirmasi;
import rontikeky.beraspakone.konfirmasi.stepOne;

/**
 * Created by Acer on 2/16/2018.
 */

public abstract class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mCtx;
    private List<CartRes> cartList;

    boolean kondisi = true;

    public CartAdapter(Context mCtx, List<CartRes> cartList) {
        this.mCtx = mCtx;
        this.cartList = cartList;
        LayoutInflater mInflater = LayoutInflater.from(mCtx);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mCtx);
        View view = mInflater.inflate(R.layout.layout_products, null);
        ImageButton imgBtn = view.findViewById(R.id.imageDelete);
        imgBtn.setVisibility(view.VISIBLE);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
        final CartRes cartRes = cartList.get(position);

        holder.txtProduct_Name.setText(cartRes.getProductName());
        holder.textViewShortDesc.setText(cartRes.getDescription());
        holder.txtQty.setText(cartRes.getQty());
        holder.txtProduct_price.setText(cartRes.getPrice());
            int realPrice  = Integer.parseInt(holder.txtProduct_price.getText().toString());
            int quantity   = Integer.parseInt(holder.txtQty.getText().toString());
            int GrandTotal = (realPrice*quantity);
            TotalBelanjaan(GrandTotal);
        holder.txtGrandTotal.setText(String.valueOf(GrandTotal));
        holder.viewAnimate.setVisibility(View.GONE);
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.txtQty.equals("") || holder.txtQty.getText().toString() == "" || holder.txtQty.getText().length() == 0) {
                    Toast.makeText(mCtx, "Masukan jumlah barang", Toast.LENGTH_SHORT).show();
                }else{
                    updateCart(prefHandler.getUserId(), cartRes.getIdProduct(), holder.txtQty.getText().toString());
                }

            }
        });
        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCart(String.valueOf(prefHandler.getUserId()), String.valueOf(cartRes.getIdProduct()));
            }
        });
    }

    private void updateCart(String userId, String idProduct, String qty) {
        ApiService client        = appControllerRetro.createService(ApiService.class);
        Call<DeleteCartRes> call = client.updateCart(userId, idProduct, qty);
        call.enqueue(new Callback<DeleteCartRes>() {
            @Override
            public void onResponse(Call<DeleteCartRes> call, Response<DeleteCartRes> response) {
                if (response.isSuccessful()) {
                    Log.d("Sukses Update Cart", response.body().getStatusMessage());

                    Toast.makeText(mCtx, "Sukses Update Cart", Toast.LENGTH_SHORT).show();

                    mCtx.startActivities(new Intent[]{new Intent(new Intent(mCtx, main_container_konfirmasi.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK))});
                }else{
                    Toast.makeText(mCtx, "Mohon bedakan jumlah barang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteCartRes> call, Throwable t) {

            }
        });
    }

    private void deleteCart(String idUser, String idProduct) {
        ApiService client        = appControllerRetro.createService(ApiService.class);
        Call<DeleteCartRes> call = client.deleteCart(idUser, idProduct);
        call.enqueue(new Callback<DeleteCartRes>() {
            @Override
            public void onResponse(Call<DeleteCartRes> call, Response<DeleteCartRes> response) {
                if (response.isSuccessful()) {
//                    Log.d("RESPONSE Sukses", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getStatusMessage()));
                    Toast.makeText(mCtx, "Berhasil Menghapus Barang di Keranjang", Toast.LENGTH_SHORT).show();
                    switch (Integer.parseInt(prefHandler.getQtyCart())) {
                        case 0:
                            Toast.makeText(mCtx, "Tidak ada Cart", Toast.LENGTH_SHORT).show();
                            mCtx.startActivities(new Intent[]{new Intent(new Intent(mCtx, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK))});
                            break;
                        case 1:
                            int i = Integer.parseInt(prefHandler.getQtyCart()) - 1;
                            prefHandler.setQuantity(String.valueOf(i));
                            Log.d("1. Sisa Cart: ", prefHandler.setQuantity(String.valueOf(i)));
                            break;
                        case 2:
                            int ii = Integer.parseInt(prefHandler.getQtyCart()) - 1;
                            prefHandler.setQuantity(String.valueOf(ii));
                            Log.d("2. Sisa Cart: ", prefHandler.setQuantity(String.valueOf(ii)));
                            break;
                    }
                    if (prefHandler.getQtyCart() == "0") {
                        mCtx.startActivities(new Intent[]{new Intent(new Intent(mCtx, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK))});
                    };

                    mCtx.startActivities(new Intent[]{new Intent(new Intent(mCtx, main_container_konfirmasi.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK))});
                }else{
//                    Toast.makeText(mCtx, "Delete Failed "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteCartRes> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cartList == null){
            return 0;
        }
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtProduct_Name, textViewShortDesc, txtProdcutId, txtProduct_price, txtQty, txtGrandTotal, txtGT;
        ImageButton imgBtnDelete;
        ImageView viewAnimate;
        Button btnUpdate;
        RecyclerView recyclerView;

        public CartViewHolder(View itemView) {
            super(itemView);
            txtProduct_Name   = itemView.findViewById(R.id.txtProduct_Name);
            textViewShortDesc = itemView.findViewById(R.id.txtProduct_description);
            txtProduct_price  = itemView.findViewById(R.id.txtProduct_price);
            txtQty            = itemView.findViewById(R.id.jumlah);
            txtGrandTotal     = itemView.findViewById(R.id.total);
            imgBtnDelete      = itemView.findViewById(R.id.imageDelete);
            btnUpdate         = itemView.findViewById(R.id.btnOrder);
            viewAnimate       = itemView.findViewById(R.id.imageAnimate);
        }
    }

    protected abstract void TotalBelanjaan(Integer GT);

}
