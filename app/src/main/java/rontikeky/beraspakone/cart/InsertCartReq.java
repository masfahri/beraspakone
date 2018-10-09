package rontikeky.beraspakone.cart;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/16/2018.
 */

public class InsertCartReq {

    @SerializedName("id_user")
    public String idUser;
    @SerializedName("id_product")
    public Integer idProduct;
    @SerializedName("qty")
    public Integer qtyProduct;

        public InsertCartReq(String userId, Integer productId, Integer Quantity) {
            this.idUser     = userId;
            this.idProduct  = productId;
            this.qtyProduct = Quantity;
        }
}
