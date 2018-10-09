package rontikeky.beraspakone.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.ProductAdapter;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.products.Product;
import rontikeky.beraspakone.products.ProductList;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_home extends Fragment {

    private RecyclerView rvProduct;
    private LinearLayoutManager mProductLayoutManager;
    private ProductAdapter mProductAdapter;
    protected Context mCtx;

    ProgressBar pBar;
    ProgressDialog pDialog;

    List<Product> productList = new ArrayList<>();

    public fragment_home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pBar = view.findViewById(R.id.pCircle);

        getAllProduct();
        rvProduct = view.findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);

        mProductLayoutManager = new GridLayoutManager(mCtx, 2);
        rvProduct.setLayoutManager(mProductLayoutManager);

        return view;
    }

    @Override
    public void onAttach(Context mCtx) {
        super.onAttach(mCtx);
        this.mCtx = mCtx;
    }

    private void getAllProduct() {
        pBar.setVisibility(View.VISIBLE);
        ApiService client = appControllerRetro.createService(ApiService.class, "Basic ZGV2ZWxvcG1lbnQ6");
        Call<ProductList> call = client.getMyJSON();
        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                pBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    int i = 0;
                    while (i < response.body().getProducts().size()) {
                        Product dataProduct = new Product(response.body().getProducts().get(i).getIdProduct(),
                                                          response.body().getProducts().get(i).getProductName(),
                                                          response.body().getProducts().get(i).getProductDescription());
                        productList.add(dataProduct);
                        i++;
//                        Log.d("VARDUMP", String.valueOf(dataProduct.getProductName()));
                    }
                    productList = response.body().getProducts();
                    ProductAdapter adapter = new ProductAdapter(mCtx, productList){
                        //this is the magic, abstraction do
                        public void onClickAndCount(Integer GT){
                            //proses parameter yang didapet di sini, misal angka yang diinput user dari adapter tadi, atau apapun,
                            //terus taruh hasilnya di view activity ini, misal kalau casenya situ di bottomSHIT yah
//                            TextView valGT = (TextView) findViewById(R.id.valGrandTotal);
//                            valGT.setText(String.valueOf(GT));
                        }
                    };
                    rvProduct.setAdapter(adapter);


                } else {
                    Log.d("Error Home", response.message());
                }
            }
            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
//                dialog.dismiss();
            }
        });
    }

}
