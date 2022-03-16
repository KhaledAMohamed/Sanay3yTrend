package apps.projectegy.sanay3ytrend.ui.adapters;

import java.io.Serializable;

public class SliderItem implements Serializable {

    private String description;
    private String imageUrl;

    public SliderItem(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}