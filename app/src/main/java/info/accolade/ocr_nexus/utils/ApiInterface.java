package info.accolade.ocr_nexus.utils;

import java.util.List;

import info.accolade.ocr_nexus.modal.CategoryModal;
import info.accolade.ocr_nexus.modal.ComplaintsModal;
import info.accolade.ocr_nexus.modal.DefaultModal;
import info.accolade.ocr_nexus.modal.LicenseModal;
import info.accolade.ocr_nexus.modal.LoginModal;
import info.accolade.ocr_nexus.modal.PermissionModal;
import info.accolade.ocr_nexus.modal.ProfileModal;
import info.accolade.ocr_nexus.modal.RenewModal;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    String BASE_URL = "http://192.168.0.106/ocr-nexus/";

    //register
    @FormUrlEncoded
    @POST("register.php")
    Call<DefaultModal> getRegisterResponse(
            @Field("name") String name,
            @Field("email") String email,
            @Field("number") String number,
            @Field("password") String password
    );

    //update
    @FormUrlEncoded
    @POST("update.php")
    Call<DefaultModal> getUpdateResponse(
            @Field("uid") String uid,
            @Field("password") String password,
            @Field("oldpassword") String oldpassword
    );

    //login
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModal> getLoginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    //forgot
    @FormUrlEncoded
    @POST("forgot.php")
    Call<DefaultModal> getForgotResponse(
            @Field("email") String email
    );

    //profile
    @FormUrlEncoded
    @POST("profile.php")
    Call<ProfileModal> getProfileResponse(
            @Field("uid") String uid
    );

    //complaints
    @FormUrlEncoded
    @POST("complaints.php")
    Call<List<ComplaintsModal>> getComplaintsResponse(
            @Field("userid") String userid
    );

    //permission
    @FormUrlEncoded
    @POST("permission.php")
    Call<List<PermissionModal>> getPermissionResponse(
            @Field("userid") String userid
    );

    //renew
    @FormUrlEncoded
    @POST("renew.php")
    Call<List<RenewModal>> getRenewResponse(
            @Field("userid") String userid
    );

    //license
    @FormUrlEncoded
    @POST("license.php")
    Call<List<LicenseModal>> getLicenseResponse(
            @Field("userid") String userid
    );


    //municipal complaints
    @POST("municipal-complaints.php")
    Call<List<ComplaintsModal>> getMunicipalComplaintsResponse();

    //municipal permission
    @POST("municipal-permission.php")
    Call<List<PermissionModal>> getMunicipalPermissionResponse();

    //municipal renew
    @POST("municipal-renew.php")
    Call<List<RenewModal>> getMunicipalRenewResponse();

    //municipal license
    @POST("municipal-license.php")
    Call<List<LicenseModal>> getMunicipalLicenseResponse();

    //update complaint
    @FormUrlEncoded
    @POST("update-complaint.php")
    Call<DefaultModal> getUpdateComplaintResponse(
            @Field("status") String status,
            @Field("staff") String staff,
            @Field("complaint_no") String complaint_no
    );

    //update permission
    @FormUrlEncoded
    @POST("update-permission.php")
    Call<DefaultModal> getUpdatePermissionResponse(
            @Field("status") String status,
            @Field("staff") String staff,
            @Field("permission_no") String permission_no
    );

    //update license
    @FormUrlEncoded
    @POST("update-license.php")
    Call<DefaultModal> getUpdateLicenseResponse(
            @Field("status") String status,
            @Field("staff") String staff,
            @Field("licence_no") String licence_no,
            @Field("exp") String exp
    );

    //update renew
    @FormUrlEncoded
    @POST("update-renew.php")
    Call<DefaultModal> getUpdateRenewResponse(
            @Field("status") String status,
            @Field("staff") String staff,
            @Field("licence_no") String licence_no,
            @Field("exp") String exp
    );

    //update license
    @FormUrlEncoded
    @POST("police-update-license.php")
    Call<DefaultModal> getUpdatePoliceLicenseResponse(
            @Field("status") String status,
            @Field("police") String police,
            @Field("licence_no") String licence_no
    );

    //update renew
    @FormUrlEncoded
    @POST("police-update-renew.php")
    Call<DefaultModal> getUpdatePoliceRenewResponse(
            @Field("status") String status,
            @Field("police") String police,
            @Field("licence_no") String licence_no
    );

    //police renew
    @POST("police-renew.php")
    Call<List<RenewModal>> getPoliceRenewResponse();

    //police license
    @POST("police-license.php")
    Call<List<LicenseModal>> getPoliceLicenseResponse();

    //add complaint
    @FormUrlEncoded
    @POST("add-complaint.php")
    Call<DefaultModal> getAddComplaintResponse(
            @Field("uid") String uid,
            @Field("type") String type,
            @Field("title") String title,
            @Field("desc") String desc,
            @Field("name") String name,
            @Field("number") String number,
            @Field("address") String address,
            @Field("city") String city,
            @Field("pin") String pin
    );

    //add complaint
    @FormUrlEncoded
    @POST("add-permission.php")
    Call<DefaultModal> getAddPermissionResponse(
            @Field("uid") String uid,
            @Field("type") String type,
            @Field("desc") String desc,
            @Field("name") String name,
            @Field("number") String number,
            @Field("address") String address,
            @Field("validfrom") String validfrom,
            @Field("validto") String validto
    );

    //add complaint
    @FormUrlEncoded
    @POST("add-license.php")
    Call<DefaultModal> getAddLicenseResponse(
            @Field("uid") String uid,
            @Field("type") String type,
            @Field("name") String name,
            @Field("number") String number,
            @Field("address") String address,
            @Field("trade") String trade
    );

    //add complaint
    @FormUrlEncoded
    @POST("add-renew.php")
    Call<DefaultModal> getAddRenewResponse(
            @Field("uid") String uid,
            @Field("type") String type,
            @Field("name") String name,
            @Field("number") String number,
            @Field("address") String address,
            @Field("trade") String trade,
            @Field("exp") String exp,
            @Field("lino") String lino
    );

    //category
    @POST("category.php")
    Call<List<CategoryModal>> getCategoryResponse();
}
