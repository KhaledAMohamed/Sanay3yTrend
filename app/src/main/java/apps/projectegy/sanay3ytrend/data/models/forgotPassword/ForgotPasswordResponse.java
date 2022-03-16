package apps.projectegy.sanay3ytrend.data.models.forgotPassword;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class ForgotPasswordResponse {

    @SerializedName("data")
    private String data;

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("errorMessage")
    private String errorMessage;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return
                "ForgotPasswordResponse{" +
                        "data = '" + data + '\'' +
                        ",errorCode = '" + errorCode + '\'' +
                        ",errorMessage = '" + errorMessage + '\'' +
                        "}";
    }
}