package rontikeky.beraspakone.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import rontikeky.beraspakone.products.Product;

/**
 * Created by Acer on 2/8/2018.
 */

public class LoginRes {
    @SerializedName("user")
    @Expose
    public User user;

    public String status_message;

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public LoginRes(String status_message) {
        this.status_message = status_message;
    }

    @SerializedName("list_address")
    @Expose
    private List<User.ListAddress> listAddress = new ArrayList<>();

    /*
    @return The Product
     */

    public List<User.ListAddress> getListAddress() {
        return listAddress;
    }

    /*
    @params Product the Product
     */

    public void setListAddress(ArrayList<User.ListAddress> products) {
        this.listAddress = listAddress;
    }

}
