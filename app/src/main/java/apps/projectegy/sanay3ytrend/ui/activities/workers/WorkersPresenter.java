package apps.projectegy.sanay3ytrend.ui.activities.workers;

import android.content.Context;
import android.content.SharedPreferences;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.workers.GetWorkersResponse;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class WorkersPresenter {

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final DialogLoader dialogLoader;
    private final WorkersInterface workersInterface;
    SharedPreferences.Editor editor;


    public WorkersPresenter(Context context, WorkersInterface workersInterface) {
        this.context = context;
        this.workersInterface = workersInterface;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    void GetDepartmentsResponse() {
        if (Validation.isConnected(context)) {
            /*if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
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

    private void handleResponse(GetDepartmentsResponse allAdsMapResponse) {

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        workersInterface.GetDepartmentsResponse(allAdsMapResponse);
    }

    public void GetWorkers(String departId) {
        if (Validation.isConnected(context)) {
            /*if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
            }*/
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                    .GetWorkers(sharedPreferences.getString(Constant.areaId, "1"), departId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }

    }

    private void handleResponse(GetWorkersResponse getWorkersResponse) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        workersInterface.GetWorkersResponse(getWorkersResponse);
    }

    public void getAllCountries() {
        if (Validation.isConnected(context)) {

                /*if (!dialogLoader.isAdded()) {
                    dialogLoader.show(fragmentManager, "Loader");
                }*/
            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .getCountries(Constant.getLng(context))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }
    }

    private void handleResponse(GetAllCountriesResponse getAllCountriesResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        workersInterface.GetAllCountries(getAllCountriesResponse);

    }

    private void handleError(Throwable throwable) {

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        //  dialogLoaderTwo.dismiss();
        Constant.handleError(context, throwable);

    }

/*
    void sendPush() {
        Token token = new Token();
        token.getToken(context);
        PushNotification pushNotification = new PushNotification();
        pushNotification.setOS("0");
        pushNotification.setToken(sharedPreferences.getString(Constant.devicetoken, ""));
        Log.i("Token", sharedPreferences.getString(Constant.devicetoken, ""));
        handlePush(pushNotification);
    }

    private void handlePush(PushNotification pushNotification) {
        if (Validation.isConnected(context)) {
            // dialogLoaderTwo.show(fragmentManager, "");
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""))
                    .pushNotification(pushNotification)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError2));
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }
    }

    private void handleError2(Throwable throwable) {
    }*/

  /*  private void handleResponse(PushTokenResponse pushResponse) {
        fragInterface.getCountOFNotification(pushResponse.getData().getNotificationsCount());
    }*/

}
