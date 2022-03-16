package apps.projectegy.sanay3ytrend.data.models.forgotPassword;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class ForgotPasswordRequest {

    @SerializedName("Phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return
                "ForgotPasswordRequest{" +
                        "phone = '" + phone + '\'' +
                        "}";
    }
}