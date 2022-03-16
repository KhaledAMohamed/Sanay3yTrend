package apps.projectegy.sanay3ytrend.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.ui.activities.forgotPassword.ForgotPassword;
import apps.projectegy.sanay3ytrend.ui.activities.register.Register;
import apps.projectegy.sanay3ytrend.utils.RegularEditText;
import apps.projectegy.sanay3ytrend.utils.RegularTextView;


public class Login extends AppCompatActivity {

    LoginPresenter loginPresenter;
    private LinearLayoutCompat backLin;
    private RegularEditText etUsername;
    private RegularEditText etPassword;
    private RegularTextView txtForgtPassword;
    private RegularTextView btnLogin;
    private LinearLayoutCompat txtCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        txtForgtPassword = findViewById(R.id.txt_forgt_password);
        btnLogin = findViewById(R.id.btn_login);
        txtCreateAccount = findViewById(R.id.txt_create_account);
        backLin = findViewById(R.id.back_lin);
        loginPresenter = new LoginPresenter(Login.this);

        backLin.setOnClickListener(view -> {
            finish();
        });
        txtCreateAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, Register.class));
        });
        txtForgtPassword.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgotPassword.class));
        });
        btnLogin.setOnClickListener(view -> {
            loginPresenter.signIn(etUsername.getText().toString(), etPassword.getText().toString());
        });


    }
}