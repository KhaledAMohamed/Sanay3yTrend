package apps.projectegy.sanay3ytrend.ui.activities.register;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
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

public class Register extends AppCompatActivity implements RegisterInterface {

    private final ArrayList<areaDialogItem> DialogList = new ArrayList<>();
    public static final int REQUEST_IMAGE = 100;
    private final String[] permissions = new String[]{

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    GetAllCountriesResponse getAllCountriesResponse;
    String CountryId = "-1";
    String departId = "1";
    RegisterPresenter registerPresenter;
    int CountryPosition = -1;
    String CityId = "-1";
    int img_pos;
    String image_url1 = "";
    String transUrl = "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BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBFNFt9999NNBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJdt999907z099tBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBFVd995zjtNs8+f999thBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJdtd948Ecc88888E99999tJBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBF999+1u888888888O899999NBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBV999ju888888888888N499999BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBd96Nc88888888888888+89995BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBFd9+G88888888888888888su19pBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBN99W88888888888888888888z19JBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB19yc88888888888888888888sw9JBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBF99u888888888888888888888882tBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB95X88888888888888888888888e5BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBV960888888888888888888888882tBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB19608888888888888888888888859BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBd94U88888888888888888888888I9BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBF996888888888888888888888888o9BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBR99p088888888888888888888888o9pBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB996888888888888888888888888o9pBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB997888888888888888888888888o9NBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB99p888888888888888888888888o9pBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB99p888888888888888888888888o99BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB99o888888888888888888888888I99BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB99r888888888888888888888888g99BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB9oM888888888888888888888888q/8AQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQfefPPPPPPPPPPPPPPPPPPPPPPPPPB/QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQfevPPPPPPPPPPPPPPPPPPPPPPPPPHPQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQfbvPPPPPPPPPPPPPPPPPPPPPPPPPF/QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQfePvPPPPPPPPPPPPPPPPPPPPPPPOHPQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQRffd/PPPPPPPPPPPPPPPPPPPPPPPLtqQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQUffXvPPPPPPPPPPPPPPPPPPPPPPPOPaQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQVfbn/PPPPPPPPPPPPPPPPPPPPPOv8A2kEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEHH30fzzzzzzzzzzzzzzzzzzzzzzn32kEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEFHX3/AM888888888888888888888s99pBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB1990888888888888888888884f99tBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBxt988888888888888888888p9995BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB9Q88888888888888888888U999BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBR8R8888888888888888888+999hBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB9I388888888888888888O9995BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBV9R88888888888888884+999tBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBd9W8888888888888888s+999pBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBR99U8888888888888888s2999pBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBFdd9998888888888888888+9999JBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBdd999oV8888888888888888qV99hBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBR99999288888888888888888qR9pBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBd9999mc888888888888888888+U5BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBFVd99998U8888888888888888888839BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJ9999999r888888888888888888888o9tBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJN999999993888888888888888888888sX95BBBBBBBBBBBBBBBBBBBBBBBBBBN9999999999pX8888888888888888888888u99NBBBBBBBBBBBBBBBBBBBBBBBBR999999999/W+88888888888888888888888s638JBBBBBBBBBBBBBBBBBBBBBBBB9999995+0P8APPPPPPPPPPPPPPPPPPPPPPPPPPPDhQQSQQQQQQQQQQQQQQQQQQQQUdfffeqrPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPmm1QQQQQQQQQQQQQQQQQQQQTfdyz/PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPLPTB/TQQQTQQQQQQQQRSbffbP/PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPHTsPfffXRQQQQRVXffRfPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP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";
    int sponsorType = 0;
    GetDepartmentsResponse getDepartmentsResponse;
    private RegularTextView btnRegister;
    private RegularEditText etUsername;
    private RegularEditText etEmail;
    private RegularEditText etPhone;
    private RegularEditText etPassword;
    private LinearLayoutCompat txtLogin;
    private LinearLayoutCompat backLin;
    private RegularTextView txtCountry;
    private RegularTextView txtCity;
    private RecyclerView DialogRecyclerView;
    private areaDialogAdapter filterAreaAdapter1;
    private CircleImageView uploadImg;
    private RadioGroup radioType;
    private RadioButton radioWorker;
    private RadioButton radioUser;
    private AppCompatImageView img1;
    private AppCompatImageView plus1Img;
    private AppCompatImageView delete1Img;
    private RegularTextView etAddress;
    private CardView linCard;
    private RegularEditText etSponsorPhone;
    private RegularTextView txtDepart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registger);
        initViews();
    }

    private void initViews() {

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        txtLogin = findViewById(R.id.txt_login);
        backLin = findViewById(R.id.back_lin);
        txtCountry = findViewById(R.id.txt_country);
        txtCity = findViewById(R.id.txt_city);
        radioType = findViewById(R.id.radioType);
        radioWorker = findViewById(R.id.radioWorker);
        radioUser = findViewById(R.id.radioUser);
        img1 = findViewById(R.id.img_1);
        plus1Img = findViewById(R.id.plus_1_img);
        delete1Img = findViewById(R.id.delete_1_img);

        uploadImg = (CircleImageView) findViewById(R.id.upload_img);

        txtDepart = (RegularTextView) findViewById(R.id.txt_depart);

        etSponsorPhone = (RegularEditText) findViewById(R.id.et_sponsorPhone);

        linCard = (CardView) findViewById(R.id.lin_card);

        registerPresenter = new RegisterPresenter(this, this);
        registerPresenter.getAllCountries();

        btnRegister.setOnClickListener(view -> {
            registerPresenter.register(
                    image_url1
                    , etUsername.getText().toString()
                    , etPhone.getText().toString()
                    , etPassword.getText().toString()
                    , Integer.parseInt(CountryId)
                    , Integer.parseInt(CityId)
                    , Integer.parseInt(departId)
                    , etAddress.getText().toString()
                    , lat
                    , lng
                    , sponsorType
                    , transUrl
                    , etSponsorPhone.getText().toString()
            );
        });


        radioType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioUser:
                    sponsorType = 0;
                    linCard.setVisibility(View.GONE);
                    txtDepart.setVisibility(View.GONE);
                    break;
                case R.id.radioWorker:
                    sponsorType = 1;
                    linCard.setVisibility(View.GONE);
                    txtDepart.setVisibility(View.VISIBLE);
                    break;
            }
        });

        uploadImg.setOnClickListener(view -> {
            img_pos = 1;
            change_img();
        });
        linCard.setOnClickListener(view -> {
            img_pos = 2;
            change_img();
        });

        backLin.setOnClickListener(view -> {
            finish();
        });
        txtLogin.setOnClickListener(view -> {
            finish();
        });
        txtCountry.setOnClickListener(view -> {
            chooseCountry();
        });
        txtDepart.setOnClickListener(view -> {
            chooseDepartments();
        });
        txtCity.setOnClickListener(view -> {
            if (CountryId.equals("-1")) {
                Constant.showErrorDialog(this, getString(R.string.please_select_country_first));
            } else
                chooseCity();
        });
        etAddress = findViewById(R.id.et_Address);


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

        registerPresenter.getDepartments();
    }

    @Override
    public void GetAllCountries(GetAllCountriesResponse getAllCountriesResponse) {
        this.getAllCountriesResponse = getAllCountriesResponse;
        DialogList.clear();
    }

    @Override
    public void GetDepartmentsResponse(GetDepartmentsResponse getDepartmentsResponse) {
        this.getDepartmentsResponse = getDepartmentsResponse;
    }

    @SuppressLint("SetTextI18n")
    private void chooseCountry() {

        android.app.AlertDialog.Builder builder1;
        final android.app.AlertDialog dialog1;
        builder1 = new android.app.AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
        BoldTextView DialogHeader;

        DialogHeader = (BoldTextView) mview.findViewById(R.id.DialogHeader);

        DialogHeader.setText(getString(R.string.select_country));
        loadCountry();
        DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
        filterAreaAdapter1 = new areaDialogAdapter(DialogList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

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

        filterAreaAdapter1.setOnItemClickListener(v1 -> {
                    CountryPosition = DialogRecyclerView.getChildAdapterPosition(v1);
                    CountryId = String.valueOf(DialogList.get(CountryPosition).id);
                    txtCountry.setText(DialogList.get(CountryPosition).CityName);
                    CityId = "-1";
                    txtCity.setText(getString(R.string.select_city));
                    dialog1.dismiss();
                    //basketPresenter.updateArea(areaId, this, getIntent().getStringExtra(Constant.CategoryID));
                }
        );

    }

    @SuppressLint("SetTextI18n")
    private void chooseCity() {

        android.app.AlertDialog.Builder builder1;
        final android.app.AlertDialog dialog1;
        builder1 = new android.app.AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);
        BoldTextView DialogHeader;

        DialogHeader = (BoldTextView) mview.findViewById(R.id.DialogHeader);

        DialogHeader.setText(getString(R.string.select_city));
        loadCity();
        DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
        filterAreaAdapter1 = new areaDialogAdapter(DialogList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

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
                    CityId = String.valueOf(DialogList.get(Position).id);
                    txtCity.setText(DialogList.get(Position).CityName);
                    dialog1.dismiss();
                    //basketPresenter.updateArea(areaId, this, getIntent().getStringExtra(Constant.CategoryID));
                }
        );

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

    private void loadCountry() {
        DialogList.clear();

        if (getAllCountriesResponse != null) {
            for (int country = 0; country < getAllCountriesResponse.getData().size(); country++) {
                //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                DialogList.add(new areaDialogItem(
                        //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                        getAllCountriesResponse.getData().get(country).getName()
                        , ""
                        , ""
                        , getAllCountriesResponse.getData().get(country).getId()));


            }
        }
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

    private void loadCity() {
        DialogList.clear();
        if (getAllCountriesResponse != null) {
            for (int i = 0; i < getAllCountriesResponse.getData().get(CountryPosition).getCities().size(); i++) {
                if (Constant.getLng(this).equals("ar")) {
                    DialogList.add(new areaDialogItem(
                            //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                            getAllCountriesResponse.getData().get(CountryPosition).getCities().get(i).getCity()
                            , ""
                            , ""
                            , getAllCountriesResponse.getData().get(CountryPosition).getCities().get(i).getId()));
                } else {
                    DialogList.add(new areaDialogItem(
                            //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                            getAllCountriesResponse.getData().get(CountryPosition).getCities().get(i).getCity()
                            , ""
                            , ""
                            , getAllCountriesResponse.getData().get(CountryPosition).getCities().get(i).getId()));
                }
            }
        }
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
                    if (img_pos == 1) {
                        image_url1 = "" + Base64.encodeToString(b, Base64.DEFAULT);
                        uploadImg.setImageURI(uri);


                    } else if (img_pos == 2) {
                        transUrl = "" + Base64.encodeToString(b, Base64.DEFAULT);

                        delete1Img.setVisibility(View.VISIBLE);
                        plus1Img.setVisibility(View.GONE);
                        img1.setVisibility(View.VISIBLE);
                        img1.setOnClickListener(null);

                        img1.setImageURI(uri);

                        delete1Img.setOnClickListener(view -> {
                            image_url1 = "";
                            delete1Img.setVisibility(View.GONE);
                            plus1Img.setVisibility(View.VISIBLE);
                            img1.setVisibility(View.VISIBLE);
                            img1.setOnClickListener(view1 -> change_img());
                            Picasso.get().load(Constant.BASE_URL_HTTP).into(img1);
                        });
                    }


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