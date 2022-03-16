package apps.projectegy.sanay3ytrend.data.models.profile;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class GetProfileResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("errorMessage")
    private String errorMessage;

    @SerializedName("errorCode")
    private int errorCode;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return
                "GetProfileResponse{" +
                        "data = '" + data + '\'' +
                        ",errorMessage = '" + errorMessage + '\'' +
                        ",errorCode = '" + errorCode + '\'' +
                        "}";
    }
}