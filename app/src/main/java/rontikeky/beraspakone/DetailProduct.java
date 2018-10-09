package rontikeky.beraspakone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
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
import rontikeky.beraspakone.products.ProductList;


public class DetailProduct extends AppCompatActivity {

    List<Product> productList = new ArrayList<>();
    TextView tvProductName, tvProductDesc;
    CollapsingToolbarLayout collaps;
    Toolbar toolbar;
    ScrollableNumberPicker qty;
    FloatingActionButton fabShare;
    Button btnBeli;
    Context mCtx;

    Integer idProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        prefHandler.init(DetailProduct.this);
        checkCart(prefHandler.getUserId());

        tvProductName = findViewById(R.id.txtProductName);
        tvProductDesc = findViewById(R.id.txtProduct_description);
        fabShare = findViewById(R.id.fabShare);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String message = "https://drive.google.com/open?id=19vANlHTvGpAi1E1QKbp3POKsgkhsqOUC";
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
            }
        });

        final Intent productId = getIntent();
        idProduct = Integer.valueOf(productId.getStringExtra("idProduct"));
        getDetailProduct(productId.getStringExtra("idProduct"));



        collaps = findViewById(R.id.toolbarText);
        toolbar = findViewById(R.id.toolbar);
        qty     = findViewById(R.id.qty);
        qty.setListener(new ScrollableNumberPickerListener() {
            @Override
            public void onNumberPicked(int value) {
                Log.d("Testtt", String.valueOf(qty.getValue()));
            }
        });

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnBeli = findViewById(R.id.btnNext);
        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefHandler.getUserEmail() == "") {
                    Toast.makeText(DetailProduct.this, "Value : %d" + qty.getValue() , Toast.LENGTH_SHORT).show();
                    Toast.makeText(DetailProduct.this, "Silahkan Login sebelum memesan produk", Toast.LENGTH_SHORT);
                    Intent intentHome = new Intent(DetailProduct.this, Login.class);
                    startActivity(intentHome);
                } else {
                    Toast.makeText(DetailProduct.this, "Barang Masuk Cart", Toast.LENGTH_SHORT);
                    Integer quantity = qty.getValue();
                    insertToCart(prefHandler.getUserId(), idProduct, quantity);
                }
            }
        });


    }

    private void checkCart(String userId) {
        ApiService client = appControllerRetro.createService(ApiService.class);
        Call<ListCart> call = client.checkCart(userId);
        call.enqueue(new Callback<ListCart>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<ListCart> call, Response<ListCart> response) {
                if(response.isSuccessful()) {
                    Log.d("rspns ProductAdapter", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getCart()));
                    int i = 0;
                    int size = response.body().getCart().size();
                    while (i <  size) {
                        CartRes listCart = new CartRes( response.body().getCart().get(i).getIdProduct(),
                                                    response.body().getCart().get(i).getProductName(),
                                                    response.body().getCart().get(i).getPrice(),
                                                    response.body().getCart().get(i).getWeight(),
                                                    response.body().getCart().get(i).getDescription(),
                                                    response.body().getCart().get(i).getQty()
                                );
                        i++;
                        Log.d("Id Product: ", String.valueOf(idProduct));
                        Log.d("Id Product Cart: ", listCart.getIdProduct());
                        if (listCart.getIdProduct().equals(String.valueOf(idProduct))) {
                            btnBeli.setEnabled(false);
                            btnBeli.setText("Produk sudah ada dikeranjang");
                            btnBeli.setBackgroundColor(R.color.disabled);
                        }
                    }

                    Log.d("cart", String.valueOf(size));

//                            holder.imageAnimate.setVisibility(View.VISIBLE);
//                    btnBeli.setBackgroundColor(R.color.disabled)  ;
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


    private void insertToCart(String idUser, int idProduct, int qty) {
        ApiService client = appControllerRetro.createService(ApiService.class);
        InsertCartReq insertCartReq = new InsertCartReq(idUser, idProduct, qty);
        Call<InsertCartRes> call = client.insertCart(insertCartReq);
        call.enqueue(new Callback<InsertCartRes>() {
            @SuppressLint({"ResourceAsDrawable", "ResourceAsColor"})
            @Override
            public void onResponse(Call<InsertCartRes> call, Response<InsertCartRes> response) {
                if (response.isSuccessful()) {
                    Log.d("Response: ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    Log.d("getStatusMessage ", response.body().getStatusMessage());
                    Toast.makeText(DetailProduct.this, "Berhasil masuk keranjang", Toast.LENGTH_SHORT).show();
                    btnBeli.setEnabled(false);
                    btnBeli.setText("Produk sudah ada dikeranjang");
                    btnBeli.setBackgroundColor(R.color.disabled);
                }
            }

            @Override
            public void onFailure(Call<InsertCartRes> call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent Home = new Intent(DetailProduct.this, main_container_home.class);
        startActivity(Home);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent Home = new Intent(DetailProduct.this, main_container_home.class);
        startActivity(Home);
        finish();
        return true;
    }

    private void getDetailProduct(String idProduct) {
        ApiService client       = appControllerRetro.createService(ApiService.class);
        Call<ProductList> call  = client.getDetailProduct(idProduct);
        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE Sukses", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    int i = 0;
                    while (i < response.body().getProducts().size()) {
                        Product data = new Product(response.body().getProducts().get(i).getIdProduct(),
                                                   response.body().getProducts().get(i).getProductName(),
                                                   response.body().getProducts().get(i).getProductDescription());
                        productList.add(data);
                        tvProductName.setText(response.body().getProducts().get(i).getProductName());
                        collaps.setTitle(response.body().getProducts().get(i).getProductName());
                        toolbar.setTitle(response.body().getProducts().get(i).getProductName());
                        tvProductDesc.setText(response.body().getProducts().get(i).getProductDescription());
                        i++;
                    }
                }
                else{
                    Toast.makeText(DetailProduct.this, "No data Selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {

            }
        });
    }
}
