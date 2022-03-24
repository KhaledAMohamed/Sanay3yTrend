package apps.projectegy.sanay3ytrend.ui.activities.register;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.forgotPassword.ForgotPasswordResponse;
import apps.projectegy.sanay3ytrend.data.models.register.RegisterRequestModel;
import apps.projectegy.sanay3ytrend.data.network.NetworkUtil;
import apps.projectegy.sanay3ytrend.ui.activities.login.Login;
import apps.projectegy.sanay3ytrend.ui.activities.verification.Verification;
import apps.projectegy.sanay3ytrend.ui.dialogs.DialogLoader;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RegisterPresenter {

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final CompositeSubscription mSubscriptions;
    private final FragmentManager fragmentManager;
    private final DialogLoader dialogLoader;
    private final RegisterInterface registerInterface;

    int UserType;

    public RegisterPresenter(Context context, RegisterInterface registerInterface) {
        this.context = context;
        this.registerInterface = registerInterface;
        dialogLoader = new DialogLoader();
        mSubscriptions = new CompositeSubscription();
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void register(
            String img
            , String name
            , String phone
            , String password
            , int countryId
            , int cityId
            , int departId
            , String address
            , double lat
            , double lng
            , int userType
            , String transImg
            , String helperPhone
    ) {
        if (Validation.isConnected(context)) {

            if (Validation.validateFields(name)) {
                Constant.showErrorDialog(context, context.getString(R.string.name_error));
            } else if (Validation.validateFields(phone)) {
                Constant.showErrorDialog(context, context.getString(R.string.phone_error));
            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                Constant.showErrorDialog(context, context.getString(R.string.phone_invalid));
            } else if (countryId == -1) {
                Constant.showErrorDialog(context, context.getString(R.string.please_select_country_first));
            } else if (cityId == -1) {
                Constant.showErrorDialog(context, context.getString(R.string.please_select_city_first));
            } else if (Validation.validateFields(address)) {
                Constant.showErrorDialog(context, context.getString(R.string.address_error));
            } else if (Validation.validateFields(password)) {
                Constant.showErrorDialog(context, context.getString(R.string.password_error));
            } else {
                if (userType == 1) {
                    if (departId == -1) {
                        Constant.showErrorDialog(context, context.getString(R.string.please_select_department_first));
                    } else {
                        RegisterRequestModel registerRequestModel = new RegisterRequestModel();
                        registerRequestModel.setName(name);
                        registerRequestModel.setPhoneNumber(phone);
                        registerRequestModel.setAddress(address);
                        registerRequestModel.setCityId(cityId);
                        registerRequestModel.setCountryId(countryId);
                        registerRequestModel.setDepartmentId(departId);
                        registerRequestModel.setImage(img);
                        registerRequestModel.setLatitude(lat);
                        registerRequestModel.setLongitude(lng);
                        registerRequestModel.setTransferImage(transImg);
                        registerRequestModel.setUserType(userType);
                        registerRequestModel.setPassword(password);
                        /*if (!helperPhone.equals(""))
                            registerRequestModel.setHelperPhone(helperPhone);*/
                        registerRequestModel.setHelperPhone(sharedPreferences.getString(Constant.SponsorPhone, ""));

                        Log.i("registerRequestModel", registerRequestModel.toString());
                        UserType = userType;
//                registerRequestModel.setCountryId(sharedPreferences.getString(Constant.areaId, ""));
//                registerRequestModel.setPhoneCountryCode(sharedPreferences.getString(Constant.countryCode, ""));

                        if (!dialogLoader.isAdded()) {
                            dialogLoader.show(fragmentManager, "Loader");
                        }

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constant.PHONE_NUMBER, phone);
                        editor.putString(Constant.Password, password);
                        editor.apply();

                        Log.i("name", name);

                        mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                                .Register(registerRequestModel)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::handleResponse, this::handleError));
                    }
                } else {
                    RegisterRequestModel registerRequestModel = new RegisterRequestModel();
                    registerRequestModel.setName(name);
                    registerRequestModel.setPhoneNumber(phone);
                    registerRequestModel.setAddress(address);
                    registerRequestModel.setCityId(cityId);
                    registerRequestModel.setCountryId(countryId);
                    registerRequestModel.setDepartmentId(departId);
                    registerRequestModel.setImage(img);
                    registerRequestModel.setLatitude(lat);
                    registerRequestModel.setLongitude(lng);
                    registerRequestModel.setTransferImage(transImg);
                    registerRequestModel.setUserType(userType);
                    registerRequestModel.setPassword(password);
                    if (!helperPhone.equals(""))
                        registerRequestModel.setHelperPhone(helperPhone);
                    Log.i("registerRequestModel", registerRequestModel.toString());
                    UserType = userType;
//                registerRequestModel.setCountryId(sharedPreferences.getString(Constant.areaId, ""));
//                registerRequestModel.setPhoneCountryCode(sharedPreferences.getString(Constant.countryCode, ""));

                    if (!dialogLoader.isAdded()) {
                        dialogLoader.show(fragmentManager, "Loader");
                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constant.PHONE_NUMBER, phone);
                    editor.putString(Constant.Password, password);
                    editor.apply();

                    Log.i("name", name);

                    mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                            .Register(registerRequestModel)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponse, this::handleError));
                }


            }
        } else {
            Constant.showErrorDialog(context, context.getString(R.string.pls_check_connection));
        }
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
        registerInterface.GetDepartmentsResponse(getDepartmentsResponse);

    }

    private void handleResponse(GetAllCountriesResponse getAllCountriesResponse) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
        registerInterface.GetAllCountries(getAllCountriesResponse);

    }


    private void handleError(Throwable throwable) {
        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }

        Constant.handleError(context, throwable);
    }

    private void handleResponse(ForgotPasswordResponse forgotPasswordResponse) {

        if (dialogLoader.isAdded()) {
            dialogLoader.dismiss();
        }
//        if (UserType == 0) {
//            Constant.showSuccessVERFIEDNoClearFlag(context, context.getResources().getString(R.string.account_created_successfully), Verification.class);
//        }
//        else if (UserType == 1) {
//            Constant.showSuccessVERFIEDNoClearFlag(context, context.getResources().getString(R.string.account_created_successfully_and_waiting_for_confirmation), Login.class);
//
//        }

        Constant.showSuccessVERFIEDNoClearFlag(context, context.getResources().getString(R.string.account_created_successfully_and_waiting_for_confirmation), Login.class);

    }
}
