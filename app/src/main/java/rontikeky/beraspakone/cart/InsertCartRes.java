package rontikeky.beraspakone.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/16/2018.
 */

public class InsertCartRes {
    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public InsertCartRes(String StatusMessage) {
            this.statusMessage = StatusMessage;
        }
}
