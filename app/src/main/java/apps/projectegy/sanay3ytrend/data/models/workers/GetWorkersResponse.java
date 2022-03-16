package apps.projectegy.sanay3ytrend.data.models.workers;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep

public class GetWorkersResponse {

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("errorMessage")
    private String errorMessage;

    @SerializedName("errorCode")
    private int errorCode;

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
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
                "GetWorkersResponse{" +
                        "data = '" + data + '\'' +
                        ",errorMessage = '" + errorMessage + '\'' +
                        ",errorCode = '" + errorCode + '\'' +
                        "}";
    }
}