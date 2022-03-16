package apps.projectegy.sanay3ytrend.data.models.workersAds;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep

public class DataItem {

    @SerializedName("blance")
    private int blance;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("id")
    private int id;

    @SerializedName("createdOn")
    private String createdOn;

    @SerializedName("userId")
    private String userId;

    @SerializedName("workerId")
    private String workerId;

    @SerializedName("profit")
    private int profit;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public int getBlance() {
        return blance;
    }

    public void setBlance(int blance) {
        this.blance = blance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "blance = '" + blance + '\'' +
                        ",imageUrl = '" + imageUrl + '\'' +
                        ",id = '" + id + '\'' +
                        ",createdOn = '" + createdOn + '\'' +
                        ",userId = '" + userId + '\'' +
                        ",profit = '" + profit + '\'' +
                        "}";
    }
}