package com.pixelmarketo.ularo.activities;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialAutoCompleteTextView;
import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.Example;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.UserAccount;
import com.pixelmarketo.ularo.utility.Util;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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

public class AdvertisementActivity extends BaseActivity implements PaymentResultListener {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.imgpath)
    TextView imgpath;
    @BindView(R.id.etstate)
    MaterialAutoCompleteTextView etstate;
    @BindView(R.id.etdistrict)
    MaterialAutoCompleteTextView etdistrict;
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    @BindView(R.id.btnsend)
    Button btnsend;
    public static final int PERMISSION_REQUEST_CODE = 1111;
    private static final int REQUEST = 1337;
    public static int SELECT_FROM_GALLERY = 2;
    public static int CAMERA_PIC_REQUEST = 0;
    String id = "";
    @BindView(R.id.etname)
    TextInputEditText etname;
    @BindView(R.id.etmobile)
    TextInputEditText etmobile;
    @BindView(R.id.btnyes)
    RadioButton btnyes;
    @BindView(R.id.btnno)
    RadioButton btnno;
    private String Camera_bitmap = "";
    String package_name = "", payment,package_amount;
    String State = "{  \n" + "   \"states\":[  \n" + "      {  \n" + "         \"state\":\"Andhra Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Anantapur\",\n" + "            \"Chittoor\",\n" + "            \"East Godavari\",\n" + "            \"Guntur\",\n" + "            \"Krishna\",\n" + "            \"Kurnool\",\n" + "            \"Nellore\",\n" + "            \"Prakasam\",\n" + "            \"Srikakulam\",\n" + "            \"Visakhapatnam\",\n" + "            \"Vizianagaram\",\n" + "            \"West Godavari\",\n" + "            \"YSR Kadapa\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Arunachal Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Tawang\",\n" + "            \"West Kameng\",\n" + "            \"East Kameng\",\n" + "            \"Papum Pare\",\n" + "            \"Kurung Kumey\",\n" + "            \"Kra Daadi\",\n" + "            \"Lower Subansiri\",\n" + "            \"Upper Subansiri\",\n" + "            \"West Siang\",\n" + "            \"East Siang\",\n" + "            \"Siang\",\n" + "            \"Upper Siang\",\n" + "            \"Lower Siang\",\n" + "            \"Lower Dibang Valley\",\n" + "            \"Dibang Valley\",\n" + "            \"Anjaw\",\n" + "            \"Lohit\",\n" + "            \"Namsai\",\n" + "            \"Changlang\",\n" + "            \"Tirap\",\n" + "            \"Longding\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Assam\",\n" + "         \"districts\":[  \n" + "            \"Baksa\",\n" + "            \"Barpeta\",\n" + "            \"Biswanath\",\n" + "            \"Bongaigaon\",\n" + "            \"Cachar\",\n" + "            \"Charaideo\",\n" + "            \"Chirang\",\n" + "            \"Darrang\",\n" + "            \"Dhemaji\",\n" + "            \"Dhubri\",\n" + "            \"Dibrugarh\",\n" + "            \"Goalpara\",\n" + "            \"Golaghat\",\n" + "            \"Hailakandi\",\n" + "            \"Hojai\",\n" + "            \"Jorhat\",\n" + "            \"Kamrup Metropolitan\",\n" + "            \"Kamrup\",\n" + "            \"Karbi Anglong\",\n" + "            \"Karimganj\",\n" + "            \"Kokrajhar\",\n" + "            \"Lakhimpur\",\n" + "            \"Majuli\",\n" + "            \"Morigaon\",\n" + "            \"Nagaon\",\n" + "            \"Nalbari\",\n" + "            \"Dima Hasao\",\n" + "            \"Sivasagar\",\n" + "            \"Sonitpur\",\n" + "            \"South Salmara-Mankachar\",\n" + "            \"Tinsukia\",\n" + "            \"Udalguri\",\n" + "            \"West Karbi Anglong\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Bihar\",\n" + "         \"districts\":[  \n" + "            \"Araria\",\n" + "            \"Arwal\",\n" + "            \"Aurangabad\",\n" + "            \"Banka\",\n" + "            \"Begusarai\",\n" + "            \"Bhagalpur\",\n" + "            \"Bhojpur\",\n" + "            \"Buxar\",\n" + "            \"Darbhanga\",\n" + "            \"East Champaran (Motihari)\",\n" + "            \"Gaya\",\n" + "            \"Gopalganj\",\n" + "            \"Jamui\",\n" + "            \"Jehanabad\",\n" + "            \"Kaimur (Bhabua)\",\n" + "            \"Katihar\",\n" + "            \"Khagaria\",\n" + "            \"Kishanganj\",\n" + "            \"Lakhisarai\",\n" + "            \"Madhepura\",\n" + "            \"Madhubani\",\n" + "            \"Munger (Monghyr)\",\n" + "            \"Muzaffarpur\",\n" + "            \"Nalanda\",\n" + "            \"Nawada\",\n" + "            \"Patna\",\n" + "            \"Purnia (Purnea)\",\n" + "            \"Rohtas\",\n" + "            \"Saharsa\",\n" + "            \"Samastipur\",\n" + "            \"Saran\",\n" + "            \"Sheikhpura\",\n" + "            \"Sheohar\",\n" + "            \"Sitamarhi\",\n" + "            \"Siwan\",\n" + "            \"Supaul\",\n" + "            \"Vaishali\",\n" + "            \"West Champaran\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Chandigarh (UT)\",\n" + "         \"districts\":[  \n" + "            \"Chandigarh\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Chhattisgarh\",\n" + "         \"districts\":[  \n" + "            \"Balod\",\n" + "            \"Baloda Bazar\",\n" + "            \"Balrampur\",\n" + "            \"Bastar\",\n" + "            \"Bemetara\",\n" + "            \"Bijapur\",\n" + "            \"Bilaspur\",\n" + "            \"Dantewada (South Bastar)\",\n" + "            \"Dhamtari\",\n" + "            \"Durg\",\n" + "            \"Gariyaband\",\n" + "            \"Janjgir-Champa\",\n" + "            \"Jashpur\",\n" + "            \"Kabirdham (Kawardha)\",\n" + "            \"Kanker (North Bastar)\",\n" + "            \"Kondagaon\",\n" + "            \"Korba\",\n" + "            \"Korea (Koriya)\",\n" + "            \"Mahasamund\",\n" + "            \"Mungeli\",\n" + "            \"Narayanpur\",\n" + "            \"Raigarh\",\n" + "            \"Raipur\",\n" + "            \"Rajnandgaon\",\n" + "            \"Sukma\",\n" + "            \"Surajpur  \",\n" + "            \"Surguja\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Dadra and Nagar Haveli (UT)\",\n" + "         \"districts\":[  \n" + "            \"Dadra & Nagar Haveli\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Daman and Diu (UT)\",\n" + "         \"districts\":[  \n" + "            \"Daman\",\n" + "            \"Diu\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Delhi (NCT)\",\n" + "         \"districts\":[  \n" + "            \"Central Delhi\",\n" + "            \"East Delhi\",\n" + "            \"New Delhi\",\n" + "            \"North Delhi\",\n" + "            \"North East  Delhi\",\n" + "            \"North West  Delhi\",\n" + "            \"Shahdara\",\n" + "            \"South Delhi\",\n" + "            \"South East Delhi\",\n" + "            \"South West  Delhi\",\n" + "            \"West Delhi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Goa\",\n" + "         \"districts\":[  \n" + "            \"North Goa\",\n" + "            \"South Goa\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Gujarat\",\n" + "         \"districts\":[  \n" + "            \"Ahmedabad\",\n" + "            \"Amreli\",\n" + "            \"Anand\",\n" + "            \"Aravalli\",\n" + "            \"Banaskantha (Palanpur)\",\n" + "            \"Bharuch\",\n" + "            \"Bhavnagar\",\n" + "            \"Botad\",\n" + "            \"Chhota Udepur\",\n" + "            \"Dahod\",\n" + "            \"Dangs (Ahwa)\",\n" + "            \"Devbhoomi Dwarka\",\n" + "            \"Gandhinagar\",\n" + "            \"Gir Somnath\",\n" + "            \"Jamnagar\",\n" + "            \"Junagadh\",\n" + "            \"Kachchh\",\n" + "            \"Kheda (Nadiad)\",\n" + "            \"Mahisagar\",\n" + "            \"Mehsana\",\n" + "            \"Morbi\",\n" + "            \"Narmada (Rajpipla)\",\n" + "            \"Navsari\",\n" + "            \"Panchmahal (Godhra)\",\n" + "            \"Patan\",\n" + "            \"Porbandar\",\n" + "            \"Rajkot\",\n" + "            \"Sabarkantha (Himmatnagar)\",\n" + "            \"Surat\",\n" + "            \"Surendranagar\",\n" + "            \"Tapi (Vyara)\",\n" + "            \"Vadodara\",\n" + "            \"Valsad\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Haryana\",\n" + "         \"districts\":[  \n" + "            \"Ambala\",\n" + "            \"Bhiwani\",\n" + "            \"Charkhi Dadri\",\n" + "            \"Faridabad\",\n" + "            \"Fatehabad\",\n" + "            \"Gurgaon\",\n" + "            \"Hisar\",\n" + "            \"Jhajjar\",\n" + "            \"Jind\",\n" + "            \"Kaithal\",\n" + "            \"Karnal\",\n" + "            \"Kurukshetra\",\n" + "            \"Mahendragarh\",\n" + "            \"Mewat\",\n" + "            \"Palwal\",\n" + "            \"Panchkula\",\n" + "            \"Panipat\",\n" + "            \"Rewari\",\n" + "            \"Rohtak\",\n" + "            \"Sirsa\",\n" + "            \"Sonipat\",\n" + "            \"Yamunanagar\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Himachal Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Bilaspur\",\n" + "            \"Chamba\",\n" + "            \"Hamirpur\",\n" + "            \"Kangra\",\n" + "            \"Kinnaur\",\n" + "            \"Kullu\",\n" + "            \"Lahaul &amp; Spiti\",\n" + "            \"Mandi\",\n" + "            \"Shimla\",\n" + "            \"Sirmaur (Sirmour)\",\n" + "            \"Solan\",\n" + "            \"Una\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Jammu and Kashmir\",\n" + "         \"districts\":[  \n" + "            \"Anantnag\",\n" + "            \"Bandipore\",\n" + "            \"Baramulla\",\n" + "            \"Budgam\",\n" + "            \"Doda\",\n" + "            \"Ganderbal\",\n" + "            \"Jammu\",\n" + "            \"Kargil\",\n" + "            \"Kathua\",\n" + "            \"Kishtwar\",\n" + "            \"Kulgam\",\n" + "            \"Kupwara\",\n" + "            \"Leh\",\n" + "            \"Poonch\",\n" + "            \"Pulwama\",\n" + "            \"Rajouri\",\n" + "            \"Ramban\",\n" + "            \"Reasi\",\n" + "            \"Samba\",\n" + "            \"Shopian\",\n" + "            \"Srinagar\",\n" + "            \"Udhampur\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Jharkhand\",\n" + "         \"districts\":[  \n" + "            \"Bokaro\",\n" + "            \"Chatra\",\n" + "            \"Deoghar\",\n" + "            \"Dhanbad\",\n" + "            \"Dumka\",\n" + "            \"East Singhbhum\",\n" + "            \"Garhwa\",\n" + "            \"Giridih\",\n" + "            \"Godda\",\n" + "            \"Gumla\",\n" + "            \"Hazaribag\",\n" + "            \"Jamtara\",\n" + "            \"Khunti\",\n" + "            \"Koderma\",\n" + "            \"Latehar\",\n" + "            \"Lohardaga\",\n" + "            \"Pakur\",\n" + "            \"Palamu\",\n" + "            \"Ramgarh\",\n" + "            \"Ranchi\",\n" + "            \"Sahibganj\",\n" + "            \"Seraikela-Kharsawan\",\n" + "            \"Simdega\",\n" + "            \"West Singhbhum\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Karnataka\",\n" + "         \"districts\":[  \n" + "            \"Bagalkot\",\n" + "            \"Ballari (Bellary)\",\n" + "            \"Belagavi (Belgaum)\",\n" + "            \"Bengaluru (Bangalore) Rural\",\n" + "            \"Bengaluru (Bangalore) Urban\",\n" + "            \"Bidar\",\n" + "            \"Chamarajanagar\",\n" + "            \"Chikballapur\",\n" + "            \"Chikkamagaluru (Chikmagalur)\",\n" + "            \"Chitradurga\",\n" + "            \"Dakshina Kannada\",\n" + "            \"Davangere\",\n" + "            \"Dharwad\",\n" + "            \"Gadag\",\n" + "            \"Hassan\",\n" + "            \"Haveri\",\n" + "            \"Kalaburagi (Gulbarga)\",\n" + "            \"Kodagu\",\n" + "            \"Kolar\",\n" + "            \"Koppal\",\n" + "            \"Mandya\",\n" + "            \"Mysuru (Mysore)\",\n" + "            \"Raichur\",\n" + "            \"Ramanagara\",\n" + "            \"Shivamogga (Shimoga)\",\n" + "            \"Tumakuru (Tumkur)\",\n" + "            \"Udupi\",\n" + "            \"Uttara Kannada (Karwar)\",\n" + "            \"Vijayapura (Bijapur)\",\n" + "            \"Yadgir\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Kerala\",\n" + "         \"districts\":[  \n" + "            \"Alappuzha\",\n" + "            \"Ernakulam\",\n" + "            \"Idukki\",\n" + "            \"Kannur\",\n" + "            \"Kasaragod\",\n" + "            \"Kollam\",\n" + "            \"Kottayam\",\n" + "            \"Kozhikode\",\n" + "            \"Malappuram\",\n" + "            \"Palakkad\",\n" + "            \"Pathanamthitta\",\n" + "            \"Thiruvananthapuram\",\n" + "            \"Thrissur\",\n" + "            \"Wayanad\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Lakshadweep (UT)\",\n" + "         \"districts\":[  \n" + "            \"Agatti\",\n" + "            \"Amini\",\n" + "            \"Androth\",\n" + "            \"Bithra\",\n" + "            \"Chethlath\",\n" + "            \"Kavaratti\",\n" + "            \"Kadmath\",\n" + "            \"Kalpeni\",\n" + "            \"Kilthan\",\n" + "            \"Minicoy\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Madhya Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Agar Malwa\",\n" + "            \"Alirajpur\",\n" + "            \"Anuppur\",\n" + "            \"Ashoknagar\",\n" + "            \"Balaghat\",\n" + "            \"Barwani\",\n" + "            \"Betul\",\n" + "            \"Bhind\",\n" + "            \"Bhopal\",\n" + "            \"Burhanpur\",\n" + "            \"Chhatarpur\",\n" + "            \"Chhindwara\",\n" + "            \"Damoh\",\n" + "            \"Datia\",\n" + "            \"Dewas\",\n" + "            \"Dhar\",\n" + "            \"Dindori\",\n" + "            \"Guna\",\n" + "            \"Gwalior\",\n" + "            \"Harda\",\n" + "            \"Hoshangabad\",\n" + "            \"Indore\",\n" + "            \"Jabalpur\",\n" + "            \"Jhabua\",\n" + "            \"Katni\",\n" + "            \"Khandwa\",\n" + "            \"Khargone\",\n" + "            \"Mandla\",\n" + "            \"Mandsaur\",\n" + "            \"Morena\",\n" + "            \"Narsinghpur\",\n" + "            \"Neemuch\",\n" + "            \"Panna\",\n" + "            \"Raisen\",\n" + "            \"Rajgarh\",\n" + "            \"Ratlam\",\n" + "            \"Rewa\",\n" + "            \"Sagar\",\n" + "            \"Satna\",\n" + "            \"Sehore\",\n" + "            \"Seoni\",\n" + "            \"Shahdol\",\n" + "            \"Shajapur\",\n" + "            \"Sheopur\",\n" + "            \"Shivpuri\",\n" + "            \"Sidhi\",\n" + "            \"Singrauli\",\n" + "            \"Tikamgarh\",\n" + "            \"Ujjain\",\n" + "            \"Umaria\",\n" + "            \"Vidisha\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Maharashtra\",\n" + "         \"districts\":[  \n" + "            \"Ahmednagar\",\n" + "            \"Akola\",\n" + "            \"Amravati\",\n" + "            \"Aurangabad\",\n" + "            \"Beed\",\n" + "            \"Bhandara\",\n" + "            \"Buldhana\",\n" + "            \"Chandrapur\",\n" + "            \"Dhule\",\n" + "            \"Gadchiroli\",\n" + "            \"Gondia\",\n" + "            \"Hingoli\",\n" + "            \"Jalgaon\",\n" + "            \"Jalna\",\n" + "            \"Kolhapur\",\n" + "            \"Latur\",\n" + "            \"Mumbai City\",\n" + "            \"Mumbai Suburban\",\n" + "            \"Nagpur\",\n" + "            \"Nanded\",\n" + "            \"Nandurbar\",\n" + "            \"Nashik\",\n" + "            \"Osmanabad\",\n" + "            \"Palghar\",\n" + "            \"Parbhani\",\n" + "            \"Pune\",\n" + "            \"Raigad\",\n" + "            \"Ratnagiri\",\n" + "            \"Sangli\",\n" + "            \"Satara\",\n" + "            \"Sindhudurg\",\n" + "            \"Solapur\",\n" + "            \"Thane\",\n" + "            \"Wardha\",\n" + "            \"Washim\",\n" + "            \"Yavatmal\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Manipur\",\n" + "         \"districts\":[  \n" + "            \"Bishnupur\",\n" + "            \"Chandel\",\n" + "            \"Churachandpur\",\n" + "            \"Imphal East\",\n" + "            \"Imphal West\",\n" + "            \"Jiribam\",\n" + "            \"Kakching\",\n" + "            \"Kamjong\",\n" + "            \"Kangpokpi\",\n" + "            \"Noney\",\n" + "            \"Pherzawl\",\n" + "            \"Senapati\",\n" + "            \"Tamenglong\",\n" + "            \"Tengnoupal\",\n" + "            \"Thoubal\",\n" + "            \"Ukhrul\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Meghalaya\",\n" + "         \"districts\":[  \n" + "            \"East Garo Hills\",\n" + "            \"East Jaintia Hills\",\n" + "            \"East Khasi Hills\",\n" + "            \"North Garo Hills\",\n" + "            \"Ri Bhoi\",\n" + "            \"South Garo Hills\",\n" + "            \"South West Garo Hills \",\n" + "            \"South West Khasi Hills\",\n" + "            \"West Garo Hills\",\n" + "            \"West Jaintia Hills\",\n" + "            \"West Khasi Hills\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Mizoram\",\n" + "         \"districts\":[  \n" + "            \"Aizawl\",\n" + "            \"Champhai\",\n" + "            \"Kolasib\",\n" + "            \"Lawngtlai\",\n" + "            \"Lunglei\",\n" + "            \"Mamit\",\n" + "            \"Saiha\",\n" + "            \"Serchhip\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Nagaland\",\n" + "         \"districts\":[  \n" + "            \"Dimapur\",\n" + "            \"Kiphire\",\n" + "            \"Kohima\",\n" + "            \"Longleng\",\n" + "            \"Mokokchung\",\n" + "            \"Mon\",\n" + "            \"Peren\",\n" + "            \"Phek\",\n" + "            \"Tuensang\",\n" + "            \"Wokha\",\n" + "            \"Zunheboto\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Odisha\",\n" + "         \"districts\":[  \n" + "            \"Angul\",\n" + "            \"Balangir\",\n" + "            \"Balasore\",\n" + "            \"Bargarh\",\n" + "            \"Bhadrak\",\n" + "            \"Boudh\",\n" + "            \"Cuttack\",\n" + "            \"Deogarh\",\n" + "            \"Dhenkanal\",\n" + "            \"Gajapati\",\n" + "            \"Ganjam\",\n" + "            \"Jagatsinghapur\",\n" + "            \"Jajpur\",\n" + "            \"Jharsuguda\",\n" + "            \"Kalahandi\",\n" + "            \"Kandhamal\",\n" + "            \"Kendrapara\",\n" + "            \"Kendujhar (Keonjhar)\",\n" + "            \"Khordha\",\n" + "            \"Koraput\",\n" + "            \"Malkangiri\",\n" + "            \"Mayurbhanj\",\n" + "            \"Nabarangpur\",\n" + "            \"Nayagarh\",\n" + "            \"Nuapada\",\n" + "            \"Puri\",\n" + "            \"Rayagada\",\n" + "            \"Sambalpur\",\n" + "            \"Sonepur\",\n" + "            \"Sundargarh\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Puducherry (UT)\",\n" + "         \"districts\":[  \n" + "            \"Karaikal\",\n" + "            \"Mahe\",\n" + "            \"Pondicherry\",\n" + "            \"Yanam\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Punjab\",\n" + "         \"districts\":[  \n" + "            \"Amritsar\",\n" + "            \"Barnala\",\n" + "            \"Bathinda\",\n" + "            \"Faridkot\",\n" + "            \"Fatehgarh Sahib\",\n" + "            \"Fazilka\",\n" + "            \"Ferozepur\",\n" + "            \"Gurdaspur\",\n" + "            \"Hoshiarpur\",\n" + "            \"Jalandhar\",\n" + "            \"Kapurthala\",\n" + "            \"Ludhiana\",\n" + "            \"Mansa\",\n" + "            \"Moga\",\n" + "            \"Muktsar\",\n" + "            \"Nawanshahr (Shahid Bhagat Singh Nagar)\",\n" + "            \"Pathankot\",\n" + "            \"Patiala\",\n" + "            \"Rupnagar\",\n" + "            \"Sahibzada Ajit Singh Nagar (Mohali)\",\n" + "            \"Sangrur\",\n" + "            \"Tarn Taran\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Rajasthan\",\n" + "         \"districts\":[  \n" + "            \"Ajmer\",\n" + "            \"Alwar\",\n" + "            \"Banswara\",\n" + "            \"Baran\",\n" + "            \"Barmer\",\n" + "            \"Bharatpur\",\n" + "            \"Bhilwara\",\n" + "            \"Bikaner\",\n" + "            \"Bundi\",\n" + "            \"Chittorgarh\",\n" + "            \"Churu\",\n" + "            \"Dausa\",\n" + "            \"Dholpur\",\n" + "            \"Dungarpur\",\n" + "            \"Hanumangarh\",\n" + "            \"Jaipur\",\n" + "            \"Jaisalmer\",\n" + "            \"Jalore\",\n" + "            \"Jhalawar\",\n" + "            \"Jhunjhunu\",\n" + "            \"Jodhpur\",\n" + "            \"Karauli\",\n" + "            \"Kota\",\n" + "            \"Nagaur\",\n" + "            \"Pali\",\n" + "            \"Pratapgarh\",\n" + "            \"Rajsamand\",\n" + "            \"Sawai Madhopur\",\n" + "            \"Sikar\",\n" + "            \"Sirohi\",\n" + "            \"Sri Ganganagar\",\n" + "            \"Tonk\",\n" + "            \"Udaipur\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Sikkim\",\n" + "         \"districts\":[  \n" + "            \"East Sikkim\",\n" + "            \"North Sikkim\",\n" + "            \"South Sikkim\",\n" + "            \"West Sikkim\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Tamil Nadu\",\n" + "         \"districts\":[  \n" + "            \"Ariyalur\",\n" + "            \"Chennai\",\n" + "            \"Coimbatore\",\n" + "            \"Cuddalore\",\n" + "            \"Dharmapuri\",\n" + "            \"Dindigul\",\n" + "            \"Erode\",\n" + "            \"Kanchipuram\",\n" + "            \"Kanyakumari\",\n" + "            \"Karur\",\n" + "            \"Krishnagiri\",\n" + "            \"Madurai\",\n" + "            \"Nagapattinam\",\n" + "            \"Namakkal\",\n" + "            \"Nilgiris\",\n" + "            \"Perambalur\",\n" + "            \"Pudukkottai\",\n" + "            \"Ramanathapuram\",\n" + "            \"Salem\",\n" + "            \"Sivaganga\",\n" + "            \"Thanjavur\",\n" + "            \"Theni\",\n" + "            \"Thoothukudi (Tuticorin)\",\n" + "            \"Tiruchirappalli\",\n" + "            \"Tirunelveli\",\n" + "            \"Tiruppur\",\n" + "            \"Tiruvallur\",\n" + "            \"Tiruvannamalai\",\n" + "            \"Tiruvarur\",\n" + "            \"Vellore\",\n" + "            \"Viluppuram\",\n" + "            \"Virudhunagar\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Telangana\",\n" + "         \"districts\":[  \n" + "            \"Adilabad\",\n" + "            \"Bhadradri Kothagudem\",\n" + "            \"Hyderabad\",\n" + "            \"Jagtial\",\n" + "            \"Jangaon\",\n" + "            \"Jayashankar Bhoopalpally\",\n" + "            \"Jogulamba Gadwal\",\n" + "            \"Kamareddy\",\n" + "            \"Karimnagar\",\n" + "            \"Khammam\",\n" + "            \"Komaram Bheem Asifabad\",\n" + "            \"Mahabubabad\",\n" + "            \"Mahabubnagar\",\n" + "            \"Mancherial\",\n" + "            \"Medak\",\n" + "            \"Medchal\",\n" + "            \"Nagarkurnool\",\n" + "            \"Nalgonda\",\n" + "            \"Nirmal\",\n" + "            \"Nizamabad\",\n" + "            \"Peddapalli\",\n" + "            \"Rajanna Sircilla\",\n" + "            \"Rangareddy\",\n" + "            \"Sangareddy\",\n" + "            \"Siddipet\",\n" + "            \"Suryapet\",\n" + "            \"Vikarabad\",\n" + "            \"Wanaparthy\",\n" + "            \"Warangal (Rural)\",\n" + "            \"Warangal (Urban)\",\n" + "            \"Yadadri Bhuvanagiri\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Tripura\",\n" + "         \"districts\":[  \n" + "            \"Dhalai\",\n" + "            \"Gomati\",\n" + "            \"Khowai\",\n" + "            \"North Tripura\",\n" + "            \"Sepahijala\",\n" + "            \"South Tripura\",\n" + "            \"Unakoti\",\n" + "            \"West Tripura\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Uttarakhand\",\n" + "         \"districts\":[  \n" + "            \"Almora\",\n" + "            \"Bageshwar\",\n" + "            \"Chamoli\",\n" + "            \"Champawat\",\n" + "            \"Dehradun\",\n" + "            \"Haridwar\",\n" + "            \"Nainital\",\n" + "            \"Pauri Garhwal\",\n" + "            \"Pithoragarh\",\n" + "            \"Rudraprayag\",\n" + "            \"Tehri Garhwal\",\n" + "            \"Udham Singh Nagar\",\n" + "            \"Uttarkashi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Uttar Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Agra\",\n" + "            \"Aligarh\",\n" + "            \"Allahabad\",\n" + "            \"Ambedkar Nagar\",\n" + "            \"Amethi (Chatrapati Sahuji Mahraj Nagar)\",\n" + "            \"Amroha (J.P. Nagar)\",\n" + "            \"Auraiya\",\n" + "            \"Azamgarh\",\n" + "            \"Baghpat\",\n" + "            \"Bahraich\",\n" + "            \"Ballia\",\n" + "            \"Balrampur\",\n" + "            \"Banda\",\n" + "            \"Barabanki\",\n" + "            \"Bareilly\",\n" + "            \"Basti\",\n" + "            \"Bhadohi\",\n" + "            \"Bijnor\",\n" + "            \"Budaun\",\n" + "            \"Bulandshahr\",\n" + "            \"Chandauli\",\n" + "            \"Chitrakoot\",\n" + "            \"Deoria\",\n" + "            \"Etah\",\n" + "            \"Etawah\",\n" + "            \"Faizabad\",\n" + "            \"Farrukhabad\",\n" + "            \"Fatehpur\",\n" + "            \"Firozabad\",\n" + "            \"Gautam Buddha Nagar\",\n" + "            \"Ghaziabad\",\n" + "            \"Ghazipur\",\n" + "            \"Gonda\",\n" + "            \"Gorakhpur\",\n" + "            \"Hamirpur\",\n" + "            \"Hapur (Panchsheel Nagar)\",\n" + "            \"Hardoi\",\n" + "            \"Hathras\",\n" + "            \"Jalaun\",\n" + "            \"Jaunpur\",\n" + "            \"Jhansi\",\n" + "            \"Kannauj\",\n" + "            \"Kanpur Dehat\",\n" + "            \"Kanpur Nagar\",\n" + "            \"Kanshiram Nagar (Kasganj)\",\n" + "            \"Kaushambi\",\n" + "            \"Kushinagar (Padrauna)\",\n" + "            \"Lakhimpur - Kheri\",\n" + "            \"Lalitpur\",\n" + "            \"Lucknow\",\n" + "            \"Maharajganj\",\n" + "            \"Mahoba\",\n" + "            \"Mainpuri\",\n" + "            \"Mathura\",\n" + "            \"Mau\",\n" + "            \"Meerut\",\n" + "            \"Mirzapur\",\n" + "            \"Moradabad\",\n" + "            \"Muzaffarnagar\",\n" + "            \"Pilibhit\",\n" + "            \"Pratapgarh\",\n" + "            \"RaeBareli\",\n" + "            \"Rampur\",\n" + "            \"Saharanpur\",\n" + "            \"Sambhal (Bhim Nagar)\",\n" + "            \"Sant Kabir Nagar\",\n" + "            \"Shahjahanpur\",\n" + "            \"Shamali (Prabuddh Nagar)\",\n" + "            \"Shravasti\",\n" + "            \"Siddharth Nagar\",\n" + "            \"Sitapur\",\n" + "            \"Sonbhadra\",\n" + "            \"Sultanpur\",\n" + "            \"Unnao\",\n" + "            \"Varanasi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"West Bengal\",\n" + "         \"districts\":[  \n" + "            \"Alipurduar\",\n" + "            \"Bankura\",\n" + "            \"Birbhum\",\n" + "            \"Burdwan (Bardhaman)\",\n" + "            \"Cooch Behar\",\n" + "            \"Dakshin Dinajpur (South Dinajpur)\",\n" + "            \"Darjeeling\",\n" + "            \"Hooghly\",\n" + "            \"Howrah\",\n" + "            \"Jalpaiguri\",\n" + "            \"Kalimpong\",\n" + "            \"Kolkata\",\n" + "            \"Malda\",\n" + "            \"Murshidabad\",\n" + "            \"Nadia\",\n" + "            \"North 24 Parganas\",\n" + "            \"Paschim Medinipur (West Medinipur)\",\n" + "            \"Purba Medinipur (East Medinipur)\",\n" + "            \"Purulia\",\n" + "            \"South 24 Parganas\",\n" + "            \"Uttar Dinajpur (North Dinajpur)\"\n" + "         ]\n" + "      }\n" + "   ]\n" + "}";
    ArrayList<String> stateArrayList = new ArrayList<>();
    ArrayList<String> districtArrayList = new ArrayList<>();
    private String Selected_State = "", Select_City = "";
    @Override
    protected int getContentResId() {
        return R.layout.activity_advertisement;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        titleTxt.setText("Advertisement");
        getAll_State();
        etstate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Selected_State = adapterView.getItemAtPosition(i).toString();
                getAll_District(adapterView.getItemAtPosition(i).toString());

            }
        });
        etdistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Select_City = adapterView.getItemAtPosition(i).toString();

            }
        });
    }


    @OnClick({R.id.imgpath, R.id.btnsend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgpath:
                selectImage();
                break;
            case R.id.btnsend:
                if (UserAccount.isEmpty(etname,etmobile,etstate,etdistrict)) {
                    if (UserAccount.isPhoneNumberLength(etmobile)) {
                        if (!Camera_bitmap.equals("")) {
                            int q1 = radiogroup1.getCheckedRadioButtonId();
                            RadioButton radioButton1 = (RadioButton) findViewById(q1);
                            ErrorMessage.E("1===" + radioButton1.getText());
                            if (radioButton1.getText().equals("half yearly")) {
                                package_name = "6 month";
                                package_amount = "600";
                                ErrorMessage.E("quick_mode===" + package_name);
                                startPayment(("" + (new DecimalFormat("#").format(600)) + "00"));
                                //confirm_payment("100");
                            } else {
                                package_name = "12 month";
                                package_amount = "1000";
                                startPayment(("" + (new DecimalFormat("#").format(1000)) + "00"));
                                // confirm_payment("1000");
                            }


                        } else {
                            ErrorMessage.T(this, "Please select advertisement image");
                        }
                    }else {
                        UserAccount.EditTextPointer.setError("Please enter valid mobile number !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
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
        int result1 = ContextCompat.checkSelfPermission(AdvertisementActivity.this, CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(AdvertisementActivity.this, READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(AdvertisementActivity.this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(AdvertisementActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
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
                Uri photoURI = FileProvider.getUriForFile(AdvertisementActivity.this, "com.pixelmarketo.ularo.provider", photoFile);
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
            // Glide.with(this).load(Camera_bitmap).into(proffileImage);
            imgpath.setText("" + Camera_bitmap);
            try {
                File file = Util.getCompressed(this, Camera_bitmap);
                Camera_bitmap = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == SELECT_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            Uri galleryURI = data.getData();
            //  Glide.with(this).load(galleryURI).into(proffileImage);
            Camera_bitmap = getRealPathFromURIPath(galleryURI, AdvertisementActivity.this);
            imgpath.setText("" + Camera_bitmap);
            try {
                File file = Util.getCompressed_Gellery(this, Camera_bitmap, galleryURI);
                Camera_bitmap = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
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


    @Override
    public void onPaymentSuccess(String s) {
        ErrorMessage.E("payment" + s);
        confirm_payment(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        ErrorMessage.E("payment" + s);
    }


    public void startPayment(String Charge) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        ErrorMessage.E("Charge startPayment" + Charge);

        payment = Charge;
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Ullaro");
            options.put("description", "Advertisemenr");
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", Charge);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "squarefeethelp@gmail.com");
            preFill.put("contact", etmobile.getText().toString());

            options.put("prefill", preFill);


            co.open(activity, options);
        } catch (Exception e) {
            ErrorMessage.E("" + e.getMessage());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void confirm_payment(String s) {
        if (NetworkUtil.isNetworkAvailable(AdvertisementActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(AdvertisementActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);

            ErrorMessage.E("with image" + Camera_bitmap);
            ErrorMessage.E("data"+ Select_City+""+etname.getText().toString()+""+ etmobile.getText().toString()+""+package_amount+""+package_name+""+s);

            RequestBody distirct = RequestBody.create(MediaType.parse("text/plain"),Select_City);
            RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), etname.getText().toString());
            RequestBody contact = RequestBody.create(MediaType.parse("text/plain"), etmobile.getText().toString());
            RequestBody paymen = RequestBody.create(MediaType.parse("text/plain"), package_amount);
            RequestBody package_nam = RequestBody.create(MediaType.parse("text/plain"), package_name);
            RequestBody transaction_id = RequestBody.create(MediaType.parse("text/plain"), s);
            RequestBody mode = RequestBody.create(MediaType.parse("text/plain"), "online");


            File file = new File(Camera_bitmap);
            final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("adv_image", file.getName(), requestfile);//profile pic
            Call<ResponseBody> call = apiService.advertisement(user_name,contact, transaction_id, mode, paymen, distirct, package_nam,body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.toString());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.T(AdvertisementActivity.this, object.getString("message"));
                                /*finish();*/

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(AdvertisementActivity.this, object.getString("message"));
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
            ErrorMessage.T(AdvertisementActivity.this, "No Internet");
        }
    }
    public void getAll_State() {
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(State);
            Example example = gson.fromJson(jsonObject.toString(), Example.class);
            for (int i = 0; i < example.getStates().size(); i++) {
                stateArrayList.add(example.getStates().get(i).getState());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateArrayList);
            etstate.setThreshold(1);
            etstate.setAdapter(adapter);

        } catch (Exception e) {
        }
    }

    public void getAll_District(String City) {
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(State);
            Example example = gson.fromJson(jsonObject.toString(), Example.class);
            for (int i = 0; i < example.getStates().size(); i++) {
                if (example.getStates().get(i).getState().equals(City)) {
                    districtArrayList = (ArrayList<String>) example.getStates().get(i).getDistricts();
                }
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, districtArrayList);
            etdistrict.setAdapter(spinnerArrayAdapter);
            etdistrict.setThreshold(1);

        } catch (Exception e) {
        }
    }
}
