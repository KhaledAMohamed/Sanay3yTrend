package apps.projectegy.sanay3ytrend.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import apps.projectegy.sanay3ytrend.R;
import libs.mjn.prettydialog.PrettyDialog;
import retrofit2.HttpException;

import static android.content.Context.MODE_PRIVATE;

public final class Constant {

    public static final String SHARED_PREFERENCE = "MySharedPreference";
    public static final String LANGUAGE = "LANG";
    public static final String BASE_URL_HTTP = "http://projectegy-002-site17.gtempurl.com/".trim();
    public static final String BASE_PATH_MEDIA = "http://projectegy-002-site17.gtempurl.com/".trim();
    public static final String TOKEN = "TOKEN";
    public static final String NAME = "NAME";
    public static final String Email = "Email";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String ImageProfile = "ImageProfile";
    public static final String FlagIntroSlider = "FlagIntroSlider";
    public static final String Password = "Password";
    // my fatora
    // TODO, The following data are using fot testing only, so that when you go live don't forget to replace the following test credentials with the live credentials provided by MyFatoorah.
    public static final String BASE_URL = "https://apidemo.myfatoorah.com/";
    public static final String EMAIL = "apidirectpayment@myfatoorah.com";
    public static final String PASSWORD = "api12345*";
    // TODO, The following data are using fot testing only, so that when you go live don't forget to replace the following test credentials with the live credentials provided by MyFatoorah.
    public static final String BASE_URL_DIRECT_PAYMENT = "https://apidemo.myfatoorah.com/";
    public static final String EMAIL_DIRECT_PAYMENT = "apidirectpayment@myfatoorah.com";
    public static final String PASSWORD_DIRECT_PAYMENT = "api12345*";

//    public static DataItem userAddressModel;

    public static double lat, lng;
    public static final String OrderID = "OrderID";
    public static final String OrderType = "OrderType";
    public static String OrderIDChat;

    public static final String Building = "Building";
    public static final String Floor = "Floor";
    public static final String Apartment = "Apartment";
    public static final String LatSH = "LatSH";
    public static final String LngSH = "LngSH";
    public static final String StoreID = "StoreID";
    public static final String NotificationsNumber = "NotificationsNumber";
    public static final String devicetoken = "devicetoken";
    public static final String ChatType = "ChatType";
    public static final String OrderCode = "OrderCode";
    public static final String IsExternal = "IsExternal";
    public static String home_position = "0";
    public static long DriverId;
    public static Uri uri;
    public static String NotificationType = "0";
    public static String NotificationID = "0";

    public static final String WorkerId = "WorkerId";
    public static final String areaId = "areaId";
    public static final String areaAr = "areaAr";
    public static final String areaEn = "areaEn";
    public static final String countryId = "countryId";
    public static final String DataExra = "DataExra";
    public static final String CatID = "CatID";
    public static final String ProfileImg = "ProfileImg";
    public static final String DepartId = "DepartId";
    public static final String SponsorPhone = "SponsorPhone";
    public static final String departId = "departId";
    public static final String departName = "departName";
    public static final String UserType = "UserType";
    public static final String whatsAppNumber = "whatsAppNumber";


    public static String convertImgToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        Log.d("BASE64", "convertToBase64: " + Base64.encodeToString(imgByte, Base64.DEFAULT));

