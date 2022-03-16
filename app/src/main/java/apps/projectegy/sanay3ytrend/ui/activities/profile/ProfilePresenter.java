package apps.projectegy.sanay3ytrend.ui.activities.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.login.UserModel;
import apps.projectegy.sanay3ytrend.data.models.profile.GetProfileResponse;
import apps.projectegy.sanay3ytrend.data.models.updateProfile.UpdateProfileRequest;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.activities.home.Home;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ProfilePresenter {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final FragmentManager fragmentManager;
    private final DialogLoader dialogLoader;
    private final ProfileInterface profileInterface;
    SharedPreferences.Editor editor;


    public ProfilePresenter(Context context, ProfileInterface profileInterface) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        this.profileInterface = profileInterface;
        editor = sharedPreferences.edit();

    }

    public void getProfileData() {

        if (Validation.isConnected(context)) {
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                    .GetProfile(Constant.getLng(context))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }

    }

    private void handleResponse(GetProfileResponse getProfileResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        profileInterface.GetProfileResponse(getProfileResponse);


    }


    public void getDepartments() {
        if (Validation.isConnected(context)) {

                /*if (!dialogLoader.isAdded()) {
                    dialogLoader.show(fragmentManager, "Loader");
                }*/
            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .getDepartments(Constant.getLng(context))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }
    }

    private void handleResponse(GetDepartmentsResponse getDepartmentsResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        profileInterface.GetDepartmentsResponse(getDepartmentsResponse);

    }


    public void register(
            String img
            , String name
            , String phone
            , String whats
            , int departId
            , String address
            , double lat
            , double lng
            , int userType
            , String facebook
            , String twitter
    ) {
        if (Validation.isConnected(context)) {

            if (Validation.validateFields(name)) {
                Constant.showErrorDialog(context, context.getString(R.string.name_error));
            } else if (Validation.validateFields(phone)) {
                Constant.showErrorDialog(context, context.getString(R.string.phone_error));
            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                Constant.showErrorDialog(context, context.getString(R.string.phone_invalid));
            } else if (Validation.validateFields(address)) {
                Constant.showErrorDialog(context, context.getString(R.string.address_error));
            } else {
                if (userType == 1) {
                    if (departId == -1) {
                        Constant.showErrorDialog(context, context.getString(R.string.please_select_department_first));
                    } else {
                        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
                        updateProfileRequest.setName(name);
                        updateProfileRequest.setPhone(phone);
                        updateProfileRequest.setWhatsAppNumber(whats);
                        updateProfileRequest.setAddress(address);
                        updateProfileRequest.setDepartmentId(departId);
                        updateProfileRequest.setImageUrl(img);
                        updateProfileRequest.setLatitude(lat);
                        updateProfileRequest.setLongitude(lng);
                        updateProfileRequest.setFacebook(facebook);
                        updateProfileRequest.setTwitter(twitter);

                        Log.i("registerRequestModel", updateProfileRequest.toString());

                        if (!dialogLoader.isAdded()) {
                            dialogLoader.show(fragmentManager, "Loader");
                        }

                        mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                                .UpdateProfile(updateProfileRequest, "ar")
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::handleResponse, this::handleError));
                    }
                } else {
                    UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
                    updateProfileRequest.setName(name);
                    updateProfileRequest.setPhone(phone);
                    updateProfileRequest.setDepartmentId(departId);
                    updateProfileRequest.setWhatsAppNumber(whats);
                    updateProfileRequest.setAddress(address);
                    updateProfileRequest.setImageUrl(img);
                    updateProfileRequest.setLatitude(lat);
                    updateProfileRequest.setLongitude(lng);

                    updateProfileRequest.setFacebook(facebook);
                    updateProfileRequest.setTwitter(twitter);
                    Log.i("registerRequestModel", updateProfileRequest.toString());


                    if (!dialogLoader.isAdded()) {
                        dialogLoader.show(fragmentManager, "Loader");
                    }

                    mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                            .UpdateProfile(updateProfileRequest, "ar")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
                }


            }
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }
    }

    private void handleResponse(UserModel userModel) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }

        editor.putString(Constant.whatsAppNumber, userModel.getData().getWhatsAppNumber());
        editor.putString(Constant.NAME, userModel.getData().getUserName());
        editor.putString(Constant.areaId, String.valueOf(userModel.getData().getCityId()));
        editor.putString(Constant.departId, String.valueOf(userModel.getData().getDepartmentId()));
        editor.putString(Constant.departName, String.valueOf(userModel.getData().getDepartment()));
        editor.putString(Constant.areaId, String.valueOf(userModel.getData().getCityId()));
        editor.putString(Constant.areaAr, String.valueOf(userModel.getData().getCity()));

        if (userModel.getData().getPhone() != null)
            editor.putString(Constant.PHONE_NUMBER, userModel.getData().getPhone());
        else
            editor.putString(Constant.PHONE_NUMBER, userModel.getData().getWhatsAppNumber());

        if (userModel.getData().getImageUrl() != null)
            editor.putString(Constant.ProfileImg, userModel.getData().getImageUrl());
        else
            editor.putString(Constant.ProfileImg, "");

        editor.putString(Constant.Password, "");
        editor.apply();


        Constant.showSuccessVERFIED(context,
                context.getResources().getString(R.string.account_updated_successfully)
                , Home.class);

    }

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Constant.handleError(context, throwable);
    }


}

