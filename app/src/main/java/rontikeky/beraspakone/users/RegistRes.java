package rontikeky.beraspakone.users;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/18/2018.
 */

public class RegistRes {

    @SerializedName("status_message")
    public String statusMessage;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

        public RegistRes (String statusMessage) {
            this.statusMessage = statusMessage;
        }
}
