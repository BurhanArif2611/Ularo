package com.pixelmarketo.ularo.userContent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.TypeOfWorkModel;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.UserAccount;
import com.pixelmarketo.ularo.utility.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class RailingFilterActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;

    @BindView(R.id.servicetype)
    Spinner servicetype;
    @BindView(R.id.btnsend)
    Button btnsend;
    @BindView(R.id.feet)
    EditText feet;
    String id = "", select_type = "";
    ArrayList<String> type = new ArrayList<String>();
    @BindView(R.id.imgpath)
    TextView imgpath;
    public static final int PERMISSION_REQUEST_CODE = 1111;
    private static final int REQUEST = 1337;
    public static int SELECT_FROM_GALLERY = 2;
    public static int CAMERA_PIC_REQUEST = 0;
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    String quick_mode="0";
    private String Camera_bitmap = "";
    private static final String IMAGE_DIRECTORY = "/ullaro";
    private static final int BUFFER_SIZE = 1024 * 2;
    private File file;

    @Override
    protected int getContentResId() {
        return R.layout.activity_railing_filter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("");
        titleTxt.setText("Railing Filter");
        gettype();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ErrorMessage.E("id" + bundle.getString("id"));
            id = bundle.getString("id");
        }
        servicetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_type = type.get(position).toString();
                ErrorMessage.E("type" + select_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void form_submit() {
        if (NetworkUtil.isNetworkAvailable(RailingFilterActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(RailingFilterActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            if (Camera_bitmap.equals("")) {
                Call<ResponseBody> call = apiService.form(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), id, "", "", select_type, "", "", "", "",
                        feet.getText().toString(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",quick_mode);

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
                                    ErrorMessage.T(RailingFilterActivity.this, object.getString("message"));
                                    Bundle bundle = new Bundle();
                                    bundle.putString("from", "User");
                                    ErrorMessage.I_clear(RailingFilterActivity.this, UserHomeActivity.class, bundle);
                                } else {
                                    ErrorMessage.E("comes in else");
                                    ErrorMessage.T(RailingFilterActivity.this, object.getString("message"));
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
                RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
                RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), id);
                RequestBody runningfeet = RequestBody.create(MediaType.parse("text/plain"), feet.getText().toString());
                RequestBody type = RequestBody.create(MediaType.parse("text/plain"), select_type);
                RequestBody quick = RequestBody.create(MediaType.parse("text/plain"), quick_mode);

                File file = new File(Camera_bitmap);
                final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image1", file.getName(), requestfile);//profile pic
                Call<ResponseBody> call = apiService.form_with_image(user_id, category_id, null, null, type, null, runningfeet, null, null, quick, body);

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
                                    ErrorMessage.T(RailingFilterActivity.this, object.getString("message"));
                                    Bundle bundle = new Bundle();
                                    bundle.putString("from", "User");
                                    ErrorMessage.I_clear(RailingFilterActivity.this, UserHomeActivity.class, bundle);
                                } else {
                                    ErrorMessage.E("comes in else");
                                    ErrorMessage.T(RailingFilterActivity.this, object.getString("message"));
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
            ErrorMessage.T(RailingFilterActivity.this, "No Internet");
        }
    }

    public void gettype() {
        if (NetworkUtil.isNetworkAvailable(RailingFilterActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(RailingFilterActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);

            Call<ResponseBody> call = apiService.type_of_work();


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

                                TypeOfWorkModel typeOfWorkModel = gson.fromJson(responseData, TypeOfWorkModel.class);
                                for (int i = 0; i < typeOfWorkModel.getResult().size(); i++) {
                                    type.add(typeOfWorkModel.getResult().get(i).getType_name());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RailingFilterActivity.this, android.R.layout.simple_spinner_dropdown_item, type);
                                servicetype.setAdapter(adapter);
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
            ErrorMessage.T(RailingFilterActivity.this, "No Internet");
        }
    }

    @OnClick({R.id.imgpath, R.id.btnsend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgpath:
                selectImage();
                break;
            case R.id.btnsend:
                if (UserAccount.isEmpty(feet)) {
                    int q1 = radiogroup1.getCheckedRadioButtonId();
                    RadioButton radioButton1 = (RadioButton) findViewById(q1);
                    ErrorMessage.E("1===" + radioButton1.getText());
                    if (radioButton1.getText().equals("yes")) {
                        quick_mode = "1";
                        ErrorMessage.E("quick_mode===" + quick_mode);
                        form_submit();

                    } else {
                        quick_mode = "0";
                        form_submit();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"From Camera", "From Gallery", "From Files", "Cancel"};
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
                } else if (options[item].equals("From Files")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkGalleryPermission()) fileIntent();
                        else requestGalleryPermission();
                    } else fileIntent();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.create().show();
    }


    private boolean checkCameraPermission() {
        int result1 = ContextCompat.checkSelfPermission(RailingFilterActivity.this, CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(RailingFilterActivity.this, READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(RailingFilterActivity.this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(RailingFilterActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
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
                Uri photoURI = FileProvider.getUriForFile(RailingFilterActivity.this, "com.pixelmarketo.ularo.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, CAMERA_PIC_REQUEST);
            }
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FROM_GALLERY);
    }

    private void fileIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 100);
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
            // Glide.with(this).load(Camera_bitmap).into(proffileImage);
            imgpath.setText("" + Camera_bitmap);
            try {
                file = Util.getCompressed(this, Camera_bitmap);
                Camera_bitmap = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == SELECT_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            Uri galleryURI = data.getData();
            //  Glide.with(this).load(galleryURI).into(proffileImage);
            Camera_bitmap = getRealPathFromURIPath(galleryURI, RailingFilterActivity.this);
            imgpath.setText("" + Camera_bitmap);
            try {
                file = Util.getCompressed_Gellery(this, Camera_bitmap, galleryURI);
                Camera_bitmap = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK && null != data) {
            Uri uri = data.getData();

            Camera_bitmap = getFilePathFromURI(RailingFilterActivity.this, uri);
            imgpath.setText("" + Camera_bitmap);
            try {
                file = Util.getCompressed_Gellery(this, Camera_bitmap, uri);
                Camera_bitmap = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ErrorMessage.E("Camera_bitmap>>>" + Camera_bitmap);
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

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

}
