package com.pixelmarketo.ularo.bidderContent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.BidderServiceAdapter;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
import com.pixelmarketo.ularo.model.CategoryModel;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class BidderProfileActivity extends BaseActivity {


    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.profile_pic_bid)
    CircleImageView profilePicBid;
    @BindView(R.id.tvfullname)
    TextInputEditText tvfullname;
    @BindView(R.id.tvmobile)
    TextInputEditText tvmobile;
    @BindView(R.id.tvaddress)
    TextInputEditText tvaddress;
    @BindView(R.id.recycler_service)
    RecyclerView recyclerService;
    @BindView(R.id.spinnerrepair)
    Spinner spinnerrepair;

    @BindView(R.id.btnsave)
    Button btnsave;
    ArrayList<Integer> category_id = new ArrayList<>();
    ArrayList<String> yes = new ArrayList<String>();
    ArrayList<String> img_path = new ArrayList<String>();
    ArrayList<String> adhar_img_path = new ArrayList<String>();
    ArrayList<String> user_img_path = new ArrayList<String>();
    String select_repair_type;
    public static final int PERMISSION_REQUEST_CODE = 1111;
    private static final int REQUEST = 1337;
    public static int SELECT_FROM_GALLERY = 2;
    public static int CAMERA_PIC_REQUEST = 0;
    @BindView(R.id.takeimage)
    CircleImageView takeimage;
    @BindView(R.id.stucture_label)
    TextView stuctureLabel;
    @BindView(R.id.showStructure)
    ImageView showStructure;
    @BindView(R.id.structure_type_reg)
    LinearLayout structureTypeReg;
    @BindView(R.id.repair_label)
    TextView repairLabel;
    @BindView(R.id.repair_icn)
    ImageView repairIcn;
    @BindView(R.id.police_label)
    TextView policeLabel;
    @BindView(R.id.police)
    CircleImageView police;
    @BindView(R.id.takeimagepolice)
    CircleImageView takeimagepolice;
    @BindView(R.id.adhar)
    CircleImageView adhar;
    @BindView(R.id.takeimageaadhar)
    CircleImageView takeimageaadhar;

    private String Camera_bitmap = "";
    int flag;
    private String GOOGLE_API_KEY = "AIzaSyCH49-kUK-qkoMBiMqAvDio-Oumh7GGF4g";
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton(null);
        ButterKnife.bind(this);
        titleTxt.setText("Profile");

        tvfullname.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
        tvmobile.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());
        tvaddress.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getEmaiiId());
        Glide.with(this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getAadhar()).placeholder(R.drawable.ic_user).into(adhar);
        Glide.with(this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getPolice()).placeholder(R.drawable.ic_user).into(police);
        Glide.with(this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_user).into(profilePicBid);

        getcategory();
        yes.add(0, "Yes");
        yes.add(1, "No");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BidderProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, yes);
        spinnerrepair.setAdapter(adapter);

        spinnerrepair.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_repair_type = yes.get(position).toString();
                ErrorMessage.E("type" + select_repair_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @OnClick({R.id.takeimagepolice, R.id.takeimageaadhar, R.id.btnsave, R.id.takeimage, R.id.tvaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.takeimagepolice:
                flag = 0;
                selectImage();
                break;
            case R.id.takeimageaadhar:
                flag = 1;
                selectImage();
                break;
            case R.id.takeimage:
                flag = 2;
                selectImage();
                break;
            case R.id.btnsave:
                form_submit();
                break;
            case R.id.tvaddress:
             //   googleAutoCompleat();

                break;
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"From Camera", "From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please choose an Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("From Camera")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkCameraPermission()) openCameraIntent();
                        else requestPermission();
                    } else openCameraIntent();
                } else if (options[item].equals("From Gallery")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkGalleryPermission()) galleryIntent();
                        else requestGalleryPermission();
                    } else galleryIntent();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.create().show();
    }

    private boolean checkCameraPermission() {
        int result1 = ContextCompat.checkSelfPermission(BidderProfileActivity.this, CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(BidderProfileActivity.this, READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(BidderProfileActivity.this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(BidderProfileActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(BidderProfileActivity.this, "com.pixelmarketo.ularo.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, CAMERA_PIC_REQUEST);
            }
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FROM_GALLERY);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,
                ".jpg",
                storageDir);
        Camera_bitmap = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this, "in on result", Toast.LENGTH_SHORT).show();
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
            if (flag == 0) {
                // tvpolicepath.setText("" + Camera_bitmap);
                Glide.with(this).load(Camera_bitmap).into(police);
                try {
                    File file = Util.getCompressed(this, Camera_bitmap);
                    Camera_bitmap = file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                img_path.add(0, Camera_bitmap);
            } else if (flag == 1) {
                // tvaadharpath.setText("" + Camera_bitmap);
                Glide.with(this).load(Camera_bitmap).into(adhar);
                try {
                    File file = Util.getCompressed(this, Camera_bitmap);
                    Camera_bitmap = file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adhar_img_path.add(0, Camera_bitmap);
            } else {
                Glide.with(this).load(Camera_bitmap).into(profilePicBid);
                try {
                    File file = Util.getCompressed(this, Camera_bitmap);
                    Camera_bitmap = file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                user_img_path.add(0, Camera_bitmap);


            }

        } else if (requestCode == SELECT_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            Uri galleryURI = data.getData();

            Camera_bitmap = getRealPathFromURIPath(galleryURI, BidderProfileActivity.this);
            if (flag == 0) {
                Glide.with(this).load(Camera_bitmap).into(police);
                try {
                    File file = Util.getCompressed_Gellery(this, Camera_bitmap, galleryURI);
                    Camera_bitmap = file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                img_path.add(0, Camera_bitmap);
            } else if (flag == 1) {
                Glide.with(this).load(Camera_bitmap).into(adhar);
                try {
                    File file = Util.getCompressed_Gellery(this, Camera_bitmap, galleryURI);
                    Camera_bitmap = file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adhar_img_path.add(0, Camera_bitmap);
            } else {
                Glide.with(this).load(Camera_bitmap).into(profilePicBid);
                try {
                    File file = Util.getCompressed_Gellery(this, Camera_bitmap, galleryURI);
                    Camera_bitmap = file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                user_img_path.add(0, Camera_bitmap);

            }
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.e("place", "Place: " + place.getName() + ", " + place.getId() + " , " + place.getLatLng() + "," + place.toString());
                    LatLng latLng = place.getLatLng();
                    tvaddress.setText(place.getAddress());
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.e("place", status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = null;
        int idx = 0;
        String s = "";
        try {
            cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                return contentURI.getPath();
            } else {
                cursor.moveToFirst();
                idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                s = cursor.getString(idx);
            }
        } catch (IllegalStateException e) {
            Log.e("Exception image", "selected " + e.toString());
        }

        return s;

    }

    public void getcategory() {
        if (NetworkUtil.isNetworkAvailable(BidderProfileActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidderProfileActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);

            Call<ResponseBody> call = apiService.bidder_category();


            call.enqueue(new Callback<ResponseBody>() {
                @SuppressLint("NewApi")
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        Gson gson = new Gson();
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("get" + jsonObject.toString());
                            materialDialog.dismiss();
                            if (jsonObject.getString("status").equals("200")) {
                                String responseData = jsonObject.toString();
                                ErrorMessage.E("responseData" + responseData);

                                CategoryModel categoryModel = gson.fromJson(responseData, CategoryModel.class);

                                BidderServiceAdapter bidderHomeAdapter = new BidderServiceAdapter(BidderProfileActivity.this, categoryModel.getResult(),"bidderprofile");
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(BidderProfileActivity.this, 3);
                                recyclerService.setLayoutManager(gridLayoutManager);
                                recyclerService.setAdapter(bidderHomeAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(BidderProfileActivity.this, "No Internet");
        }
    }

    public void get_category_id(String id) {
        category_id.add(Integer.valueOf(id));
        ErrorMessage.E("category_id[]  " + category_id);

    }

    public void remove_category_id(String id) {
        category_id.remove(new Integer(id));
        ErrorMessage.E("category_id[]  " + category_id);
    }

    public String getid() {
        String item = "", st = "", st1 = "";
        String[] itemsname = new String[category_id.size()];
        for (int index = 0; index < category_id.size(); index++) {
            itemsname[index] = String.valueOf(category_id.get(index));

            item = TextUtils.join(",", itemsname);
            ErrorMessage.E("item  " + item);

        }
        return item;
    }

    private void googleAutoCompleat() {

        // Initialize the SDK
        Places.initialize(this, GOOGLE_API_KEY);
        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        //  List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void form_submit() {
        if (NetworkUtil.isNetworkAvailable(BidderProfileActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidderProfileActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            if (Camera_bitmap.equals("")) {
                ErrorMessage.E("without image" + Camera_bitmap);
                Call<ResponseBody> call = apiService.bidder_update_profile(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), tvfullname.getText().toString(), tvaddress.getText().toString(), getid(), select_repair_type);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ErrorMessage.E("Response" + response.code());

                        if (response.isSuccessful()) {
                            JSONObject object = null;
                            try {
                                materialDialog.dismiss();
                                object = new JSONObject(response.body().string());
                                ErrorMessage.E("comes in cond" + object.toString());
                                if (object.getString("status").equals("200")) {
                                    // ErrorMessage.E("comes in if cond" + object.toString());
                                    ErrorMessage.T(BidderProfileActivity.this, object.getString("message"));
                                    JSONObject jsonObject1 = object.getJSONObject("user_info");
                                    UserProfileModel userProfileModel = new UserProfileModel();
                                    userProfileModel.setDisplayName(jsonObject1.getString("fname"));
                                    userProfileModel.setUser_id(jsonObject1.getString("user_id"));
                                    userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                    userProfileModel.setEmaiiId(jsonObject1.getString("address"));
                                    userProfileModel.setProfile_pic(jsonObject1.getString("profile_image"));
                                    userProfileModel.setPolice(jsonObject1.getString("police_verification_report"));
                                    userProfileModel.setAadhar(jsonObject1.getString("adhar_card"));
                                    UserProfileHelper.getInstance().delete();
                                    userProfileModel.setProvider("Bidder");
                                    UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("from", "Bidder");
                                    ErrorMessage.I_clear(BidderProfileActivity.this, BidderHomeActivity.class, bundle);
                                } else {
                                    ErrorMessage.E("comes in else");
                                    ErrorMessage.T(BidderProfileActivity.this, object.getString("message"));
                                    materialDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                materialDialog.dismiss();
                                ErrorMessage.E("JsonException" + e);
                            } catch (Exception e) {
                                e.printStackTrace();
                                materialDialog.dismiss();
                                ErrorMessage.E("Exceptions" + e);
                            }
                        } else {
                            materialDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        ErrorMessage.E("Falure login" + t);
                        materialDialog.dismiss();
                    }
                });
            } else {
                ErrorMessage.E("with image" + Camera_bitmap);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), tvfullname.getText().toString());
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), tvaddress.getText().toString());
                RequestBody id = RequestBody.create(MediaType.parse("text/plain"), getid());
                RequestBody repair = RequestBody.create(MediaType.parse("text/plain"), select_repair_type);
                RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
                MultipartBody.Part body = null;
                MultipartBody.Part body1 = null;
                MultipartBody.Part body2 = null;
                File file = null;
                for (int i = 0; i < img_path.size(); i++) {
                    file = new File(img_path.get(i));
                    final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    body = MultipartBody.Part.createFormData("police_verification_report", file.getName(), requestfile);
                    ErrorMessage.E("filename " + file.getName());
                }
                for (int i = 0; i < adhar_img_path.size(); i++) {
                    file = new File(adhar_img_path.get(i));
                    final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    body1 = MultipartBody.Part.createFormData("adhar_card_img", file.getName(), requestfile);
                    ErrorMessage.E("filename " + file.getName());
                }
                for (int i = 0; i < user_img_path.size(); i++) {
                    file = new File(user_img_path.get(i));
                    final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    body2 = MultipartBody.Part.createFormData("profile_img", file.getName(), requestfile);
                    ErrorMessage.E("filename " + file.getName());
                }
                Call<ResponseBody> call = apiService.bidder_update_profile_with_image(userid, name, address, id, repair, body, body1, body2);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ErrorMessage.E("Response>>"+response.code());
                        ErrorMessage.E("Response>>"+response.body());
                        ErrorMessage.E("Response>>"+response.message());
                        if (response.isSuccessful()) {
                            JSONObject object = null;
                            try {
                                materialDialog.dismiss();
                                object = new JSONObject(response.body().string());
                                ErrorMessage.E("comes in cond" + object.toString());

                                if (object.getString("status").equals("200")) {
                                    ErrorMessage.T(BidderProfileActivity.this, object.getString("message"));
                                    JSONObject jsonObject1 = object.getJSONObject("user_info");
                                    UserProfileModel userProfileModel = new UserProfileModel();
                                    userProfileModel.setDisplayName(jsonObject1.getString("fname"));
                                    userProfileModel.setUser_id(jsonObject1.getString("user_id"));
                                    userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                    userProfileModel.setEmaiiId(jsonObject1.getString("address"));
                                    userProfileModel.setProfile_pic(jsonObject1.getString("profile_image"));
                                    userProfileModel.setPolice(jsonObject1.getString("police_verification_report"));
                                    userProfileModel.setAadhar(jsonObject1.getString("adhar_card"));

                                    UserProfileHelper.getInstance().delete();
                                    userProfileModel.setProvider("Bidder");
                                    UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("from", "Bidder");
                                    ErrorMessage.I_clear(BidderProfileActivity.this, BidderHomeActivity.class, bundle);

                                } else {
                                    ErrorMessage.E("comes in else");
                                    ErrorMessage.T(BidderProfileActivity.this, object.getString("message"));
                                    materialDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                materialDialog.dismiss();
                                ErrorMessage.E("JsonException" + e);
                            } catch (Exception e) {
                                e.printStackTrace();
                                materialDialog.dismiss();
                                ErrorMessage.E("Exceptions" + e);
                            }
                        } else {
                            materialDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        ErrorMessage.E("Falure login" + t);
                        materialDialog.dismiss();
                    }
                });
            }
        } else {
            ErrorMessage.T(BidderProfileActivity.this, "No Internet");
        }
    }

}
