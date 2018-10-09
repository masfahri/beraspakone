package rontikeky.beraspakone;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.ListCart;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.products.Product;
import rontikeky.beraspakone.konfirmasi.main_container_konfirmasi;
import rontikeky.beraspakone.products.ProductList;

public class Home extends AppCompatActivity {

    TextView grandTotal;
    Button btnBuy;
    private AlertDialog.Builder alertDialogBuilder;
    ProgressDialog dialog;
    ProgressBar pBar;
    //a list to store all the products
    List<Product> productList = new ArrayList<>();

    //the recyclerview
    RecyclerView recyclerView;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pBar = (ProgressBar) findViewById(R.id.pCircle);

        prefHandler.init(Home.this);
        getAllProduct();

        parentView = findViewById(R.id.parentLayout);
        alertDialogBuilder = new AlertDialog.Builder(this);
        prefHandler.init(Home.this);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        grandTotal = (TextView) findViewById(R.id.valGrandTotal);
        btnBuy = (Button) findViewById(R.id.buy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefHandler.getUserEmail() != "") {
                    checkCart(prefHandler.getUserId());
                    Log.d("Sisa Cart di Home ", prefHandler.getQtyCart());
                    if (prefHandler.getQtyCart().equals("") || prefHandler.getQtyCart().equals("0")) {
                        Toast toast = Toast.makeText(Home.this.getApplicationContext(), "Belum ada Item di Cart", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0 , 0);
                        toast.show();
                    }else{
                        Intent konfirmasi = new Intent(Home.this, main_container_konfirmasi.class);
                        startActivity(konfirmasi);
                    }
                    if (grandTotal == null || grandTotal.getText() == "" || grandTotal.equals("")) {
                        Toast.makeText(Home.this.getApplicationContext(), "Silahkan pilih produk yang ingin di Beli", Toast.LENGTH_SHORT);
                    }
                }else{
                    Toast.makeText(Home.this.getApplicationContext(), "Silahkan Login sebelum memesan produk", Toast.LENGTH_SHORT);
                    Intent intentHome = new Intent(Home.this, Login.class);
                    startActivity(intentHome);
                }

            }
        });
        //Call BottomNavigationView
        final BottomNavigationView btmNavView = (BottomNavigationView) findViewById(R.id.btmNav);
        /* SET SELECTED ITEM */
        btmNavView.setSelectedItemId(R.id.action_home);
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intentHome = new Intent(Home.this, Home.class);
                        startActivity(intentHome);
                        break;
                    case R.id.action_help:
                        Intent intentHelp = new Intent(Home.this, MainActivity.class);
                        startActivity(intentHelp);
                        break;
                    case R.id.action_status:
                        Intent intentStatus = new Intent(Home.this, status_pembelian.class);
                        startActivity(intentStatus);
                        break;
//                    case R.id.action_history:
//                        Intent intentHistory = new Intent(Home.this, HistoryPembelian.class);
//                        finish();
//                        startActivity(intentHistory);
//                        break;
                    case R.id.action_option:
                        if (prefHandler.getUserEmail() != "") {
                            Intent intentOption = new Intent(Home.this, MainOption.class);
                            startActivity(intentOption);
                            break;
                        }
                        Toast.makeText(Home.this.getApplicationContext(), "Anda belum login, Silahkan Login", Toast.LENGTH_SHORT);
                        Intent Login = new Intent(Home.this, Login.class);
                        startActivity(Login);
                }
                return true;
            }
        });
    }

    private void getAllProduct() {
        pBar.setVisibility(View.VISIBLE);
        ApiService client = appControllerRetro.createService(ApiService.class, "development", "beras");
        Call<ProductList> call = client.getMyJSON();
        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                pBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
//                   Log.d("RESPONSE", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    int i = 0;
                    while (i < response.body().getProducts().size()) {
                        Product dataProduct = new Product(response.body().getProducts().get(i).getIdProduct(),
                                                          response.body().getProducts().get(i).getProductName(),
                                                          response.body().getProducts().get(i).getProductDescription());
                        productList.add(dataProduct);
                        i++;
                        Log.d("VARDUMP", String.valueOf(dataProduct.getProductName()));
                    }
                    productList = response.body().getProducts();
                    ProductAdapter adapter = new ProductAdapter(Home.this, productList){
                        //this is the magic, abstraction do
                        public void onClickAndCount(Integer GT){
                            //proses parameter yang didapet di sini, misal angka yang diinput user dari adapter tadi, atau apapun,
                            //terus taruh hasilnya di view activity ini, misal kalau casenya situ di bottomSHIT yah
                            TextView valGT = (TextView) findViewById(R.id.valGrandTotal);
                            valGT.setText(String.valueOf(GT));
                        }
                    };
                    recyclerView.setAdapter(adapter);


                } else {
                    Log.d("Fail Home", String.valueOf(response.errorBody()));
                }
            }
            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void checkCart(String idUser) {
        ApiService client = appControllerRetro.createService(ApiService.class);
        Call<ListCart> call = client.checkCart(idUser);
        call.enqueue(new Callback<ListCart>() {
            @Override
            public void onResponse(Call<ListCart> call, Response<ListCart> response) {
                if(response.isSuccessful()) {
                    Log.d("RESPONSE", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getCart().size()));
                    prefHandler.setQuantity(String.valueOf(response.body().getCart().size()));
//                    int i = 0;
//                    while (i < response.body().getCart().size()) {
//
//                    }
//                    cartList = response.body().getCart();
//                    CartAdapter adapter = new CartAdapter(mCtx, cartList);
//                    listCartProduct.setAdapter(adapter);
//                    mCartProductAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ListCart> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    public void onBackPressed() {
        alertDialogBuilder
                .setTitle("Tutup Aplikasi")
                .setMessage("Ingin Menutup Aplikasi?")
                .setCancelable(false)
                .setPositiveButton("YA",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //YA
                                Home.super.onBackPressed();
                            }
                        })
                .setNegativeButton("TIDAK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();


    }


}
