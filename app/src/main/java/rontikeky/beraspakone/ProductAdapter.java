package rontikeky.beraspakone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.extra.Scale;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.CartRes;
import rontikeky.beraspakone.cart.InsertCartReq;
import rontikeky.beraspakone.cart.InsertCartRes;
import rontikeky.beraspakone.cart.ListCart;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.products.Product;

public abstract class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;
    private List<CartRes> cartList;
//    public abstract void onClickAndCount(int GrandTotal, int Price);
    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_product, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);

        holder.txtProduct_Name.setText(product.getProductName());
        holder.textViewShortDesc.setText(product.getProductDescription());
//        holder.textViewRating.setText(String.valueOf(product.getRating()));
        holder.txtProduct_price.setText("Rp. "+String.valueOf(product.getPrice()));
        holder.tvWeight.setText("per "+String.valueOf(product.getWeight())+" kg");
//        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.get()));
        holder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                Toast.makeText(mCtx, "Clicked at Position "+ holder.txtProduct_Name.getText(), Toast.LENGTH_SHORT).show();
                prefHandler.init(mCtx);
                if (prefHandler.getUserEmail() == "") {
                    Toast toast = Toast.makeText(mCtx.getApplicationContext(), "SILAHKAN LOGIN TERLEBIH DAHULU", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0 , 0);
                    toast.show();
                    Intent Login = new Intent(mCtx, rontikeky.beraspakone.Login.class);
                    mCtx.startActivity(Login);
                    ((Activity)mCtx).finish();
                }else{
                    holder.imageAnimate.setVisibility(View.VISIBLE);
                    ((Animatable)holder.imageAnimate.getDrawable()).start();
                    checkCart(prefHandler.getUserId());
                    String idProduct = product.getIdProduct();
                    Intent produkDetail = new Intent(mCtx, DetailProduct.class);
                    produkDetail.putExtra("idProduct", idProduct);
                    mCtx.startActivity(produkDetail);
                    ((Activity)mCtx).finish();


                        /*TRANSISITION ANIMATION*/
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                            TransitionSet tSet = new TransitionSet()
//                                    .addTransition(new Scale(0.7f))
//                                    .addTransition(new Fade())
//                                    .setInterpolator(new FastOutLinearInInterpolator());
//
//                            TransitionManager.beginDelayedTransition(holder.transitionsContainer, tSet);
//                            holder.tvTransition.setVisibility(View.VISIBLE);
//                        }
                        /*END TRANSISITION ANIMATION*/
                }
            }

            private void checkCart(String userId) {
                ApiService client = appControllerRetro.createService(ApiService.class);
                Call<ListCart> call = client.checkCart(userId);
                call.enqueue(new Callback<ListCart>() {
                    @Override
                    public void onResponse(Call<ListCart> call, Response<ListCart> response) {
                        if(response.isSuccessful()) {
                            Log.d("rspns ProductAdapter", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getCart().size()));
                            prefHandler.setQuantity(String.valueOf(response.body().getCart().size()));
//                            holder.imageAnimate.setVisibility(View.VISIBLE);
//                            ((Animatable)holder.imageAnimate.getDrawable()).start();
                        }else{
                            Log.d("Fail ON ADAPTER", String.valueOf(response.errorBody()));
                        }

                    }
                    @Override
                    public void onFailure(Call<ListCart> call, Throwable t) {

                    }
                });
            }

        });

        holder.txtProduct_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idProduct = product.getIdProduct();
                Intent produkDetail = new Intent(mCtx, DetailProduct.class);
                produkDetail.putExtra("idProduct", idProduct);
                mCtx.startActivity(produkDetail);
            }
        });
        holder.imageProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idProduct = product.getIdProduct();
                Intent produkDetail = new Intent(mCtx, DetailProduct.class);
                produkDetail.putExtra("idProduct", idProduct);
                mCtx.startActivity(produkDetail);
            }
        });
    }

    private void insertToCart(String idUser, int idProduct, int qty ) {
        ApiService client = appControllerRetro.createService(ApiService.class);
        InsertCartReq insertCartReq = new InsertCartReq(idUser, idProduct, qty);
        Call<InsertCartRes> call = client.insertCart(insertCartReq);
        call.enqueue(new Callback<InsertCartRes>() {
            @Override
            public void onResponse(Call<InsertCartRes> call, Response<InsertCartRes> response) {
                if (response.isSuccessful()) {
                    Log.d("Response: ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    Log.d("getStatusMessage ", response.body().getStatusMessage());
                    if  (response.body().getStatusMessage() == "success") {
                        Toast.makeText(mCtx, "Success", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mCtx, "Already Added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertCartRes> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }

    protected abstract void onClickAndCount(Integer GT);

    @Override
    public int getItemCount() {
        if(productList == null) { return 0; }
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtProduct_Name, textViewShortDesc, txtProduct_price, txtQty, txtGrandTotal, txtGT, tvTransition, tvWeight;
        ImageView imageAnimate, imageProduk;
        Button btnReadmore, btnOrder;
        ViewGroup transitionsContainer;

        public ProductViewHolder(View itemView) {
            super(itemView);

            txtProduct_Name      = itemView.findViewById(R.id.txtProduct_Name);
            textViewShortDesc    = itemView.findViewById(R.id.txtProduct_description);
            txtProduct_price     = itemView.findViewById(R.id.txtProduct_price);
            txtQty               = itemView.findViewById(R.id.jumlah);
            txtGrandTotal        = itemView.findViewById(R.id.total);
            txtGT                = itemView.findViewById(R.id.valGrandTotal);
            tvWeight             = itemView.findViewById(R.id.tvWeight);
            btnOrder             = itemView.findViewById(R.id.btnOrder);
            btnReadmore          = itemView.findViewById(R.id.btnRead);
            transitionsContainer = itemView.findViewById(R.id.parentLayout);
            tvTransition         = itemView.findViewById(R.id.tvTransition);
            imageAnimate         = itemView.findViewById(R.id.imageAnimate);
            imageProduk          = itemView.findViewById(R.id.imageView);
        }
    }


}
