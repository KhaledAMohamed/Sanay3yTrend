package apps.projectegy.sanay3ytrend.ui.activities.forgotPassword;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.forgotPassword.ForgotPasswordRequest;
import apps.projectegy.sanay3ytrend.data.models.forgotPassword.ForgotPasswordResponse;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


class ForgetPasswordPresenter {

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final DialogLoader dialogLoader;
    private final ForgotPasswordInterface forgotPasswordInterface;

    public ForgetPasswordPresenter(Context context, ForgotPasswordInterface forgotPasswordInterface) {
        this.context = context;
        this.forgotPasswordInterface = forgotPasswordInterface;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    void getPhoneNumber() {
        forgotPasswordInterface.getPhoneNumber(sharedPreferences.getString(Constant.PHONE_NUMBER, ""));
    }

    void forgetPassword(String phone) {
        if (Validation.validateFields(phone)) {
            Constant.showErrorDialog(context, context.getString(R.string.phone_error));
        } else {
            if (Validation.isConnected(context)) {

                if (!dialogLoader.isAdded()) {
                    dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
                }

                ForgotPasswordRequest forgetPasswordBody = new ForgotPasswordRequest();
                forgetPasswordBody.setPhone(phone);

                mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                        .ForgotPassword(phone)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            } else {
                Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
            }
        }

    }

    private void handleResponse(ForgotPasswordResponse forgotPasswordResponse) {
        Constant.showSuccessVERFIEDFinishThis(context, context.getResources().getString(R.string.New_Password_sent_successfully));
//        Toast.makeText(context, forgotPasswordResponse.getData(), Toast.LENGTH_LONG).show();
    }

    private void handleError(Throwable throwable) {

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        //  dialogLoaderTwo.dismiss();
        Constant.handleError(context, throwable);

    }

}
