package apps.projectegy.sanay3ytrend.data.models.forgotPassword;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class Data {

    @SerializedName("Password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "password = '" + password + '\'' +
                        "}";
    }
}