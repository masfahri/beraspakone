package rontikeky.beraspakone.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/11/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListAddress {

    @SerializedName("list_address")
    public List<ListAlamat> listAlamat;

    public static class ListAlamat{
        @SerializedName("id_address")
        @Expose
        private String idAddress;
        @SerializedName("address_name")
        @Expose
        private String addressName;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("id_district")
        @Expose
        private String idDistrict;
        @SerializedName("district_name")
        @Expose
        private String districtName;
        @SerializedName("id_regency")
        @Expose
        private String idRegency;
        @SerializedName("regency_name")
        @Expose
        private String regencyName;
        @SerializedName("id_province")
        @Expose
        private String idProvince;
        @SerializedName("province_name")
        @Expose
        private String provinceName;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("postal_code")
        @Expose
        private String postalCode;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("long")
        @Expose
        private String _long;
        @SerializedName("place_id")
        @Expose
        private String placeId;

        public ListAlamat(String addressName, String address) {
            this.addressName = addressName;
            this.address = address;
        }



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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLong() {
            return _long;
        }

        public void setLong(String _long) {
            this._long = _long;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

    }


    public List<ListAlamat> getListAddress() {
        return listAlamat;
    }



}
