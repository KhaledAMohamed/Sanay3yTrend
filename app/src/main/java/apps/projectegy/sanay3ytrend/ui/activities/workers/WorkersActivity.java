package apps.projectegy.sanay3ytrend.ui.activities.workers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.workers.GetWorkersResponse;
import apps.projectegy.sanay3ytrend.ui.activities.workerDetails.WorkerDetails;
import apps.projectegy.sanay3ytrend.ui.adapters.areaDialog.areaDialogAdapter;
import apps.projectegy.sanay3ytrend.ui.adapters.areaDialog.areaDialogItem;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.Constant;

public class WorkersActivity extends FragmentActivity implements OnMapReadyCallback, WorkersInterface {


    private static final LatLngBounds NETHERLANDS = new LatLngBounds(
            new LatLng(20.776143, 46.586704), new LatLng(29.725310, 41.499720));
    private final ArrayList<areaDialogItem> DialogList = new ArrayList<>();
    FragmentManager fragmentManager;
    GoogleMap googleMap;
    WorkersPresenter homeFragPresenter;
    List<Marker> marker = new ArrayList<>(); //change length of array according to you
    SharedPreferences.Editor editor;
    GetAllCountriesResponse getAllCountriesResponse;
    String cityId = "-1";
    WorkersPresenter workersPresenter;
    //    AllAdsMapResponse allAdsMapResponse;
    private BoldTextView etSearch;
    private LinearLayoutCompat linCardInfo;
    private AppCompatImageView img;
    private BoldTextView name;
    private BoldTextView job;
    private BoldTextView address;


    private SharedPreferences sharedPreferences;
    private RecyclerView DialogRecyclerView;
    private areaDialogAdapter filterAreaAdapter1;

    GetWorkersResponse getWorkersResponse;

    SupportMapFragment mapFragment;

    private ProgressBar progressBar;

    private BoldTextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        noData = findViewById(R.id.no_data);

        etSearch = findViewById(R.id.et_search);
        linCardInfo = (LinearLayoutCompat) findViewById(R.id.lin_card_info);
        img = (AppCompatImageView) findViewById(R.id.img);
        name = (BoldTextView) findViewById(R.id.name);
        job = (BoldTextView) findViewById(R.id.job);
        address = (BoldTextView) findViewById(R.id.address);

        linCardInfo.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progress_bar);


        etSearch.setText(sharedPreferences.getString(Constant.areaAr, ""));
        cityId = sharedPreferences.getString(Constant.areaId, "");

        workersPresenter = new WorkersPresenter(this, this);
        workersPresenter.GetWorkers(getIntent().getStringExtra(Constant.DepartId));
        workersPresenter.getAllCountries();
        progressBar.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);

        etSearch.setOnClickListener(view1 -> {
            chooseDepartments();
        });

