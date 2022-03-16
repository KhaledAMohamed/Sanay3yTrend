package apps.projectegy.sanay3ytrend.data.models.countries;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class CitiesItem {

    @SerializedName("id")
    private int id;

    @SerializedName("city")
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}