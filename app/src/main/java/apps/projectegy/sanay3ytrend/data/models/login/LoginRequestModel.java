package apps.projectegy.sanay3ytrend.data.models.login;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Keep
public class LoginRequestModel {

    @SerializedName("Phone")
    private String Phone;

    @SerializedName("Password")
    private String Password;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    @NotNull
    @Override
    public String toString() {
        return
                "LoginRequestModel{" +
                        "phone = '" + Phone + '\'' +
                        ",password = '" + Password + '\'' +
                        "}";
    }
}