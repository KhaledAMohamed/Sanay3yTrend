package apps.projectegy.sanay3ytrend.data.models.login;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class LoginWithFBRequest {

    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return
                "LoginWithFBRequest{" +
                        "accessToken = '" + accessToken + '\'' +
                        "}";
    }
}