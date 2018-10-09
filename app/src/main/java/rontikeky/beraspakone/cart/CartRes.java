package rontikeky.beraspakone.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/16/2018.
 */

public class CartRes {
    @SerializedName("id_product")
    @Expose
    private String idProduct;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("qty")
    @Expose
    private String qty;

    public String getIdProduct() { return idProduct; }

    public void setIdProduct (String idProduct) {this.idProduct = idProduct; }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

        public CartRes (String idProduct, String productName, String price, String weight, String description, String qty) {
            this.idProduct   = idProduct;
            this.productName = productName;
            this.price       = price;
            this.weight      = weight;
            this.description = description;
            this.qty         = qty;
        }

}
