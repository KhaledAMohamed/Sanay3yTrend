package apps.projectegy.sanay3ytrend.data.models.generalAds;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class DataItem {

    @SerializedName("modifiedOn")
    private String modifiedOn;

    @SerializedName("blance")
    private int blance;

    @SerializedName("isModified")
    private boolean isModified;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    @SerializedName("deletedOn")
    private String deletedOn;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("link")
    private String link;

    @SerializedName("id")
    private int id;

    @SerializedName("profit")
    private int profit;

    @SerializedName("createdOn")
    private String createdOn;

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public int getBlance() {
        return blance;
    }

    public void setBlance(int blance) {
        this.blance = blance;
    }

    public boolean isIsModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(String deletedOn) {
        this.deletedOn = deletedOn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "modifiedOn = '" + modifiedOn + '\'' +
                        ",blance = '" + blance + '\'' +
                        ",isModified = '" + isModified + '\'' +
                        ",isDeleted = '" + isDeleted + '\'' +
                        ",deletedOn = '" + deletedOn + '\'' +
                        ",imageUrl = '" + imageUrl + '\'' +
                        ",link = '" + link + '\'' +
                        ",id = '" + id + '\'' +
                        ",profit = '" + profit + '\'' +
                        ",createdOn = '" + createdOn + '\'' +
                        "}";
    }
}