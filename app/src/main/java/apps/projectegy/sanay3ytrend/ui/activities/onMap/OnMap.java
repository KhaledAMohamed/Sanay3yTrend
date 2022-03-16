package apps.projectegy.sanay3ytrend.ui.activities.onMap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.utils.BoldButton;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.RegularTextView;

import static apps.projectegy.sanay3ytrend.utils.Constant.lat;
import static apps.projectegy.sanay3ytrend.utils.Constant.lng;

public class OnMap extends FragmentActivity implements
        OnMapReadyCallback
        , GoogleMap.OnCameraMoveStartedListener
        , GoogleMap.OnCameraMoveListener
        , GoogleMap.OnCameraMoveCanceledListener
        , GoogleMap.OnCameraIdleListener {

    private static final int REQUEST_CODE = 101;
    public static LatLng latLng;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    LinearLayoutCompat getCurrentLocation;
    private GoogleMap mMap;
    private RegularTextView locationMarkertext;
    private BoldTextView etAddress;

//    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_map);
        BoldButton done_marker = findViewById(R.id.doneMarker);
        locationMarkertext = findViewById(R.id.locationMarkertextAddress);
        getCurrentLocation = findViewById(R.id.current_loc);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        getCurrentLocation.setVisibility(View.GONE);
        etAddress = findViewById(R.id.et_address);
        etAddress.setOnClickListener(v -> {
            if (!Places.isInitialized()) {
                Places.initialize(this, getString(R.string.google_maps_key), Locale.US);
            }
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.PLUS_CODE);
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fields)
                    .build(this);
            //FULLSCREEN
            startActivityForResult(intent, 999);
        });


        done_marker.setOnClickListener(v ->
        {
            finish();
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation.setOnClickListener(v -> {
            //getLastLocation();


            /*latLng = new LatLng(Double.parseDouble(sharedPreferences.getString(Constant.LatSH, "29.253585"))
                    , Double.parseDouble(sharedPreferences.getString(Constant.LngSH, "30.253585")));
*/
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng)) // Sets the center of the map to Maracanã
                    .bearing(300) // Sets the orientation of the camera to look west
                    .tilt(50) // Sets the tilt of the camera to 30 degrees
                    .zoom(18)
                    .build(); // Creates a CameraPosition from the builder


            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);
            String addressName = getAddressAr(lat, lng);
            locationMarkertext.setText(addressName);
            etAddress.setText(addressName);

        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        latLng = new LatLng(lat, lng);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng) // Sets the center of the map to Maracanã
                .bearing(300) // Sets the orientation of the camera to look west
                .tilt(50) // Sets the tilt of the camera to 30 degrees
                .zoom(18)
                .build(); // Creates a CameraPosition from the builder


        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);
        String addressName = getAddressAr(lat, lng);
        locationMarkertext.setText(addressName);
        etAddress.setText(addressName);
    }


    private String getAddressAr(double lat, double lng) {
        Geocoder geocoder = null;
        Locale loc = new Locale("ar");
        geocoder = new Geocoder(getApplicationContext(), loc);
        String add = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            add = add.replaceAll("\\d", "");
            add = add.replaceAll("Unnamed Road", "");
            add = add.replaceAll("المملكة العربية", "");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }

    @Override
    public void onCameraIdle() {
        latLng = mMap.getCameraPosition().target;
        lat = latLng.latitude;
        lng = latLng.longitude;
        String addressName = getAddressAr(lat, lng);
        locationMarkertext.setText(addressName);
        etAddress.setText(addressName);
    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraMoveStarted(int i) {
        locationMarkertext.setText(getResources().getString(R.string.loading));

    }

    public void onBackPressed() {
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        /*if (checkPermissions()) {
            getLastLocation();
        }*/

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("onActivityResult", "Place: " + place.getName() + ", " + place.getId() + " , " + place.getPlusCode());
                //etAddress.setText(String.valueOf(place.getAddress()));
                lat = Objects.requireNonNull(place.getLatLng()).latitude;
                lng = place.getLatLng().longitude;

                etAddress.setText(getAddressAr(lat, lng));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(lat, lng)) // Sets the center of the map to Maracanã
                        .bearing(300) // Sets the orientation of the camera to look west
                        .tilt(50) // Sets the tilt of the camera to 30 degrees
                        .zoom(18)
                        .build(); // Creates a CameraPosition from the builder

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("onActivityResult", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i("onActivityResult", "CANCELED");
            }
            /*if (requestCode == PERMISSION_ID) {
                getLastLocation();
            }*/
        }
    }

}