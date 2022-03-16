package apps.projectegy.sanay3ytrend.data.models.countries;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep
public class GetAllCountriesResponse {

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("errorMessage")
    private String errorMessage;

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
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
                "GetAllCountriesResponse{" +
                        "data = '" + data + '\'' +
                        ",errorCode = '" + errorCode + '\'' +
                        ",errorMessage = '" + errorMessage + '\'' +
                        "}";
    }
}