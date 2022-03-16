package apps.projectegy.sanay3ytrend.data.models.login;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class UserModel {

    @SerializedName("data")
    private Data data;

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("errorMessage")
    private String errorMessage;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
                "UserModel{" +
                        "data = '" + data + '\'' +
                        ",errorCode = '" + errorCode + '\'' +
                        ",errorMessage = '" + errorMessage + '\'' +
                        "}";
    }
}