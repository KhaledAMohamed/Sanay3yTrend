package apps.projectegy.sanay3ytrend.ui.activities.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.login.LoginRequestModel;
import apps.projectegy.sanay3ytrend.data.models.login.UserModel;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.activities.home.Home;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenter {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final FragmentManager fragmentManager;
    private final DialogLoader dialogLoader;
    SharedPreferences.Editor editor;

    public LoginPresenter(Context context) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    void signIn(String phone, String password) {
        if (Validation.isConnected(context)) {


            if (phone.equals("")) {
                Constant.showErrorDialog(context, context.getString(R.string.phone_requires));
            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                Constant.showErrorDialog(context, context.getString(R.string.phone_invalid));
            } else if (password.equals("")) {
                Constant.showErrorDialog(context, context.getString(R.string.password_error));
            } else {
                if (!dialogLoader.isAdded()) {
                    dialogLoader.show(fragmentManager, "Loader");
                }

                editor.putString(Constant.PHONE_NUMBER, phone);
                editor.putString(Constant.Password, password);
                editor.apply();

                LoginRequestModel loginRequest = new LoginRequestModel();
                loginRequest.setPhone(phone);
                loginRequest.setPassword(password);

                mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                        .Login(loginRequest, Constant.getLng(context))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));

                Intent intent = new Intent(context, Home.class);
                context.startActivity(intent);
                dialogLoader.dismiss();

            }
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }


    }
    /*public void signInWithSocialMedia(LoginWithFBRequest loginWithFBRequest) {
        if (Validation.isConnected(context)) {
            if (!dialogLoader.isAdded())
            Log.i("getExternalAccessToken", loginWithFBRequest.getAccessToken());

            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .loginSocialMedia(loginWithFBRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));

        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }

    }
*/

    private void handleResponse(UserModel loginResponseModel) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        editor.putString(Constant.TOKEN, loginResponseModel.getData().getToken());
        editor.putString(Constant.whatsAppNumber, loginResponseModel.getData().getWhatsAppNumber());
        editor.putString(Constant.NAME, loginResponseModel.getData().getUserName());
//        editor.putString(Constant.CurrencyAr, loginResponseModel.getData().getCurrencyAr());
//        editor.putString(Constant.CurrencyEn, loginResponseModel.getData().getCurrencyEn());
//        editor.putString(Constant.countryId, loginResponseModel.getData().getCountryId());
//        editor.putString(Constant.cityId, loginResponseModel.getData().getCityId());
        editor.putString(Constant.areaId, String.valueOf(loginResponseModel.getData().getCityId()));
        editor.putString(Constant.departId, String.valueOf(loginResponseModel.getData().getDepartmentId()));
        editor.putString(Constant.departName, String.valueOf(loginResponseModel.getData().getDepartment()));
        editor.putString(Constant.areaId, String.valueOf(loginResponseModel.getData().getCityId()));
        editor.putString(Constant.areaAr, String.valueOf(loginResponseModel.getData().getCity()));

        if (loginResponseModel.getData().getPhone() != null)
            editor.putString(Constant.PHONE_NUMBER, loginResponseModel.getData().getPhone());
        else
            editor.putString(Constant.PHONE_NUMBER, loginResponseModel.getData().getWhatsAppNumber());

        if (loginResponseModel.getData().getImageUrl() != null)
            editor.putString(Constant.ProfileImg, loginResponseModel.getData().getImageUrl());
        else
            editor.putString(Constant.ProfileImg, "");

//        editor.putString(Constant.NotificationsNumber, loginResponseModel.getData().getNotificationsNumber());
        editor.putString(Constant.Password, "");
        editor.apply();
//        Intent intent = new Intent(context, Home.class);
//        context.startActivity(intent);
        ((Activity) context).finish();

//        dialogLoader.dismiss();

    }

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Constant.handleError(context, throwable);
    }


}

