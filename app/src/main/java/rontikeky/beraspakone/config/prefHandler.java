package rontikeky.beraspakone.config;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Acer on 2/7/2018.
 */

public class prefHandler {
    public static SharedPreferences.Editor editor;
    public static SharedPreferences sharedPreferences;
    public Context context;

    int PRIVATE_MODE = 0;

    private static final String SHAREDPREFNAME = "token";
    private static final String TOKEN          = "token";

    //    private static final String IS_LOGIN    = "isLoggin";
    /* USER */
    private static final String USER_ID = "userId";
    private static final String USER_PASS = "userPass";
    private static final String USER_EMAIL = "emailUser";
    private static final String FRIST_NAME = "fName";
    private static final String LAST_NAME = "lName";
    private static final String IMG_NAME = "imageName";

    /* ALAMAT USER */
    private static final String ADDRESS_USER = "userAddress";
    private static final String NO_PHONE = "phoneNo";
    private static final String ID_ADDRESS_CHECKOUT = "idAddressCheckout";

    /* PRODUCT CART */
    private static final String QTY_CART      = "quantity";

    /* PRODUCT DETAIL */
    private static final String PRODUCT_ID  = "productId";
//    private static final String EMAIL_KEY   =   "emailkey";
//    private static final String TELP_KEY    =   "telpkey";
//    private static final String PASS_KEY    =   "passkey";
//    private static final String NAME_KEY    =   "namekey";

    public prefHandler(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHAREDPREFNAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHAREDPREFNAME, Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public static String setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
        return userId;
    }

    public static String getToken() { return sharedPreferences.getString(TOKEN, ""); }

    public static String setToken(String token) {
        editor.putString(USER_ID, token);
        editor.commit();
        return token;
    }

    public static String getUserId() { return sharedPreferences.getString(USER_ID, ""); }

    public static String setQuantity (String quantity) {
        editor.putString(QTY_CART, quantity);
        editor.commit();
        return quantity;
    }

    public static String getQtyCart() {
        return sharedPreferences.getString(QTY_CART, "");
    }

    public static String setUserEmail(String emailUser) {
        editor.putString(USER_EMAIL, emailUser);
        editor.commit();
        return emailUser;
    }

    public static String getUserEmail() {
        return sharedPreferences.getString(USER_EMAIL, "");
    }

    public static String setAddressUser(String userAddress) {
        editor.putString(ADDRESS_USER, userAddress);
        editor.commit();
        return userAddress;
    }

    public static String getAddressUser() {
        return sharedPreferences.getString(ADDRESS_USER, "");
    }

    public static String setProductId(String productId) {
        editor.putString(PRODUCT_ID, productId);
        editor.commit();
        return productId;
    }

    public static String getProductId() { return sharedPreferences.getString(PRODUCT_ID, ""); }

    public static String getIdAddressCheckout() {
        return ID_ADDRESS_CHECKOUT;
    }

    public static String setIdAddressCheckout(String idAddressCheckout) {
        editor.putString(ID_ADDRESS_CHECKOUT, idAddressCheckout);
        editor.commit();
        return idAddressCheckout;
    }



    public static void setLogout() {
        editor.clear();
        editor.commit();
    }

}