package apps.projectegy.sanay3ytrend.data.models.login;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class Data {

    @SerializedName("address")
    private String address;

    @SerializedName("wallet")
    private double wallet;

    @SerializedName("city")
    private String city;

    @SerializedName("whatsAppNumber")
    private String whatsAppNumber;

    @SerializedName("phone")
    private String phone;

    @SerializedName("cityId")
    private String cityId;

    @SerializedName("departmentId")
    private String departmentId;

    @SerializedName("facebook")
    private String facebook;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("userName")
    private String userName;

    @SerializedName("token")
    private String token;

    @SerializedName("twitter")
    private String twitter;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("id")
    private String id;

    @SerializedName("department")
    private String department;

    @SerializedName("email")
    private String email;

    @SerializedName("UserType")
    private String UserType;

    @SerializedName("longitude")
    private double longitude;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
                "Data{" +
                        "address = '" + address + '\'' +
                        ",wallet = '" + wallet + '\'' +
                        ",city = '" + city + '\'' +
                        ",whatsAppNumber = '" + whatsAppNumber + '\'' +
                        ",facebook = '" + facebook + '\'' +
                        ",latitude = '" + latitude + '\'' +
                        ",userName = '" + userName + '\'' +
                        ",token = '" + token + '\'' +
                        ",twitter = '" + twitter + '\'' +
                        ",imageUrl = '" + imageUrl + '\'' +
                        ",id = '" + id + '\'' +
                        ",department = '" + department + '\'' +
                        ",email = '" + email + '\'' +
                        ",longitude = '" + longitude + '\'' +
                        "}";
    }
}