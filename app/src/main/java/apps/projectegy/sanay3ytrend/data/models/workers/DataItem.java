package apps.projectegy.sanay3ytrend.data.models.workers;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class DataItem {

    @SerializedName("address")
    private String address;

    @SerializedName("workerId")
    private String workerId;

    @SerializedName("wallet")
    private double wallet;

    @SerializedName("whatsAppNumber")
    private String whatsAppNumber;

    @SerializedName("departmentName")
    private String departmentName;

    @SerializedName("facebook")
    private String facebook;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("start")
    private String start;

    @SerializedName("userName")
    private String userName;

    @SerializedName("twitter")
    private String twitter;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("end")
    private String end;

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("longitude")
    private double longitude;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getWhatsAppNumber() {
        return whatsAppNumber;
    }

    public void setWhatsAppNumber(String whatsAppNumber) {
        this.whatsAppNumber = whatsAppNumber;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "address = '" + address + '\'' +
                        ",wallet = '" + wallet + '\'' +
                        ",whatsAppNumber = '" + whatsAppNumber + '\'' +
                        ",facebook = '" + facebook + '\'' +
                        ",latitude = '" + latitude + '\'' +
                        ",start = '" + start + '\'' +
                        ",userName = '" + userName + '\'' +
                        ",twitter = '" + twitter + '\'' +
                        ",imageUrl = '" + imageUrl + '\'' +
                        ",end = '" + end + '\'' +
                        ",id = '" + id + '\'' +
                        ",email = '" + email + '\'' +
                        ",longitude = '" + longitude + '\'' +
                        "}";
    }
}