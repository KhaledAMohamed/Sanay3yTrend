package apps.projectegy.sanay3ytrend.ui.activities.verification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.login.UserModel;
import apps.projectegy.sanay3ytrend.data.models.verification.VerificationRequest;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.activities.home.Home;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


class VerificationPresenter {

    private static final String TAG = "allTendersRequest";

    private final DialogLoader dialogLoader;
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;

    VerificationPresenter(Context context) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    void verifyAccount(String code) {
        if (Validation.validateFields(code)) {
            Constant.showErrorDialog(context, context.getString(R.string.code_error));
        } else {
            if (Validation.isConnected(context)) {
                if (code.equals("")) {
                    Constant.showErrorDialog(context, context.getString(R.string.code_error));
                } else if (code.length() != 4) {
                    Constant.showErrorDialog(context, context.getString(R.string.code_length));
                } else {
                    if (!dialogLoader.isAdded())
                        dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
                    VerificationRequest verificationReques = new VerificationRequest();
                    verificationReques.setVerificationCode(Integer.parseInt(code));
                    verificationReques.setPhone(sharedPreferences.getString(Constant.PHONE_NUMBER, ""));
                    mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                            .VerifyAccount(
                                    verificationReques
                            )
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
                }

                /*Log.d("DATATHATSENT", "onCreate: " + sharedPreferences.getString(Constant.Useremail, "")
                        + sharedPreferences.getString(Constant.PASSWORD, "") + code);*/
            } else {
                Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
            }

        }

    }

    private void handleError(Throwable throwable) {
        Constant.handleError(context, throwable);

        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
    }

    private void handleResponse(UserModel verifyBody) {

        Log.d(TAG, "handleResponse: " + verifyBody.toString());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.TOKEN, verifyBody.getData().getToken());
        editor.putString(Constant.PHONE_NUMBER, verifyBody.getData().getWhatsAppNumber());
        editor.putString(Constant.NAME, verifyBody.getData().getUserName());
//        editor.putString(Constant.CurrencyAr, loginResponseModel.getData().getCurrencyAr());
//        editor.putString(Constant.CurrencyEn, loginResponseModel.getData().getCurrencyEn());
//        editor.putString(Constant.countryId, loginResponseModel.getData().getCountryId());
//        editor.putString(Constant.cityId, loginResponseModel.getData().getCityId());
        editor.putString(Constant.areaId, String.valueOf(verifyBody.getData().getCityId()));
        editor.putString(Constant.areaAr, String.valueOf(verifyBody.getData().getCity()));
//        editor.putString(Constant.Email, verifyBody.getData().getEmail());
        if (verifyBody.getData().getImageUrl() != null)
            editor.putString(Constant.ProfileImg, verifyBody.getData().getImageUrl());
        else
            editor.putString(Constant.ProfileImg, "");

//        editor.putString(Constant.NotificationsNumber, loginResponseModel.getData().getNotificationsNumber());
        editor.putString(Constant.Password, "");
        editor.apply();
//        Constant.showSuccessVERFIED(context, context.getResources().getString(R.string.account_verified_successfully), Home.class);

        Intent intent = new Intent(context, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();


    }
}
