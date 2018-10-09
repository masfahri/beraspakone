package rontikeky.beraspakone.users;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 2/12/2018.
 */

public class AddressReq {
//    Selama sama gausah make Serialiized Name
    public String action;

    public String id_user;

    public int id_district;

    public String address_name;

    public String first_name;

    public String last_name;

    public String address;

    public String phone;

    public String postal_code;

    public Double lat;

    @SerializedName("long")
    public Double longitude;

    public int place_id;



    public AddressReq(String action, String idUser, String alamatTujuan, String namaDepan, String namaBelakang, String alamat, String notelp, String nopos) {
        this.action         = action;
        this.id_user        = idUser;
        this.id_district    = id_district;
        this.address_name   = alamatTujuan;
        this.first_name     = namaDepan;
        this.last_name      = namaBelakang;
        this.address        = alamat;
        this.phone          = notelp;
        this.postal_code    = nopos;
        this.lat            = lat;
        this.longitude      = longitude;
        this.place_id       = place_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
        }

        public int getId_district() {
            return id_district;
        }

        public void setId_district(int id_district) {
            this.id_district = id_district;
        }

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPostal_code() {
            return postal_code;
        }

        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public int getPlace_id() {
            return place_id;
        }

        public void setPlace_id(int place_id) {
            this.place_id = place_id;
        }

            public AddressReq(int id_user,
                              int id_district,
                              String address_name,
                              String first_name,
                              String last_name,
                              String address,
                              String phone,
                              String postal_code,
                              Double lat,
                              Double longitude,
                              int place_id) {


        }
}
