package apps.projectegy.sanay3ytrend.data.models.changePassword;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class ChangePasswordResponse {

    @SerializedName("NewPassword")
    private String newPassword;

    @SerializedName("OldPassword")
    private String oldPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String toString() {
        return
                "ChangePasswordResponse{" +
                        "newPassword = '" + newPassword + '\'' +
                        ",oldPassword = '" + oldPassword + '\'' +
                        "}";
    }
}