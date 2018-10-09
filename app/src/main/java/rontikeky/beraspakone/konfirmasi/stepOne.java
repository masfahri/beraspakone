package rontikeky.beraspakone.konfirmasi;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.Home;
import rontikeky.beraspakone.MainActivity;
import rontikeky.beraspakone.ProductAdapter;
import rontikeky.beraspakone.R;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;
import rontikeky.beraspakone.cart.CartAdapter;
import rontikeky.beraspakone.cart.CartRes;
import rontikeky.beraspakone.cart.ListCart;
import rontikeky.beraspakone.config.prefHandler;
import rontikeky.beraspakone.products.Product;
import rontikeky.beraspakone.products.ProductList;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class stepOne extends Fragment implements View.OnClickListener {

    private RecyclerView listCartProduct;
    private LinearLayoutManager mCartLinearLayoutManager;
    private CartAdapter mCartProductAdapter;
    protected Context mCtx;

    Button btnNext;
    TextView tvKosong, tvTotalHarga;
    Boolean kondisi = true;


    Locale localId;

    List<CartRes> cartList = new ArrayList<>();

    private View parentView;
    ProgressDialog dialog;
    ProgressBar pBar;


    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    public static stepOne newInstance() {
        return new stepOne();
    }

    @Override
    public void onAttach(Context mCtx) {
        super.onAttach(mCtx);
        this.mCtx = mCtx;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_one, container, false);


        listCartProduct = (RecyclerView) view.findViewById(R.id.listProduct);
        tvKosong = (TextView)view.findViewById(R.id.tv_kosong);
        pBar = (ProgressBar) view.findViewById(R.id.pCircle);
        tvTotalHarga = view.findViewById(R.id.tvHargaTotal);

        dialog = new ProgressDialog(getActivity());

        btnNext= (Button) view.findViewById(R.id.btnNext);
        btnNext .setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                if (kondisi) {
                    stepTwo mStepTwo = new stepTwo();
                    FragmentManager mFragmentManager = getFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.frame_konfirmasi, mStepTwo, "stepTwo")
                            .addToBackStack(null);
                    mFragmentTransaction.commit();
                    break;
                }
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCartLinearLayoutManager = new LinearLayoutManager(mCtx);
        CartAdapter adapter = new CartAdapter(mCtx, cartList) {
            @Override
            protected void TotalBelanjaan(Integer GT) {

            }
        };

        listCartProduct.setLayoutManager(mCartLinearLayoutManager);
        listCartProduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadData(prefHandler.getUserId());
    }

    private void loadData(String idUser) {
        pBar.setVisibility(View.VISIBLE);
        ApiService client = appControllerRetro.createService(ApiService.class);
        Call<ListCart> call = client.checkCart(idUser);
        call.enqueue(new Callback<ListCart>() {
            @Override
            public void onResponse(Call<ListCart> call, Response<ListCart> response) {
                pBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    if (response.body().getCart().size() == 0) {
                        Log.d("No Cart", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getCart()));
                    }
                    Log.d("RESPONSE", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getCart()));
                    int i = 0;
                    double GT = 0;
                    while (i < response.body().getCart().size()) {
                        GT = GT + (Integer.valueOf(response.body().getCart().get(i).getQty()) * Integer.valueOf(response.body().getCart().get(i).getPrice()));
                        Log.d("Grand Total", String.valueOf(GT));
                        i++;

                    }

                    cartList = response.body().getCart();
                    CartAdapter adapter = new CartAdapter(mCtx, cartList) {
                        @Override
                        protected void TotalBelanjaan(Integer GT) {

                        }
                    };
                    listCartProduct.setAdapter(adapter);

                    if (adapter.getItemCount() == 0){
                        tvKosong.setVisibility(View.VISIBLE);
                    } else {
                        CartAdapter cartAdapter = new CartAdapter(mCtx, cartList) {
                            @Override
                            protected void TotalBelanjaan(Integer GT) {

                            }
                        };
                        cartAdapter.notifyDataSetChanged();
                        listCartProduct.setVisibility(View.VISIBLE);
                        tvKosong.setVisibility(View.GONE);
                        localId = new Locale ("in", "ID");
                        NumberFormat formatRupiah =
                        NumberFormat .getCurrencyInstance(localId);
                        tvTotalHarga.setText(formatRupiah.format(GT));
                    }
                } else {
                    Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ListCart> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

}
