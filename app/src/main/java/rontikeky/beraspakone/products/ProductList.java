package rontikeky.beraspakone.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by Acer on 1/22/2018.
 */

public class ProductList {
    @SerializedName("products")
    @Expose
    private ArrayList<Product> products = new ArrayList<>();

    /*
    @return The Product
     */

    public ArrayList<Product> getProducts() {
        return products;
    }

    /*
    @params Product the Product
     */

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


}
