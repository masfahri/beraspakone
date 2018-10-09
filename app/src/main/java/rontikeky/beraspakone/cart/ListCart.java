package rontikeky.beraspakone.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Acer on 2/16/2018.
 */

public class ListCart {
    @SerializedName("cart")
    @Expose
    private List<CartRes> cartList = null;

    public List<CartRes> getCart() {
        return cartList;
    }

    public void setCart(List<CartRes> cart) {
        this.cartList = cart;
    }
}
