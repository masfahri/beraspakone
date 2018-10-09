package rontikeky.beraspakone.app;

import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rontikeky.beraspakone.List.ListAddress;
import rontikeky.beraspakone.Response.Address;
import rontikeky.beraspakone.cart.CartRes;
import rontikeky.beraspakone.cart.DeleteCartReq;
import rontikeky.beraspakone.cart.DeleteCartRes;
import rontikeky.beraspakone.cart.InsertCartReq;
import rontikeky.beraspakone.cart.InsertCartRes;
import rontikeky.beraspakone.cart.ListCart;
import rontikeky.beraspakone.products.ProductList;
import rontikeky.beraspakone.users.AddressReq;
import rontikeky.beraspakone.users.LoginReq;
import rontikeky.beraspakone.users.LoginRes;
import rontikeky.beraspakone.users.PasswordReq;
import rontikeky.beraspakone.users.RegistReq;
import rontikeky.beraspakone.users.RegistRes;

/**
 * Created by Acer on 1/19/2018.
 */

public interface ApiService {
    /**
     * Retro get Annotation from URL
     * and method that will return the List of  ProductList
     */
    @GET("products")
    Call<ProductList> getMyJSON();

    @GET("products")
    Call<ProductList> getDetailProduct(@Query("id_product") String id_product);

    @FormUrlEncoded
    @POST("users")
    Call<LoginRes> cekUser(@Field("action") String action,
                           @Field("email") String email,
                           @Field("pass") String pass,
                           @Field("id_usergroup") Integer id_usergroup);

    @Headers("Accept: application/json")
    @POST("users")
    Call<RegistRes> cekRegis(@Body RegistReq requestRegist);

    @Headers("Accept: application/json")
    @GET("users")
    Call<LoginRes> cekAddress(@Query("id_user") String id_User);

    @Headers("Accept: application/json")
    @GET("users")
    Call<LoginRes> getUserProfile(@Query("action") String action,
                                  @Query("id_user") String id_user);

    @Headers("Accept: application/json")
    @GET("users")
    Call<ListAddress> getUserAddress(@Query("action") String action,
                                     @Query("id_user") String id_user);

    @Headers("Accept: application/json")
    @GET("users")
    Call<ListAddress> getUserAddressDetails(@Query("action") String action,
                                        @Query("id_user") String id_user,
                                        @Query("id_address") String id_address);

    @Headers("Accept: application/json")
    @POST("cart")
    Call<InsertCartRes> insertCart(@Body InsertCartReq insertCartReq);

    @Headers("Accept: application/json")
    @GET("cart")
    Call<ListCart> checkCart(@Query("id_user") String id_User);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "cart", hasBody = true)
    Call<DeleteCartRes> deleteCart(@Field("id_user") String idUser, @Field("id_product") String idProduct);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "cart", hasBody = true)
    Call<DeleteCartRes> updateCart(@Field("id_user") String idUser,
                                   @Field("id_product") String idProduct,
                                   @Field("qty") String qty);

    @FormUrlEncoded
    @PUT("users")
    Call<DeleteCartRes> updatePassword(@Field("action") String action,
                                       @Field("id_user") String id_user,
                                       @Field("old_pass") String old_pass,
                                       @Field("new_pass") String new_pass);

    @FormUrlEncoded
    @POST("users")
    Call<DeleteCartRes> insertAddress(@Field("action") String action,
                                      @Field("id_user") String id_user,
                                      @Field("id_district") String id_district,
                                      @Field("address_name") String address_name,
                                      @Field("first_name") String first_name,
                                      @Field("last_name") String last_name,
                                      @Field("address") String address,
                                      @Field("phone") String phone,
                                      @Field("postal_code") String postal_code,
                                      @Field("lat") String latitude,
                                      @Field("long") String longitude,
                                      @Field("place_id") String place_id
                                    );

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "users", hasBody = true)
    Call<DeleteCartRes> updateAddress(@Field("action") String action,
                                      @Field("id_user") String id_user,
                                      @Field("id_address") String id_address,
                                      @Field("id_district") String id_district,
                                      @Field("address_name") String address_name,
                                      @Field("first_name") String first_name,
                                      @Field("last_name") String last_name,
                                      @Field("address") String address,
                                      @Field("phone") String phone,
                                      @Field("postal_code") String postal_code,
                                      @Field("lat") String latitude,
                                      @Field("long") String longitude,
                                      @Field("place_id") String place_id
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "users", hasBody = true)
    Call<DeleteCartRes> deleteAlamat(@Field("action") String action,
                                     @Field("id_user") String idUser,
                                     @Field("id_address") String idAlamat);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "users", hasBody = true)
    Call<DeleteCartRes> updateUser(@Field("action") String action,
                                   @Field("id_user") String idUser,
                                   @Field("first_name") String first_name,
                                   @Field("last_name") String last_name,
                                   @Field("phone") String phone,
                                   @Field("gender") String gender,
                                   @Field("birth_date") String birth_date);

}
