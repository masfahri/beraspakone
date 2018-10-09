package rontikeky.beraspakone.contacts;

/**
 * Created by Acer on 1/19/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("profile_pic")
    @Expose
    public String profilePic;
    @SerializedName("phone")
    @Expose
    public Phone phone;

    public class Phone{
        @SerializedName("mobile")
        @Expose
        private String mobile;

        @SerializedName("home")
        @Expose
        private String home;

        @SerializedName("office")
        @Expose
        private String office;
    }

}
