package apps.projectegy.sanay3ytrend.ui.activities.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.profile.GetProfileResponse;
import apps.projectegy.sanay3ytrend.ui.activities.onMap.OnMap;
import apps.projectegy.sanay3ytrend.ui.adapters.areaDialog.areaDialogAdapter;
import apps.projectegy.sanay3ytrend.ui.adapters.areaDialog.areaDialogItem;
import apps.projectegy.sanay3ytrend.utils.BoldButton;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.ImagePickerActivity;
import apps.projectegy.sanay3ytrend.utils.RegularEditText;
import apps.projectegy.sanay3ytrend.utils.RegularTextView;
import de.hdodenhof.circleimageview.CircleImageView;

import static apps.projectegy.sanay3ytrend.utils.Constant.lat;
import static apps.projectegy.sanay3ytrend.utils.Constant.lng;

public class Profile extends AppCompatActivity implements ProfileInterface {

    ProfilePresenter profilePresenter;
    private AppCompatImageView arrowBackPageTwo;
    private CircleImageView uploadImg;
    private BoldTextView txtType;
    private RegularEditText etUsername;
    private RegularEditText etPhone;
    private RegularTextView etAddress;
    private RegularEditText etFace;
    private RegularEditText etTwitter;
    private RegularTextView btnSave;
    private LinearLayout linParent;
    private ProgressBar progressBar;
    public static final int REQUEST_IMAGE = 100;
    private final ArrayList<areaDialogItem> DialogList = new ArrayList<>();
    private final String[] permissions = new String[]{

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    String departId = "1";
    GetDepartmentsResponse getDepartmentsResponse;
    String image_url1 = "";
    private RegularTextView txtDepart;
    private RecyclerView DialogRecyclerView;
    private areaDialogAdapter filterAreaAdapter1;
    private SharedPreferences sharedPreferences;
    private RegularEditText etWhats;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        MobileAds.initialize(this);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        initViews();
    }

    private void initViews() {
        arrowBackPageTwo = (AppCompatImageView) findViewById(R.id.arrow_back_page_two);
        uploadImg = (CircleImageView) findViewById(R.id.upload_img);
        txtType = (BoldTextView) findViewById(R.id.txt_type);
        etUsername = (RegularEditText) findViewById(R.id.et_username);
        etPhone = (RegularEditText) findViewById(R.id.et_phone);
        etAddress = (RegularTextView) findViewById(R.id.et_address);
        etFace = (RegularEditText) findViewById(R.id.et_face);
        etTwitter = (RegularEditText) findViewById(R.id.et_twitter);
        btnSave = (RegularTextView) findViewById(R.id.btn_save);

        etWhats = (RegularEditText) findViewById(R.id.et_whats);

        linParent = (LinearLayout) findViewById(R.id.lin_parent);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        txtDepart = (RegularTextView) findViewById(R.id.txt_depart);

        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        arrowBackPageTwo.setOnClickListener(v -> {
            finish();
        });


        profilePresenter = new ProfilePresenter(this, this);
        profilePresenter.getProfileData();
        txtDepart.setVisibility(View.VISIBLE);
        profilePresenter.getDepartments();
        departId = sharedPreferences.getString(Constant.DepartId, "1");
        txtDepart.setText(sharedPreferences.getString(Constant.departName, ""));
        progressBar.setVisibility(View.VISIBLE);
        linParent.setVisibility(View.GONE);

        txtDepart.setOnClickListener(view -> {
            chooseDepartments();
        });

        btnSave.setOnClickListener(view -> {
            profilePresenter.register(
                    image_url1
                    , etUsername.getText().toString()
                    , etPhone.getText().toString()
                    , etWhats.getText().toString()
                    , Integer.parseInt(departId)
                    , etAddress.getText().toString()
                    , lat
                    , lng
                    , 0
                    , etFace.getText().toString()
                    , etTwitter.getText().toString()
            );
        });
        uploadImg.setOnClickListener(view -> {
            change_img();
        });
        etAddress.setOnClickListener(view -> {
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

    }


    @SuppressLint("SetTextI18n")
    private void chooseDepartments() {

        android.app.AlertDialog.Builder builder1;
        final android.app.AlertDialog dialog1;
        builder1 = new android.app.AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);

        loadDepartments();
        DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
        filterAreaAdapter1 = new areaDialogAdapter(DialogList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        BoldTextView DialogHeader;

        DialogHeader = (BoldTextView) mview.findViewById(R.id.DialogHeader);

        DialogHeader.setText(getString(R.string.select_department));
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
                    departId = String.valueOf(DialogList.get(Position).id);
                    txtDepart.setText(DialogList.get(Position).CityName);
                    dialog1.dismiss();
                    //basketPresenter.updateArea(areaId, this, getIntent().getStringExtra(Constant.CategoryID));
                }
        );

    }

