package apps.projectegy.sanay3ytrend.data.models.departments;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class DataItem {

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "image = '" + image + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}