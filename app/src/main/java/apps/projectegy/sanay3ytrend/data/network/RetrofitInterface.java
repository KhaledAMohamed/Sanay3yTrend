package apps.projectegy.sanay3ytrend.data.network;


import apps.projectegy.sanay3ytrend.data.models.changePassword.ChangePasswordResponse;
import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.forgotPassword.ForgotPasswordResponse;
import apps.projectegy.sanay3ytrend.data.models.generalAds.GeneralAdsResponse;
import apps.projectegy.sanay3ytrend.data.models.login.LoginRequestModel;
import apps.projectegy.sanay3ytrend.data.models.login.UserModel;
import apps.projectegy.sanay3ytrend.data.models.profile.GetProfileResponse;
import apps.projectegy.sanay3ytrend.data.models.register.RegisterRequestModel;
import apps.projectegy.sanay3ytrend.data.models.updateProfile.UpdateProfileRequest;
import apps.projectegy.sanay3ytrend.data.models.verification.VerificationRequest;
import apps.projectegy.sanay3ytrend.data.models.workerDetails.WorkerDetailsResponse;
import apps.projectegy.sanay3ytrend.data.models.workers.GetWorkersResponse;
import apps.projectegy.sanay3ytrend.data.models.workersAds.GetWorkersAdsResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitInterface {

    //login
    @POST("/api/Account/login")
    Observable<UserModel> Login(@Body LoginRequestModel loginRequestModel, @Query("lang") String lang);

    //VerifyAccount
    @POST("/api/account/Verify")
    Observable<UserModel> VerifyAccount(@Body VerificationRequest verificationRequest);

    //Register
    @POST("/api/Account/Register")
    Observable<ForgotPasswordResponse> Register(@Body RegisterRequestModel registerRequestModel);

    //Register
    @PUT("api/account/UpdateProfile")
    Observable<UserModel> UpdateProfile(@Body UpdateProfileRequest updateProfileRequest, @Query("lang") String lang);

    //ForgotPassword
    @PUT("/api/Account/ForgetPassword")
    Observable<ForgotPasswordResponse> ForgotPassword(@Query("Phone") String PhoneNumber);

    //Get Countries
    @GET("/api/City/GetCountryWithCity")
    Observable<GetAllCountriesResponse> getCountries(@Query("lang") String lang);

    //Get Countries
    @GET("/api/Department/GetDepartmetns")
    Observable<GetDepartmentsResponse> getDepartments(@Query("lang") String lang);

    //Get Worker
    @GET("/api/worker/GetWorker")
    Observable<GetWorkersResponse> GetWorkers(@Query("CityId") String CityId, @Query("DepartmentId") String DepartmentId);

    //Get Worker
    @GET("/api/worker/Details")
    Observable<WorkerDetailsResponse> GetWorkerDetails(@Query("Id") String Id, @Query("lang") String lang);

    //Get Worker
    @GET("/api/slider/GetSliderWorker")
    Observable<GetWorkersAdsResponse> GetSliderWorker();

    //Get Worker
    @GET("/api/slider/GetSlider")
    Observable<GeneralAdsResponse> GetGeneralAds();

    //Get Worker
    @GET("/api/account/GetProfile")
    Observable<GetProfileResponse> GetProfile(@Query("lang") String lang);

    //Change Password
    @PUT("/api/account/ChangePassword")
    Observable<ForgotPasswordResponse> ChangePassword(@Body ChangePasswordResponse changePasswordResponse);


}