//        homeFragPresenter.sendPush();

    }


    @SuppressLint("SetTextI18n")
    private void chooseDepartments() {

        android.app.AlertDialog.Builder builder1;
        final android.app.AlertDialog dialog1;
        builder1 = new android.app.AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);

        loadCities();
        DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
        filterAreaAdapter1 = new areaDialogAdapter(DialogList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        BoldTextView DialogHeader;

        DialogHeader = mview.findViewById(R.id.DialogHeader);

        DialogHeader.setText(getString(R.string.select_city));
        DialogRecyclerView.setLayoutManager(linearLayoutManager);
        DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DialogRecyclerView.setAdapter(filterAreaAdapter1);
        builder1.setView(mview);
        dialog1 = builder1.create();
        Window window = dialog1.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        dialog1.show();

        filterAreaAdapter1.setOnItemClickListener(v1 ->
                {
                    int Position = DialogRecyclerView.getChildAdapterPosition(v1);
                    cityId = String.valueOf(DialogList.get(Position).id);
                    etSearch.setText(DialogList.get(Position).CityName);
                    dialog1.dismiss();

                    editor.putString(Constant.areaId, String.valueOf(cityId));
                    editor.putString(Constant.areaAr, String.valueOf(DialogList.get(Position).CityName));
                    editor.apply();
                    workersPresenter.GetWorkers(getIntent().getStringExtra(Constant.DepartId));
                    googleMap.clear();
                    progressBar.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);


                }
        );

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
        this.googleMap = googleMap;
    }

    private String getAddressAr(double lat, double lng) {

        Geocoder geocoder = null;
        Locale loc = new Locale("ar");
        geocoder = new Geocoder(this, loc);
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


    private void loadCities() {
        DialogList.clear();

        if (getAllCountriesResponse != null) {
            for (int country = 0; country < getAllCountriesResponse.getData().size(); country++) {
                //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                DialogList.add(new areaDialogItem(
                        //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                        getAllCountriesResponse.getData().get(0).getCities().get(country).getCity()
                        , ""
                        , ""
                        , getAllCountriesResponse.getData().get(0).getCities().get(country).getId()));


            }
        }
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("onActivityResult", "Place: " + place.getName() + ", " + place.getId() + " , " + place.getPlusCode());
                //etAddress.setText(String.valueOf(place.getAddress()));
                double lat = place.getLatLng().latitude;
                double lng = place.getLatLng().longitude;

                etSearch.setText(getAddressAr(lat, lng));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(lat, lng)) // Sets the center of the map to Maracanã
                        .bearing(300) // Sets the orientation of the camera to look west
                        .tilt(50) // Sets the tilt of the camera to 30 degrees
                        .zoom(8)
                        .build(); // Creates a CameraPosition from the builder

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("onActivityResult", status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i("onActivityResult", "CANCELED");
            }
            /*if (requestCode == PERMISSION_ID) {
                getLastLocation();
            }*/
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public void GetDepartmentsResponse(GetDepartmentsResponse getDepartmentsResponse) {

    }

    @Override
    public void GetWorkersResponse(GetWorkersResponse getWorkersResponse) {

        this.getWorkersResponse = getWorkersResponse;
        Log.i("getWorkersResponse", getWorkersResponse.toString());
        List<SampleClusterItem> clusterItems = new ArrayList<>();


        for (int i = 0; i < getWorkersResponse.getData().size(); i++) {
            LatLng latLng = new LatLng(getWorkersResponse.getData().get(i).getLatitude()
                    , getWorkersResponse.getData().get(i).getLongitude());

            String imgUrl = "";
            if (getWorkersResponse.getData().get(i).getImageUrl() != null) {
                imgUrl = getWorkersResponse.getData().get(i).getImageUrl();
            }
            clusterItems.add(new SampleClusterItem(
                    latLng
                    , getWorkersResponse.getData().get(i).getWorkerId()
                    , getWorkersResponse.getData().get(i).getAddress()
                    , getWorkersResponse.getData().get(i).getDepartmentName()
                    , getWorkersResponse.getData().get(i).getUserName()
                    , Constant.BASE_URL_HTTP + imgUrl
            ));
            Log.i("ImageUrl", imgUrl);

            marker.add(createMarker(getWorkersResponse.getData().get(i).getLatitude()
                    , getWorkersResponse.getData().get(i).getLongitude(), i
            ));
//            /*dataModel.put("pos", String.valueOf(i));
//            Marker marker = googleMap.addMarker(new MarkerOptions()
//                    .position(latLng)
//                    );
//            markers.put(marker, dataModel);*/
//
//        }
            googleMap.setOnMarkerClickListener(marker -> {

                String pos = marker.getSnippet();


                if (pos != null) {
                    if (clusterItems.get(Integer.parseInt(pos)).getName() != null)
                        name.setText(clusterItems.get(Integer.parseInt(pos)).getName());
                    if (clusterItems.get(Integer.parseInt(pos)).getJob() != null)
                        job.setText(clusterItems.get(Integer.parseInt(pos)).getJob());
                    if (clusterItems.get(Integer.parseInt(pos)).getAddress() != null)
                        address.setText(clusterItems.get(Integer.parseInt(pos)).getAddress());
                    if (clusterItems.get(Integer.parseInt(pos)).getImage() != null)
                        Picasso.get().load(clusterItems.get(Integer.parseInt(pos)).getImage())
                                .placeholder(R.drawable.ic_profile_avatar).error(R.drawable.ic_profile_avatar).into(img);

                    Log.i("clusterItems", clusterItems.get(Integer.parseInt(pos)).getImage());

                    linCardInfo.setVisibility(View.VISIBLE);

                    linCardInfo.setOnClickListener(view1 -> {
                        Intent intent = new Intent(this, WorkerDetails.class);
                        intent.putExtra(Constant.WorkerId, String.valueOf(clusterItems.get(Integer.parseInt(pos)).getId()));
                        startActivity(intent);

                    });
                }

                return false;
            });

            googleMap.setOnMapLoadedCallback(() ->
            {
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(NETHERLANDS, 100))

                if (getWorkersResponse.getData().size() > 0) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(getWorkersResponse.getData().get(0).getLatitude(), getWorkersResponse.getData().get(0).getLongitude())) // Sets the center of the map to Maracanã
                            .bearing(300) // Sets the orientation of the camera to look west
                            .tilt(50) // Sets the tilt of the camera to 30 degrees
                            .zoom(8)
                            .build(); // Creates a CameraPosition from the builder

                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);
                }


            });

            //googleMap.setOnCameraIdleListener(clusterManager);

       /* for (int i = 0; i < 20000; i++) {
            clusterItems.add(new SampleClusterItem(
                    RandomLocationGenerator.generate(NETHERLANDS)));
        }*/
        }

        progressBar.setVisibility(View.GONE);
        if (getWorkersResponse.getData().size() == 0) {
            noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void GetAllCountries(GetAllCountriesResponse getWorkersResponse) {
        this.getAllCountriesResponse = getWorkersResponse;
    }

    protected Marker createMarker(double latitude, double longitude, int pos) {
        // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 9f));

        Marker myMarker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .snippet(String.valueOf(pos))
        );
        return myMarker;

    }


}