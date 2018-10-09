package rontikeky.beraspakone.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Acer on 1/22/2018.
 */

public class Product {

        @SerializedName("id_product")
        @Expose
        public String idProduct;
        @SerializedName("id_store")
        @Expose
        public String idStore;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("weight")
        @Expose
        public String weight;
        @SerializedName("product_description")
        @Expose
        public String productDescription;
        @SerializedName("stock")
        @Expose
        public String stock;
        @SerializedName("store_name")
        @Expose
        public String storeName;
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
        public Object idDistrict;
        @SerializedName("district_name")
        @Expose
        public Object districtName;
        @SerializedName("id_regency")
        @Expose
        public Object idRegency;
        @SerializedName("regency_name")
        @Expose
        public Object regencyName;
        @SerializedName("id_province")
        @Expose
        public Object idProvince;
        @SerializedName("province_name")
        @Expose
        public Object provinceName;
        @SerializedName("phone")
        @Expose
        public String phone;


    public Product(String idProduct, String productName, String description) {
        this.idProduct           = idProduct;
        this.productName         = productName;
        this.productDescription  = description;
//        this.price      = hargaProduk;
//        this.weight     = berat;
    }

    public String getIdProduct() { return idProduct; }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @SerializedName("postal_code")
    @Expose
    public String postalCode;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("long")
    @Expose
    public String _long;
    @SerializedName("place_id")
    @Expose
    public String placeId;

}
