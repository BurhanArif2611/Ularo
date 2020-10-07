package com.pixelmarketo.ularo.userContent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.activities.OtpActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
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

public class UserProfileActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.imgprofile)
    CircleImageView imgprofile;
    @BindView(R.id.etname)
    TextInputEditText etname;
    @BindView(R.id.etmobile)
    TextInputEditText etmobile;
    @BindView(R.id.etaddress)
    TextInputEditText etaddress;
    @BindView(R.id.btnregister)
    Button btnregister;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.takeimage)
    ImageView takeimage;
    public static final int PERMISSION_REQUEST_CODE = 1111;
    private static final int REQUEST = 1337;
    public static int SELECT_FROM_GALLERY = 2;
    public static int CAMERA_PIC_REQUEST = 0;
    String id = "";
    private String Camera_bitmap = "";
    private String GOOGLE_API_KEY = "AIzaSyCH49-kUK-qkoMBiMqAvDio-Oumh7GGF4g";
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private File file;

    @Override
    protected int getContentResId() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        titleTxt.setText("Update Profile");
        etname.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
        etmobile.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());
        etaddress.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getEmaiiId());
        Glide.with(this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_user).into(imgprofile);

    }

    @OnClick({R.id.takeimage, R.id.btnregister,R.id.etaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.takeimage:
                selectImage();
                break;
            case R.id.btnregister:
                    form_submit();
                break;
            case R.id.etaddress:
               // googleAutoCompleat();
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
                       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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
        int result1 = ContextCompat.checkSelfPermission(UserProfileActivity.this, CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(UserProfileActivity.this, READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(UserProfileActivity.this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(UserProfileActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
    }

    private void openCameraIntent() {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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
                Uri photoURI = FileProvider.getUriForFile(UserProfileActivity.this, "com.pixelmarketo.ularo.provider", photoFile);
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
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(Camera_bitmap).into(imgprofile);
            try {
                file = Util.getCompressed(this, Camera_bitmap);
                Camera_bitmap = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == SELECT_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            Uri galleryURI = data.getData();
            Glide.with(this).load(galleryURI).into(imgprofile);
            Camera_bitmap = getRealPathFromURIPath(galleryURI, UserProfileActivity.this);
            try {
                file = Util.getCompressed_Gellery(this, Camera_bitmap, galleryURI);
                Camera_bitmap = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.e("place", "Place: " + place.getName() + ", " + place.getId() + " , " + place.getLatLng() + "," + place.toString());
                LatLng latLng = place.getLatLng();
                etaddress.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("place", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
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

    private void form_submit() {
        if (NetworkUtil.isNetworkAvailable(UserProfileActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(UserProfileActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            if (Camera_bitmap.equals("")) {
                ErrorMessage.E("without image"+Camera_bitmap);
                Call<ResponseBody> call = apiService.update_profile(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(),etname.getText().toString(),etmobile.getText().toString(),etaddress.getText().toString());

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
                                    ErrorMessage.T(UserProfileActivity.this, object.getString("message"));
                                    JSONObject jsonObject1 = object.getJSONObject("user_info");
                                    UserProfileModel userProfileModel = new UserProfileModel();
                                    userProfileModel.setDisplayName(jsonObject1.getString("fname"));
                                    userProfileModel.setUser_id(jsonObject1.getString("user_id"));
                                    userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                    userProfileModel.setEmaiiId(jsonObject1.getString("address"));
                                    userProfileModel.setProfile_pic(jsonObject1.getString("profile_image"));

                                    UserProfileHelper.getInstance().delete();
                                    userProfileModel.setProvider("user");
                                    UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("from","User");
                                    ErrorMessage.I_clear(UserProfileActivity.this,UserHomeActivity.class,bundle);
                                } else {
                                    ErrorMessage.E("comes in else");
                                    ErrorMessage.T(UserProfileActivity.this, object.getString("message"));
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
            else {
                ErrorMessage.E("with image"+Camera_bitmap);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etname.getText().toString());
                RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), etmobile.getText().toString());
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), etaddress.getText().toString());
                RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
                File file = new File(Camera_bitmap);
                final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image1",file.getName(), requestfile);
                ErrorMessage.E("body "+file.getName());
                Call<ResponseBody> call = apiService.update_profile_with_image(user_id,name,mobile,address,body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            JSONObject object = null;
                            try {
                                materialDialog.dismiss();
                                object = new JSONObject(response.body().string());
                                ErrorMessage.E("comes in cond" + object.toString());

                                if (object.getString("status").equals("200")) {
                                    ErrorMessage.T(UserProfileActivity.this, object.getString("message"));
                                    JSONObject jsonObject1 = object.getJSONObject("user_info");
                                    UserProfileModel userProfileModel = new UserProfileModel();
                                    userProfileModel.setDisplayName(jsonObject1.getString("fname"));
                                    userProfileModel.setUser_id(jsonObject1.getString("user_id"));
                                    userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                    userProfileModel.setEmaiiId(jsonObject1.getString("address"));
                                    userProfileModel.setProfile_pic(jsonObject1.getString("profile_image"));

                                    UserProfileHelper.getInstance().delete();
                                    userProfileModel.setProvider("user");
                                    UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("from","User");
                                    ErrorMessage.I_clear(UserProfileActivity.this,UserHomeActivity.class,bundle);

                                } else {
                                    ErrorMessage.E("comes in else");
                                    ErrorMessage.T(UserProfileActivity.this, object.getString("message"));
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
            ErrorMessage.T(UserProfileActivity.this, "No Internet");
        }
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

}
