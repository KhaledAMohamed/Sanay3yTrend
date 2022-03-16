package apps.projectegy.sanay3ytrend.data.models.register;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class RegisterRequestModel {

    @SerializedName("Name")
    private String Name;

    @SerializedName("PhoneNumber")
    private String PhoneNumber;

    @SerializedName("DepartmentId")
    private int DepartmentId;

    @SerializedName("Address")
    private String address;

    @SerializedName("CityId")
    private int CityId;

    @SerializedName("CountryId")
    private int CountryId;

    @SerializedName("UserType")
    private int UserType;

    @SerializedName("Latitude")
    private double Latitude;

    @SerializedName("Longitude")
    private double Longitude;

    @SerializedName("Image")
    private String Image;

    @SerializedName("Password")
    private String Password;

    @SerializedName("TransferImage")
    private String TransferImage;

    @SerializedName("HelperPhone")
    private String HelperPhone;

    public String getHelperPhone() {
        return HelperPhone;
    }

    public void setHelperPhone(String helperPhone) {
        HelperPhone = helperPhone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTransferImage() {
        return TransferImage;
    }

    public void setTransferImage(String transferImage) {
        TransferImage = transferImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "RegisterRequestModel{" +
                "Name='" + Name + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", DepartmentId=" + DepartmentId +
                ", address='" + address + '\'' +
                ", CityId=" + CityId +
                ", CountryId=" + CountryId +
                ", UserType=" + UserType +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", Password='" + Password + '\'' +
                ", HelperPhone='" + HelperPhone + '\'' +
                ", Image='" + Image + '\'' +
                ", TransferImage='" + TransferImage + '\'' +
                '}';
    }
}