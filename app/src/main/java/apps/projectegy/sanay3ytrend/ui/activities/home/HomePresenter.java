package apps.projectegy.sanay3ytrend.ui.activities.home;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.workersAds.GetWorkersAdsResponse;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final FragmentManager fragmentManager;
    private final DialogLoader dialogLoader;
    private final HomeInterface homeInterface;
    SharedPreferences.Editor editor;

    public HomePresenter(Context context, HomeInterface homeInterface) {
        this.context = context;
        this.homeInterface = homeInterface;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    void getDepartments() {
        if (Validation.isConnected(context)) {

            /*if (!dialogLoader.isAdded()) {
                dialogLoader.show(fragmentManager, "Loader");
            }*/

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                    .getDepartments(Constant.getLng(context))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));

        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }

    }

    void GetSliderWorker() {
        if (Validation.isConnected(context)) {

            /*if (!dialogLoader.isAdded()) {
                dialogLoader.show(fragmentManager, "Loader");
            }*/

            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                    .GetSliderWorker()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));

        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }

    }

    private void handleResponse(GetWorkersAdsResponse getWorkersAdsResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }

        homeInterface.GetWorkersAdsResponse(getWorkersAdsResponse);
    }

    private void handleResponse(GetDepartmentsResponse getDepartmentsResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        homeInterface.GetDepartmentsResponse(getDepartmentsResponse);

    }

    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        Constant.handleError(context, throwable);
    }


}

