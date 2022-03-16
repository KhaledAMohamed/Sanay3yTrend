package apps.projectegy.sanay3ytrend.data.models.verification;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class VerificationRequest {

    @SerializedName("VerificationCode")
    private int verificationCode;

    @SerializedName("Phone")
    private String phone;

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}