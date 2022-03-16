package apps.projectegy.sanay3ytrend.ui.activities.workerDetails;

import android.content.Context;
import android.content.SharedPreferences;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.workerDetails.WorkerDetailsResponse;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class WorkerDetailsPresenter {

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final DialogLoader dialogLoader;
    private final WorkerDetailsInterface workersInterface;
    SharedPreferences.Editor editor;


    public WorkerDetailsPresenter(Context context, WorkerDetailsInterface workersInterface) {
        this.context = context;
        this.workersInterface = workersInterface;
        mSubscriptions = new CompositeSubscription();
        dialogLoader = new DialogLoader();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    void GetWorkerDetails(String workerId) {
        if (Validation.isConnected(context)) {
            /*if (dialogLoader.isAdded()) {
                return;
            } else {
                dialogLoader.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
            }*/
            mSubscriptions.add(NetworkUtil.getRetrofitByToken(sharedPreferences.getString(Constant.TOKEN, ""), context)
                    .GetWorkerDetails(workerId, Constant.getLng(context))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }

    }

    private void handleResponse(WorkerDetailsResponse workerDetailsResponse) {
        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        workersInterface.WorkerDetailsResponse(workerDetailsResponse);
    }

    private void handleError(Throwable throwable) {

        if (dialogLoader.isAdded())
            dialogLoader.dismiss();

        Constant.handleError(context, throwable);

    }
}
