package apps.projectegy.sanay3ytrend.ui.activities.generalAds;

import android.content.Context;
import android.content.SharedPreferences;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.generalAds.GeneralAdsResponse;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class GeneralAdsPresenter {

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final DialogLoader dialogLoader;
    private final GeneralAdsInterface generalAdsInterface;
    SharedPreferences.Editor editor;


    public GeneralAdsPresenter(Context context, GeneralAdsInterface generalAdsInterface) {
        this.context = context;
        this.generalAdsInterface = generalAdsInterface;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    void GetGeneralAds() {
        if (Validation.isConnected(context)) {
            /*if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
            }*/
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                    .GetGeneralAds()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }

    }

    private void handleResponse(GeneralAdsResponse generalAdsResponse) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();
        generalAdsInterface.GeneralAdsResponse(generalAdsResponse);

    }

    private void handleError(Throwable throwable) {

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        Constant.handleError(context, throwable);

    }
}
