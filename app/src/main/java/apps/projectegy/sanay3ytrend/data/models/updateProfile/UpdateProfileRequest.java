package apps.projectegy.sanay3ytrend.data.models.updateProfile;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class UpdateProfileRequest {

    @SerializedName("Address")
    private String address;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("Twitter")
    private String twitter;

    @SerializedName("Latitude")
    private double latitude;

    @SerializedName("DepartmentId")
    private int departmentId;

    @SerializedName("Facebook")
    private String facebook;

    @SerializedName("Longitude")
    private double longitude;

    @SerializedName("Name")
    private String name;

    @SerializedName("WhatsAppNumber")
    private String whatsAppNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhatsAppNumber() {
        return whatsAppNumber;
    }

    public void setWhatsAppNumber(String whatsAppNumber) {
        this.whatsAppNumber = whatsAppNumber;
    }

    @Override
    public String toString() {
        return
                "UpdateProfileRequest{" +
                        "address = '" + address + '\'' +
                        ",phone = '" + phone + '\'' +
                        ",imageUrl = '" + imageUrl + '\'' +
                        ",twitter = '" + twitter + '\'' +
                        ",latitude = '" + latitude + '\'' +
                        ",departmentId = '" + departmentId + '\'' +
                        ",facebook = '" + facebook + '\'' +
                        ",longitude = '" + longitude + '\'' +
                        ",name = '" + name + '\'' +
                        ",whatsAppNumber = '" + whatsAppNumber + '\'' +
                        "}";
    }
}