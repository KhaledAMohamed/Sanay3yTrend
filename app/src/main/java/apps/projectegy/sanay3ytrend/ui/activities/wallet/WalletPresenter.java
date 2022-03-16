package apps.projectegy.sanay3ytrend.ui.activities.wallet;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.profile.GetProfileResponse;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class WalletPresenter {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final FragmentManager fragmentManager;
    private final DialogLoader dialogLoader;
    private final WalletInterface walletInterface;


    public WalletPresenter(Context context, WalletInterface walletInterface) {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        this.walletInterface = walletInterface;
    }

    public void getWalletData() {

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
        walletInterface.GetProfileResponse(getProfileResponse);


    }


    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Constant.handleError(context, throwable);
    }


}

