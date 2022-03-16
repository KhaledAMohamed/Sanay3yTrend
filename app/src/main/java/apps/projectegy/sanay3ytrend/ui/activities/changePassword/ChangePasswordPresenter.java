package apps.projectegy.sanay3ytrend.ui.activities.changePassword;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.changePassword.ChangePasswordResponse;
import apps.projectegy.sanay3ytrend.data.models.forgotPassword.ForgotPasswordResponse;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.activities.home.Home;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

class ChangePasswordPresenter {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final FragmentManager fragmentManager;
    private final DialogLoader dialogLoader;

    ChangePasswordPresenter(Context context) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        dialogLoader = new DialogLoader();

    }

    void changePassword(ChangePasswordResponse changePasswordRequest) {
        if (Validation.isConnected(context)) {
            /*if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(fragmentManager, "LOADER");
            }
*/

            if (!dialogLoader.isAdded()) {
                dialogLoader.show(fragmentManager, "Loader");
            }
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                    .ChangePassword(changePasswordRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));

        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }

    }

    private void handleResponse(ForgotPasswordResponse ForgotPasswordResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }

        Constant.showSuccessVERFIED(context, context.getString(R.string.password_changed_Successfully), Home.class);
        //Toast.makeText(context, ForgotPasswordResponse.getMsg(), Toast.LENGTH_SHORT).show();
    }

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Constant.handleError(context, throwable);
    }
}
