package rontikeky.beraspakone.products;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rontikeky.beraspakone.*;
import rontikeky.beraspakone.app.ApiService;
import rontikeky.beraspakone.app.appControllerRetro;


public class MainProduct extends AppCompatActivity {

    private ListView listView;
    private View parentView;

    private ArrayList<Product> productList;
    private MyProductAdapter adapter;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);
        /*
        Array Llist for Binding Data From JSON  to this list
         */

        productList = new ArrayList<>();
        parentView = findViewById(R.id.parentLayout);

        /*
        Getting List and Setting List Adapter
         */

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(parentView, productList.get(position).getProductName() + " => " + productList.get(position).getProductDescription()+ " => " + productList.get(position).getPrice(), Snackbar.LENGTH_LONG).show();
            }
        });

        /*
        Just to Know onClick and Printing Hello Toast in Center
         */
        Toast toast = Toast.makeText(getApplicationContext(), R.string.string_click_to_load, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0 , 0);
        toast.show();

        getAllProduct();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(@NonNull final View view ){
                /*
                Check Connections
                 */

            }
        });
    }
    private void getAllProduct(){
        //Creating an object of our api interface
        //ApiService api = appControllerRetro.getApiService();
        ApiService client = appControllerRetro.createService(ApiService.class);
        /**
         * Calling JSON
         */
        Call<ProductList> call = client.getMyJSON();
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if(response.isSuccessful()) {
//                   Log.d("RESPONSE", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    /**
                     * Binding that List to Adapter
                     */
                    productList = response.body().getProducts();
                    adapter = new MyProductAdapter(MainProduct.this, productList);
                    listView.setAdapter(adapter);

                } else {
                    Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }

}
