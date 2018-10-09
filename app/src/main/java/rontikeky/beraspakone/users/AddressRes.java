package rontikeky.beraspakone.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Acer on 2/12/2018.
 */

public class AddressRes {
    @SerializedName("list_address")
    @Expose
    private List<ListAddress> listAddress = null;

    public List<ListAddress> getListAddress() {
        return listAddress;
    }

    public void setListAddress(List<ListAddress> listAddress) {
        this.listAddress = listAddress;
    }
}
