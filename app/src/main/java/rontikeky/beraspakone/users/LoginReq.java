package rontikeky.beraspakone.users;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/8/2018.
 */

public class LoginReq {
    @SerializedName("email")
    public String email;
    @SerializedName("pass")
    public String pass;
    @SerializedName("action")
    public String action;
    @SerializedName("id_usergroup")
    public Integer id_usergroup;


    public LoginReq(String email, String password, Integer idUserGroup, String action) {
        this.action = action;
        this.email = email;
        this.pass = password;
        this.id_usergroup=idUserGroup;
    }
}
