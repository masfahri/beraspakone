package rontikeky.beraspakone.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/16/2018.
 */

public class DeleteCartReq {

    @SerializedName("id_user")
    public String idUser;
    @SerializedName("id_product")
    public String idProduct;

        public DeleteCartReq (String idUser, String idProduct) {
            this.idUser = idUser;
            this.idProduct = idProduct;
        }
}