    private void loadDepartments() {
        DialogList.clear();

        if (getDepartmentsResponse != null) {
            for (int country = 0; country < getDepartmentsResponse.getData().size(); country++) {
                //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                DialogList.add(new areaDialogItem(
                        //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                        getDepartmentsResponse.getData().get(country).getName()
                        , ""
                        , ""
                        , getDepartmentsResponse.getData().get(country).getId()));


            }
        }
    }


    @Override
    public void GetProfileResponse(GetProfileResponse response) {
        progressBar.setVisibility(View.GONE);
        linParent.setVisibility(View.VISIBLE);

        if (response.getData() != null) {
            if (response.getData().getImageUrl() != null) {
                Picasso.get().load(Constant.BASE_URL_HTTP + response.getData().getImageUrl()).into(uploadImg);
            }
            if (response.getData().getName() != null) {
                etUsername.setText(response.getData().getName());
            }
            if (response.getData().getPhone() != null) {
                etPhone.setText(response.getData().getPhone());
            }
            if (response.getData().getAddress() != null) {
                etAddress.setText(response.getData().getAddress());
            }
            if (response.getData().getFacebook() != null) {
                etFace.setText(response.getData().getFacebook());
            }
            if (response.getData().getTwitter() != null) {
                etTwitter.setText(response.getData().getTwitter());
            }
            if (response.getData().getWhatsAppNumber() != null) {
                etWhats.setText(response.getData().getWhatsAppNumber());
            }
            lat = response.getData().getLatitude();
            lng = response.getData().getLongitude();
        }


    }

    @Override
    public void GetDepartmentsResponse(GetDepartmentsResponse getDepartmentsResponse) {
        this.getDepartmentsResponse = getDepartmentsResponse;

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    image_url1 = "" + Base64.encodeToString(b, Base64.DEFAULT);
                    uploadImg.setImageURI(uri);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("onActivityResult", "Place: " + place.getName() + ", " + place.getId() + " , " + place.getPlusCode());
                //etAddress.setText(String.valueOf(place.getAddress()));
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
                etAddress.setText(getAddressAr(lat, lng));

                startActivity(new Intent(this, OnMap.class));
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

    private void change_img() {
        try {
            if (checkPermissions()) {

            } else {
                //final String[] PERMISSIONS_STORAGE = {Manifest.permission.CAMERA};
                //Asking request Permissions
                //ActivityCompat.requestPermissions(Settings.this, PERMISSIONS_STORAGE, 10);
                //Toast.makeText(Settings.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
                checkPermissions();
            }
        } catch (Exception e) {
            //Toast.makeText(Settings.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
            checkPermissions();
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), 100);
            return false;
        } else {
            PackageManager pm = this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder;
                AlertDialog dialog;
                builder = new AlertDialog.Builder(this);

                @SuppressLint("InflateParams")
                View mview = getLayoutInflater().inflate(R.layout.dialog_upload_image, null);
                builder.setView(mview);
                dialog = builder.create();
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setGravity(Gravity.CENTER);
                }
                dialog.show();

                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                RegularTextView take_photo = mview.findViewById(R.id.take_photo);
                RegularTextView photo_album = mview.findViewById(R.id.photo_album);
                BoldButton cancel = mview.findViewById(R.id.cancel);
                take_photo.setOnClickListener(view -> {
                    dialog.dismiss();
                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);*/
                    launchCameraIntent();
                });
                photo_album.setOnClickListener(view -> {
                    dialog.dismiss();
                    /*Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 99);*/
                    launchGalleryIntent();
                });

                cancel.setOnClickListener(view -> dialog.dismiss());
            }
        }
        return true;
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
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


}