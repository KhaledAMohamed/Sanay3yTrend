package apps.projectegy.sanay3ytrend.ui.activities.changePassword;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.Objects;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.changePassword.ChangePasswordResponse;
import apps.projectegy.sanay3ytrend.utils.BoldButton;
import apps.projectegy.sanay3ytrend.utils.BoldEditText;
import apps.projectegy.sanay3ytrend.utils.Constant;


public class ChangePassword extends AppCompatActivity {


    private ChangePasswordPresenter changePasswordPresenter;

    private BoldEditText old;
    private BoldEditText newPass;
    private BoldEditText confirmPass;
    private BoldButton resetPassword;
    private AppCompatImageView arrowBackPageTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initViews();
    }

    private void initViews() {
        changePasswordPresenter = new ChangePasswordPresenter(this);

        old = findViewById(R.id.old);
        newPass = findViewById(R.id.new_pass);
        confirmPass = findViewById(R.id.confirm_pass);
        resetPassword = findViewById(R.id.reset_password);


        arrowBackPageTwo = findViewById(R.id.arrow_back_page_two);
        arrowBackPageTwo.setOnClickListener(v -> {
            finish();
        });

        resetPassword.setOnClickListener(v -> {
            if (Objects.requireNonNull(old.getText()).toString().equals("")) {
                Constant.showErrorDialog(this, getString(R.string.old_password_is_required));
            } else if (Objects.requireNonNull(newPass.getText()).toString().equals("")) {
                Constant.showErrorDialog(this, getString(R.string.new_password_is_required));
            } else if (Objects.requireNonNull(confirmPass.getText()).toString().equals("")) {
                Constant.showErrorDialog(this, getString(R.string.confirm_password_is_required));
            } else if (!Objects.requireNonNull(newPass.getText()).toString().equals(confirmPass.getText().toString())) {
                Constant.showErrorDialog(this, getString(R.string.password_Not_match));
            } else {
                ChangePasswordResponse changePasswordRequest = new ChangePasswordResponse();
                changePasswordRequest.setOldPassword(old.getText().toString());
                changePasswordRequest.setNewPassword(newPass.getText().toString());
                changePasswordPresenter.changePassword(changePasswordRequest);
            }

        });
    }
}