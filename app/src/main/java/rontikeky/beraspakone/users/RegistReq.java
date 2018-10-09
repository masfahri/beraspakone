package rontikeky.beraspakone.users;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/18/2018.
 */

public class RegistReq {


    @SerializedName("action")
    public String action;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("email")
    public String email;
    @SerializedName("pass")
    public String pass;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

        public RegistReq(String action, String fName, String lName, String email, String pass) {
            this.action    = "register";
            this.firstName = fName;
            this.lastName  = lName;
            this.email     = email;
            this.pass  = pass;
        }
}
