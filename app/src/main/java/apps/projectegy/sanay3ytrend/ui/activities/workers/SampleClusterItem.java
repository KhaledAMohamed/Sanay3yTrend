package apps.projectegy.sanay3ytrend.ui.activities.workers;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import net.sharewire.googlemapsclustering.ClusterItem;

public class SampleClusterItem implements ClusterItem {

    private LatLng location;

    private String id;

    private String address;

    private String job;

    private String name;

    private String Image;


    public SampleClusterItem(LatLng location, String id, String address, String job, String name, String Image) {
        this.location = location;
        this.id = id;
        this.address = address;
        this.job = job;
        this.name = name;
        this.Image = Image;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public LatLng getLocation() {
        return location;
    }

    @Override
    public double getLatitude() {
        return location.latitude;
    }

    @Override
    public double getLongitude() {
        return location.longitude;
    }

    @Nullable
    @Override
    public String getTitle() {
        return null;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }
}