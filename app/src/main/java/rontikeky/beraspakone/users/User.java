package rontikeky.beraspakone.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer on 2/10/2018.
 */

public class User {
    public String token;
    @SerializedName("id_user")
    @Expose
    public String idUser;
    @SerializedName("id_usergroup")
    @Expose
    public String idUsergroup;
    @SerializedName("user_email")
    @Expose
    public String userEmail;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("birth_date")
    @Expose
    public String birthDate;
    @SerializedName("image_name")
    @Expose
    public String imageName;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("last_login")
    @Expose
    public String lastLogin;
    @SerializedName("last_ip")
    @Expose
    public String lastIp;
    @SerializedName("list_address")
    @Expose
    public List<ListAddress> listAddress;

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String status_message;

    public static class ListAddress {
        @SerializedName("id_address")
        @Expose
        public String idAddress;
        @SerializedName("address_name")
        @Expose
        public String addressName;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("id_district")
        @Expose
        public String idDistrict;
        @SerializedName("district_name")
        @Expose
        public String districtName;
        @SerializedName("id_regency")
        @Expose
        public String idRegency;
        @SerializedName("regency_name")
        @Expose
        public String regencyName;
        @SerializedName("id_province")
        @Expose
        public String idProvince;
        @SerializedName("province_name")
        @Expose
        public String provinceName;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("postal_code")
        @Expose
        public String postalCode;
        @SerializedName("lat")
        @Expose
        public Double lat;
        @SerializedName("long")
        @Expose
        public Double _long;
        @SerializedName("place_id")
        @Expose
        public String placeId;



        public String getIdAddress() {
            return idAddress;
        }

        public void setIdAddress(String idAddress) {
            this.idAddress = idAddress;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdDistrict() {
            return idDistrict;
        }

        public void setIdDistrict(String idDistrict) {
            this.idDistrict = idDistrict;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getIdRegency() {
            return idRegency;
        }

        public void setIdRegency(String idRegency) {
            this.idRegency = idRegency;
        }

        public String getRegencyName() {
            return regencyName;
        }

        public void setRegencyName(String regencyName) {
            this.regencyName = regencyName;
        }

        public String getIdProvince() {
            return idProvince;
        }

        public void setIdProvince(String idProvince) {
            this.idProvince = idProvince;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double get_long() {
            return _long;
        }

        public void set_long(Double _long) {
            this._long = _long;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public ListAddress(String idAddress, String addressName, String address, String phone, Double latitude, Double longitude, String firstName, String lastName) {
            this.idAddress = idAddress;
            this.firstName = firstName;
            this.lastName = lastName;
            this.addressName = addressName;
            this.address = address;
            this.phone = phone;
            this.lat = latitude;
            this._long = longitude;
        }
    }


    public List<ListAddress> getListAddress() {
        return listAddress;
    }

    public void setListAddress(List<ListAddress> listAddress) {
        this.listAddress = listAddress;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUsergroup() {
        return idUsergroup;
    }

    public void setIdUsergroup(String idUsergroup) {
        this.idUsergroup = idUsergroup;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }



    public User (String user_email, String idUser, String status, String status_message, String token) {
        this.token= token;
        this.status = status;
        this.userEmail = user_email;
        this.idUser = idUser;
        this.status_message = status_message;
    }





}
