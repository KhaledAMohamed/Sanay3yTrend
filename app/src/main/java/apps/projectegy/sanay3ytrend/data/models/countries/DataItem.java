package apps.projectegy.sanay3ytrend.data.models.countries;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep
public class DataItem {

    @SerializedName("id")
    private int id;

    @SerializedName("cities")
    private List<CitiesItem> cities;

    @SerializedName("country")
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CitiesItem> getCities() {
        return cities;
    }

    public void setCities(List<CitiesItem> cities) {
        this.cities = cities;
    }

    public String getName() {
        return country;
    }

    public void setName(String name) {
        this.country = name;
    }

}