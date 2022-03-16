package apps.projectegy.sanay3ytrend.data.models.profile;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class Data {

    @SerializedName("pendingWallet")
    private int pendingWallet;

    @SerializedName("address")
    private String address;

    @SerializedName("userStatus")
    private int userStatus;

    @SerializedName("wallet")
    private double wallet;

    @SerializedName("city")
    private String city;

    @SerializedName("whatsAppNumber")
    private String whatsAppNumber;

    @SerializedName("facebook")
    private String facebook;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("start")
    private String start;

    @SerializedName("transferImage")
    private String transferImage;

    @SerializedName("createdOn")
    private String createdOn;

    @SerializedName("twitter")
    private String twitter;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    @SerializedName("phone")
    private String phone;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("end")
    private String end;

    @SerializedName("id")
    private String id;

    @SerializedName("userType")
    private int userType;

    @SerializedName("department")
    private String department;

    @SerializedName("longitude")
    private double longitude;

    public int getPendingWallet() {
        return pendingWallet;
    }

    public void setPendingWallet(int pendingWallet) {
        this.pendingWallet = pendingWallet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTransferImage() {
        return transferImage;
    }

    public void setTransferImage(String transferImage) {
        this.transferImage = transferImage;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
                        "pendingWallet = '" + pendingWallet + '\'' +
                        ",address = '" + address + '\'' +
                        ",userStatus = '" + userStatus + '\'' +
                        ",wallet = '" + wallet + '\'' +
                        ",city = '" + city + '\'' +
                        ",whatsAppNumber = '" + whatsAppNumber + '\'' +
                        ",facebook = '" + facebook + '\'' +
                        ",latitude = '" + latitude + '\'' +
                        ",start = '" + start + '\'' +
                        ",transferImage = '" + transferImage + '\'' +
                        ",createdOn = '" + createdOn + '\'' +
                        ",twitter = '" + twitter + '\'' +
                        ",isDeleted = '" + isDeleted + '\'' +
                        ",phone = '" + phone + '\'' +
                        ",imageUrl = '" + imageUrl + '\'' +
                        ",name = '" + name + '\'' +
                        ",end = '" + end + '\'' +
                        ",id = '" + id + '\'' +
                        ",userType = '" + userType + '\'' +
                        ",department = '" + department + '\'' +
                        ",longitude = '" + longitude + '\'' +
                        "}";
    }
}