        return Base64.encodeToString(imgByte, Base64.DEFAULT);

    }

    public static void setSwipeLayourColor(Context context, SwipeRefreshLayout swipeLayourColor) {
        swipeLayourColor.setColorSchemeColors(context.getResources().getColor(R.color.pdlg_color_white));
        swipeLayourColor.setColorSchemeResources(
                R.color.colorAccent,
                R.color.timestamp,
                android.R.color.holo_orange_light,
                android.R.color.black,
                R.color.colorPrimary);
    }

    public static String getLng(Context context) {
        SharedPreferences mSharedPreferences;

        mSharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);
        return mSharedPreferences.getString(Constant.LANGUAGE, "ar");
    }

    public static void changeLang(Context mContext, String countryCode) {
        Resources res = mContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(countryCode.toLowerCase()));
        res.updateConfiguration(conf, dm);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        view = ((AppCompatActivity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


    }

    public static void hideStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = activity.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static void showSuccessVERFIED(Context context, String message, Class<?> cls) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(false);
        prettyDialog
                .setIcon(R.drawable.ic_success_svg)
                .setTitle(message)
                .addButton(context.getString(R.string.done), android.R.color.white, R.color.pdlg_color_green, () -> {
                    ((Activity) context).finish();
                    Intent intent = new Intent(context, cls);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                    //Animatoo.animateSlideRight(context);
                    if (prettyDialog.isShowing()) {
                        prettyDialog.dismiss();
                    }
                })
                .show();
    }

    public static void showSuccessVERFIEDFinishDialog(Context context, String message) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(false);
        prettyDialog
                .setIcon(R.drawable.ic_success_svg)
                .setTitle(message)
                .addButton(context.getString(R.string.done), android.R.color.white, R.color.pdlg_color_green, () -> {
                    /*((Activity) context).finish();
                    Intent intent = new Intent(context, cls);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);*/
                    //Animatoo.animateSlideRight(context);
                    if (prettyDialog.isShowing()) {
                        prettyDialog.dismiss();
                    }

                })
                .show();
    }


    public static void clearStackIntent(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);

        context.startActivity(intent);
        ((AppCompatActivity) context).finish();

    }

    public static void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right); //TODO (Other Option)R.anim.layout_animation

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public static void expand(View view) {
        //set Visible
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, view.getMeasuredHeight(), view);
        mAnimator.start();
    }

    public static void makeUnderlineForText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    public static void collapse(final View view) {

        int finalHeight = view.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, view);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();

    }

    public static ValueAnimator slideAnimator(int start, int end, final View view) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(valueAnimator -> {
            //Update Height
            int value = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = value;
            view.setLayoutParams(layoutParams);
        });
        return animator;
    }

    public static void handleError(Context context, Throwable throwable) {
        String message = "";
        if (throwable instanceof retrofit2.HttpException) {
            try {
                retrofit2.HttpException error = (retrofit2.HttpException) throwable;
                JSONObject jsonObject = new JSONObject(((HttpException) throwable).response().errorBody().string());
                message = jsonObject.getString("errorCode");
                Log.d("handleError", "handleError: " + jsonObject.toString());
            } catch (Exception e) {
                message = throwable.getMessage();
                Log.d("handleError", "handleError: " + e.getMessage());
            }
            Constant.getErrorDependingOnResponse(context, message);

        }
        Log.d("handleError", "handleError: " + throwable.toString());

    }


    private static void getErrorDependingOnResponse(Context context, String response) {
        String message = "";
        boolean flag = false;
        switch (response) {
            case "1":
                message = context.getResources().getString(R.string.TheModelIsInvalid);
                break;
            case "2":
                message = context.getResources().getString(R.string.places_try_again);
                break;
            case "3":
                message = context.getResources().getString(R.string.TransferImageIsRequired);
                break;
            case "4":
                message = context.getResources().getString(R.string.TheUserNotExistOrDeleted);
                break;
            case "5":
                message = context.getResources().getString(R.string.UserIsDeleted);
                break;
            case "6":
                message = context.getResources().getString(R.string.UserIsPending);
                break;
            case "7":
                message = context.getResources().getString(R.string.UserIsRejected);
                break;
            case "8":
                message = context.getResources().getString(R.string.phone_exists_before);
                break;
            case "9":
                message = context.getResources().getString(R.string.ThisPhoneNumberNotExist);
                break;
            case "10":
                message = context.getResources().getString(R.string.YourMoneyInTheWalletNotEnough);
                break;
            case "11":
                message = context.getResources().getString(R.string.TheHelperPhoneNumberNotExist);
                break;
            case "12":
                message = context.getResources().getString(R.string.TheUsernameOrPasswordIsIncorrect);
//                flag = true;
                break;
            case "13":
                message = context.getResources().getString(R.string.TheOldPasswordIsInCorrect);
                break;
            case "14":
                message = context.getResources().getString(R.string.TheVerificationCodeUnvalid);
                break;
            case "15":
                message = context.getResources().getString(R.string.TheCountryNotExistOrDeleted);
                break;
            case "16":
                message = context.getResources().getString(R.string.TheCityNotExistOrDeleted);
                break;
            case "17":
                message = context.getResources().getString(R.string.TheDepartmentNotExistOrDeleted);
                break;
            default:
                message = context.getString(R.string.default_error);
                break;
        }
//        if (flag) {
//            context.startActivity(new Intent(context, Verification.class));
//        } else
        Constant.showErrorDialog(context, message);

        Log.d("ERROR_TAG", "handleError: " + response);
    }


    public static void showSuccessVERFIEDRefreshOrderDetails(Context context, String string, Class<?> cls, String id) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(false);
        prettyDialog
                .setIcon(R.drawable.ic_success_svg)
                .setTitle(string)
                .addButton(context.getString(R.string.done), android.R.color.white, R.color.pdlg_color_green, () -> {
                    ((Activity) context).finish();
                    Intent intent = new Intent(context, cls);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Constant.OrderID, id);
                    context.startActivity(intent);
                    //Animatoo.animateSlideRight(context);
                    if (prettyDialog.isShowing()) {
                        prettyDialog.dismiss();
                    }

                })
                .show();
    }

    public static void showSuccessVERFIEDNoClearFlag(Context context, String message, Class<?> cls) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(false);
        prettyDialog
                .setIcon(R.drawable.ic_success_svg)
                .setTitle(message)
                .addButton(context.getString(R.string.done), android.R.color.white, R.color.pdlg_color_green, () -> {
                    ((Activity) context).finish();
                    Intent intent = new Intent(context, cls);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                    //Animatoo.animateSlideRight(context);
                    if (prettyDialog.isShowing()) {
                        prettyDialog.dismiss();
                    }

                })
                .show();
    }

    public static void showSuccessVERFIEDFinishThis(Context context, String message) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(false);
        prettyDialog
                .setIcon(R.drawable.ic_success_svg)
                .setTitle(message)
                .addButton(context.getString(R.string.done), android.R.color.white, R.color.pdlg_color_green, () -> {
                    ((Activity) context).finish();
                   /* Intent intent = new Intent(context, cls);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);*/
                    //Animatoo.animateSlideRight(context);
                    if (prettyDialog.isShowing()) {
                        prettyDialog.dismiss();
                    }

                })
                .show();
    }

    public static void showErrorDialog(Context context, String message) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(true);
        prettyDialog
                .setIcon(R.drawable.ic_error)
                .setTitle(message)
                .addButton(context.getString(R.string.ok), android.R.color.white, R.color.pdlg_color_red, prettyDialog::dismiss)
                .show();
    }

    public static void showInformationDialog(Context context, String message) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(true);
        prettyDialog
                .setIcon(R.drawable.ic_information)
                .setTitle(message)
                .addButton(context.getString(R.string.ok), android.R.color.white, R.color.black, prettyDialog::dismiss)
                .show();
    }

    public static void showInformationDialogForMenu(Context context, String message) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(true);
        prettyDialog
                .setIcon(R.drawable.ic_information)
                .setTitle(message)
                .addButton(context.getString(R.string.ok), android.R.color.white, R.color.black, () -> {
                    prettyDialog.dismiss();
                    ((AppCompatActivity) context).finish();
                    Animatoo.animateSlideRight(context);
                })
                .show();
    }

    public static void showInformationDialogNotClose(Context context, String message) {
        PrettyDialog prettyDialog = new PrettyDialog(context);

        prettyDialog.setCancelable(false);
        prettyDialog.setCanceledOnTouchOutside(false);
        prettyDialog
                .setIcon(R.drawable.ic_information)
                .setTitle(message)
                .addButton(context.getString(R.string.ok), R.color.white, R.color.colorAccent, prettyDialog::dismiss)
                .show();


    }
}
