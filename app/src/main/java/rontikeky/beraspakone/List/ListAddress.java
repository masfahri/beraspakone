package rontikeky.beraspakone.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import rontikeky.beraspakone.Response.Address;

/**
 * Created by Acer on 4/2/2018.
 */

public class ListAddress {
    @SerializedName("address")
    @Expose
    private List<Address> address = null;

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
