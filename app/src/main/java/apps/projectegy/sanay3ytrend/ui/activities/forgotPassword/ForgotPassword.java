package apps.projectegy.sanay3ytrend.ui.activities.forgotPassword;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.RegularEditText;
import apps.projectegy.sanay3ytrend.utils.RegularTextView;

public class ForgotPassword extends AppCompatActivity implements ForgotPasswordInterface {
    ForgetPasswordPresenter forgetPasswordPresenter;

    private RegularEditText etPhone;
    private RegularTextView btnForgotPassword;
    private BoldTextView resendTv;
    private BoldTextView timer;
    private LinearLayoutCompat txtLogin;
    private LinearLayoutCompat backLin;
    private BoldTextView phoneTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etPhone = (RegularEditText) findViewById(R.id.et_phone);
        btnForgotPassword = (RegularTextView) findViewById(R.id.btn_forgot_password);
        resendTv = (BoldTextView) findViewById(R.id.resend_tv);
        timer = (BoldTextView) findViewById(R.id.timer);
        txtLogin = (LinearLayoutCompat) findViewById(R.id.txt_login);
        backLin = (LinearLayoutCompat) findViewById(R.id.back_lin);

        phoneTxt = (BoldTextView) findViewById(R.id.phone_txt);


        forgetPasswordPresenter = new ForgetPasswordPresenter(this, this);
        forgetPasswordPresenter.getPhoneNumber();


        btnForgotPassword.setOnClickListener(view -> {
            forgetPasswordPresenter.forgetPassword(etPhone.getText().toString());
        });
        backLin.setOnClickListener(view -> finish());
        txtLogin.setOnClickListener(view -> finish());

    }

    @Override
    public void getPhoneNumber(String phone) {
        phoneTxt.setText(phone);
    }
}
