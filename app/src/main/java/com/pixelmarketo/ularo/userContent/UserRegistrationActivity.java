package com.pixelmarketo.ularo.userContent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialAutoCompleteTextView;
import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.activities.OtpActivity;
import com.pixelmarketo.ularo.bidderContent.TermsAndConditionActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
import com.pixelmarketo.ularo.model.Example;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.SavedData;
import com.pixelmarketo.ularo.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationActivity extends BaseActivity {

    @BindView(R.id.etname)
    TextInputEditText etname;
    @BindView(R.id.etmobile)
    TextInputEditText etmobile;
    @BindView(R.id.etaddress)
    TextInputEditText etaddress;
    @BindView(R.id.etpassword)
    TextInputEditText etpassword;
    @BindView(R.id.etconfirmpassword)
    TextInputEditText etconfirmpassword;
    @BindView(R.id.terms_condition_check_reg)
    CheckBox termsConditionCheckReg;
    @BindView(R.id.btnregister)
    Button btnregister;
    @BindView(R.id.etpincode)
    TextInputEditText etpincode;
    @BindView(R.id.etcity)
    TextInputEditText etcity;
    @BindView(R.id.etstate)
    MaterialAutoCompleteTextView etstate;
    @BindView(R.id.etdistrict)
    MaterialAutoCompleteTextView etdistrict;
    @BindView(R.id.tvterms)
    TextView tvterms;
    private String GOOGLE_API_KEY = "AIzaSyCH49-kUK-qkoMBiMqAvDio-Oumh7GGF4g";
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    String State = "{  \n" + "   \"states\":[  \n" + "      {  \n" + "         \"state\":\"Andhra Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Anantapur\",\n" + "            \"Chittoor\",\n" + "            \"East Godavari\",\n" + "            \"Guntur\",\n" + "            \"Krishna\",\n" + "            \"Kurnool\",\n" + "            \"Nellore\",\n" + "            \"Prakasam\",\n" + "            \"Srikakulam\",\n" + "            \"Visakhapatnam\",\n" + "            \"Vizianagaram\",\n" + "            \"West Godavari\",\n" + "            \"YSR Kadapa\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Arunachal Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Tawang\",\n" + "            \"West Kameng\",\n" + "            \"East Kameng\",\n" + "            \"Papum Pare\",\n" + "            \"Kurung Kumey\",\n" + "            \"Kra Daadi\",\n" + "            \"Lower Subansiri\",\n" + "            \"Upper Subansiri\",\n" + "            \"West Siang\",\n" + "            \"East Siang\",\n" + "            \"Siang\",\n" + "            \"Upper Siang\",\n" + "            \"Lower Siang\",\n" + "            \"Lower Dibang Valley\",\n" + "            \"Dibang Valley\",\n" + "            \"Anjaw\",\n" + "            \"Lohit\",\n" + "            \"Namsai\",\n" + "            \"Changlang\",\n" + "            \"Tirap\",\n" + "            \"Longding\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Assam\",\n" + "         \"districts\":[  \n" + "            \"Baksa\",\n" + "            \"Barpeta\",\n" + "            \"Biswanath\",\n" + "            \"Bongaigaon\",\n" + "            \"Cachar\",\n" + "            \"Charaideo\",\n" + "            \"Chirang\",\n" + "            \"Darrang\",\n" + "            \"Dhemaji\",\n" + "            \"Dhubri\",\n" + "            \"Dibrugarh\",\n" + "            \"Goalpara\",\n" + "            \"Golaghat\",\n" + "            \"Hailakandi\",\n" + "            \"Hojai\",\n" + "            \"Jorhat\",\n" + "            \"Kamrup Metropolitan\",\n" + "            \"Kamrup\",\n" + "            \"Karbi Anglong\",\n" + "            \"Karimganj\",\n" + "            \"Kokrajhar\",\n" + "            \"Lakhimpur\",\n" + "            \"Majuli\",\n" + "            \"Morigaon\",\n" + "            \"Nagaon\",\n" + "            \"Nalbari\",\n" + "            \"Dima Hasao\",\n" + "            \"Sivasagar\",\n" + "            \"Sonitpur\",\n" + "            \"South Salmara-Mankachar\",\n" + "            \"Tinsukia\",\n" + "            \"Udalguri\",\n" + "            \"West Karbi Anglong\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Bihar\",\n" + "         \"districts\":[  \n" + "            \"Araria\",\n" + "            \"Arwal\",\n" + "            \"Aurangabad\",\n" + "            \"Banka\",\n" + "            \"Begusarai\",\n" + "            \"Bhagalpur\",\n" + "            \"Bhojpur\",\n" + "            \"Buxar\",\n" + "            \"Darbhanga\",\n" + "            \"East Champaran (Motihari)\",\n" + "            \"Gaya\",\n" + "            \"Gopalganj\",\n" + "            \"Jamui\",\n" + "            \"Jehanabad\",\n" + "            \"Kaimur (Bhabua)\",\n" + "            \"Katihar\",\n" + "            \"Khagaria\",\n" + "            \"Kishanganj\",\n" + "            \"Lakhisarai\",\n" + "            \"Madhepura\",\n" + "            \"Madhubani\",\n" + "            \"Munger (Monghyr)\",\n" + "            \"Muzaffarpur\",\n" + "            \"Nalanda\",\n" + "            \"Nawada\",\n" + "            \"Patna\",\n" + "            \"Purnia (Purnea)\",\n" + "            \"Rohtas\",\n" + "            \"Saharsa\",\n" + "            \"Samastipur\",\n" + "            \"Saran\",\n" + "            \"Sheikhpura\",\n" + "            \"Sheohar\",\n" + "            \"Sitamarhi\",\n" + "            \"Siwan\",\n" + "            \"Supaul\",\n" + "            \"Vaishali\",\n" + "            \"West Champaran\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Chandigarh (UT)\",\n" + "         \"districts\":[  \n" + "            \"Chandigarh\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Chhattisgarh\",\n" + "         \"districts\":[  \n" + "            \"Balod\",\n" + "            \"Baloda Bazar\",\n" + "            \"Balrampur\",\n" + "            \"Bastar\",\n" + "            \"Bemetara\",\n" + "            \"Bijapur\",\n" + "            \"Bilaspur\",\n" + "            \"Dantewada (South Bastar)\",\n" + "            \"Dhamtari\",\n" + "            \"Durg\",\n" + "            \"Gariyaband\",\n" + "            \"Janjgir-Champa\",\n" + "            \"Jashpur\",\n" + "            \"Kabirdham (Kawardha)\",\n" + "            \"Kanker (North Bastar)\",\n" + "            \"Kondagaon\",\n" + "            \"Korba\",\n" + "            \"Korea (Koriya)\",\n" + "            \"Mahasamund\",\n" + "            \"Mungeli\",\n" + "            \"Narayanpur\",\n" + "            \"Raigarh\",\n" + "            \"Raipur\",\n" + "            \"Rajnandgaon\",\n" + "            \"Sukma\",\n" + "            \"Surajpur  \",\n" + "            \"Surguja\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Dadra and Nagar Haveli (UT)\",\n" + "         \"districts\":[  \n" + "            \"Dadra & Nagar Haveli\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Daman and Diu (UT)\",\n" + "         \"districts\":[  \n" + "            \"Daman\",\n" + "            \"Diu\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Delhi (NCT)\",\n" + "         \"districts\":[  \n" + "            \"Central Delhi\",\n" + "            \"East Delhi\",\n" + "            \"New Delhi\",\n" + "            \"North Delhi\",\n" + "            \"North East  Delhi\",\n" + "            \"North West  Delhi\",\n" + "            \"Shahdara\",\n" + "            \"South Delhi\",\n" + "            \"South East Delhi\",\n" + "            \"South West  Delhi\",\n" + "            \"West Delhi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Goa\",\n" + "         \"districts\":[  \n" + "            \"North Goa\",\n" + "            \"South Goa\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Gujarat\",\n" + "         \"districts\":[  \n" + "            \"Ahmedabad\",\n" + "            \"Amreli\",\n" + "            \"Anand\",\n" + "            \"Aravalli\",\n" + "            \"Banaskantha (Palanpur)\",\n" + "            \"Bharuch\",\n" + "            \"Bhavnagar\",\n" + "            \"Botad\",\n" + "            \"Chhota Udepur\",\n" + "            \"Dahod\",\n" + "            \"Dangs (Ahwa)\",\n" + "            \"Devbhoomi Dwarka\",\n" + "            \"Gandhinagar\",\n" + "            \"Gir Somnath\",\n" + "            \"Jamnagar\",\n" + "            \"Junagadh\",\n" + "            \"Kachchh\",\n" + "            \"Kheda (Nadiad)\",\n" + "            \"Mahisagar\",\n" + "            \"Mehsana\",\n" + "            \"Morbi\",\n" + "            \"Narmada (Rajpipla)\",\n" + "            \"Navsari\",\n" + "            \"Panchmahal (Godhra)\",\n" + "            \"Patan\",\n" + "            \"Porbandar\",\n" + "            \"Rajkot\",\n" + "            \"Sabarkantha (Himmatnagar)\",\n" + "            \"Surat\",\n" + "            \"Surendranagar\",\n" + "            \"Tapi (Vyara)\",\n" + "            \"Vadodara\",\n" + "            \"Valsad\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Haryana\",\n" + "         \"districts\":[  \n" + "            \"Ambala\",\n" + "            \"Bhiwani\",\n" + "            \"Charkhi Dadri\",\n" + "            \"Faridabad\",\n" + "            \"Fatehabad\",\n" + "            \"Gurgaon\",\n" + "            \"Hisar\",\n" + "            \"Jhajjar\",\n" + "            \"Jind\",\n" + "            \"Kaithal\",\n" + "            \"Karnal\",\n" + "            \"Kurukshetra\",\n" + "            \"Mahendragarh\",\n" + "            \"Mewat\",\n" + "            \"Palwal\",\n" + "            \"Panchkula\",\n" + "            \"Panipat\",\n" + "            \"Rewari\",\n" + "            \"Rohtak\",\n" + "            \"Sirsa\",\n" + "            \"Sonipat\",\n" + "            \"Yamunanagar\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Himachal Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Bilaspur\",\n" + "            \"Chamba\",\n" + "            \"Hamirpur\",\n" + "            \"Kangra\",\n" + "            \"Kinnaur\",\n" + "            \"Kullu\",\n" + "            \"Lahaul &amp; Spiti\",\n" + "            \"Mandi\",\n" + "            \"Shimla\",\n" + "            \"Sirmaur (Sirmour)\",\n" + "            \"Solan\",\n" + "            \"Una\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Jammu and Kashmir\",\n" + "         \"districts\":[  \n" + "            \"Anantnag\",\n" + "            \"Bandipore\",\n" + "            \"Baramulla\",\n" + "            \"Budgam\",\n" + "            \"Doda\",\n" + "            \"Ganderbal\",\n" + "            \"Jammu\",\n" + "            \"Kargil\",\n" + "            \"Kathua\",\n" + "            \"Kishtwar\",\n" + "            \"Kulgam\",\n" + "            \"Kupwara\",\n" + "            \"Leh\",\n" + "            \"Poonch\",\n" + "            \"Pulwama\",\n" + "            \"Rajouri\",\n" + "            \"Ramban\",\n" + "            \"Reasi\",\n" + "            \"Samba\",\n" + "            \"Shopian\",\n" + "            \"Srinagar\",\n" + "            \"Udhampur\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Jharkhand\",\n" + "         \"districts\":[  \n" + "            \"Bokaro\",\n" + "            \"Chatra\",\n" + "            \"Deoghar\",\n" + "            \"Dhanbad\",\n" + "            \"Dumka\",\n" + "            \"East Singhbhum\",\n" + "            \"Garhwa\",\n" + "            \"Giridih\",\n" + "            \"Godda\",\n" + "            \"Gumla\",\n" + "            \"Hazaribag\",\n" + "            \"Jamtara\",\n" + "            \"Khunti\",\n" + "            \"Koderma\",\n" + "            \"Latehar\",\n" + "            \"Lohardaga\",\n" + "            \"Pakur\",\n" + "            \"Palamu\",\n" + "            \"Ramgarh\",\n" + "            \"Ranchi\",\n" + "            \"Sahibganj\",\n" + "            \"Seraikela-Kharsawan\",\n" + "            \"Simdega\",\n" + "            \"West Singhbhum\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Karnataka\",\n" + "         \"districts\":[  \n" + "            \"Bagalkot\",\n" + "            \"Ballari (Bellary)\",\n" + "            \"Belagavi (Belgaum)\",\n" + "            \"Bengaluru (Bangalore) Rural\",\n" + "            \"Bengaluru (Bangalore) Urban\",\n" + "            \"Bidar\",\n" + "            \"Chamarajanagar\",\n" + "            \"Chikballapur\",\n" + "            \"Chikkamagaluru (Chikmagalur)\",\n" + "            \"Chitradurga\",\n" + "            \"Dakshina Kannada\",\n" + "            \"Davangere\",\n" + "            \"Dharwad\",\n" + "            \"Gadag\",\n" + "            \"Hassan\",\n" + "            \"Haveri\",\n" + "            \"Kalaburagi (Gulbarga)\",\n" + "            \"Kodagu\",\n" + "            \"Kolar\",\n" + "            \"Koppal\",\n" + "            \"Mandya\",\n" + "            \"Mysuru (Mysore)\",\n" + "            \"Raichur\",\n" + "            \"Ramanagara\",\n" + "            \"Shivamogga (Shimoga)\",\n" + "            \"Tumakuru (Tumkur)\",\n" + "            \"Udupi\",\n" + "            \"Uttara Kannada (Karwar)\",\n" + "            \"Vijayapura (Bijapur)\",\n" + "            \"Yadgir\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Kerala\",\n" + "         \"districts\":[  \n" + "            \"Alappuzha\",\n" + "            \"Ernakulam\",\n" + "            \"Idukki\",\n" + "            \"Kannur\",\n" + "            \"Kasaragod\",\n" + "            \"Kollam\",\n" + "            \"Kottayam\",\n" + "            \"Kozhikode\",\n" + "            \"Malappuram\",\n" + "            \"Palakkad\",\n" + "            \"Pathanamthitta\",\n" + "            \"Thiruvananthapuram\",\n" + "            \"Thrissur\",\n" + "            \"Wayanad\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Lakshadweep (UT)\",\n" + "         \"districts\":[  \n" + "            \"Agatti\",\n" + "            \"Amini\",\n" + "            \"Androth\",\n" + "            \"Bithra\",\n" + "            \"Chethlath\",\n" + "            \"Kavaratti\",\n" + "            \"Kadmath\",\n" + "            \"Kalpeni\",\n" + "            \"Kilthan\",\n" + "            \"Minicoy\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Madhya Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Agar Malwa\",\n" + "            \"Alirajpur\",\n" + "            \"Anuppur\",\n" + "            \"Ashoknagar\",\n" + "            \"Balaghat\",\n" + "            \"Barwani\",\n" + "            \"Betul\",\n" + "            \"Bhind\",\n" + "            \"Bhopal\",\n" + "            \"Burhanpur\",\n" + "            \"Chhatarpur\",\n" + "            \"Chhindwara\",\n" + "            \"Damoh\",\n" + "            \"Datia\",\n" + "            \"Dewas\",\n" + "            \"Dhar\",\n" + "            \"Dindori\",\n" + "            \"Guna\",\n" + "            \"Gwalior\",\n" + "            \"Harda\",\n" + "            \"Hoshangabad\",\n" + "            \"Indore\",\n" + "            \"Jabalpur\",\n" + "            \"Jhabua\",\n" + "            \"Katni\",\n" + "            \"Khandwa\",\n" + "            \"Khargone\",\n" + "            \"Mandla\",\n" + "            \"Mandsaur\",\n" + "            \"Morena\",\n" + "            \"Narsinghpur\",\n" + "            \"Neemuch\",\n" + "            \"Panna\",\n" + "            \"Raisen\",\n" + "            \"Rajgarh\",\n" + "            \"Ratlam\",\n" + "            \"Rewa\",\n" + "            \"Sagar\",\n" + "            \"Satna\",\n" + "            \"Sehore\",\n" + "            \"Seoni\",\n" + "            \"Shahdol\",\n" + "            \"Shajapur\",\n" + "            \"Sheopur\",\n" + "            \"Shivpuri\",\n" + "            \"Sidhi\",\n" + "            \"Singrauli\",\n" + "            \"Tikamgarh\",\n" + "            \"Ujjain\",\n" + "            \"Umaria\",\n" + "            \"Vidisha\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Maharashtra\",\n" + "         \"districts\":[  \n" + "            \"Ahmednagar\",\n" + "            \"Akola\",\n" + "            \"Amravati\",\n" + "            \"Aurangabad\",\n" + "            \"Beed\",\n" + "            \"Bhandara\",\n" + "            \"Buldhana\",\n" + "            \"Chandrapur\",\n" + "            \"Dhule\",\n" + "            \"Gadchiroli\",\n" + "            \"Gondia\",\n" + "            \"Hingoli\",\n" + "            \"Jalgaon\",\n" + "            \"Jalna\",\n" + "            \"Kolhapur\",\n" + "            \"Latur\",\n" + "            \"Mumbai City\",\n" + "            \"Mumbai Suburban\",\n" + "            \"Nagpur\",\n" + "            \"Nanded\",\n" + "            \"Nandurbar\",\n" + "            \"Nashik\",\n" + "            \"Osmanabad\",\n" + "            \"Palghar\",\n" + "            \"Parbhani\",\n" + "            \"Pune\",\n" + "            \"Raigad\",\n" + "            \"Ratnagiri\",\n" + "            \"Sangli\",\n" + "            \"Satara\",\n" + "            \"Sindhudurg\",\n" + "            \"Solapur\",\n" + "            \"Thane\",\n" + "            \"Wardha\",\n" + "            \"Washim\",\n" + "            \"Yavatmal\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Manipur\",\n" + "         \"districts\":[  \n" + "            \"Bishnupur\",\n" + "            \"Chandel\",\n" + "            \"Churachandpur\",\n" + "            \"Imphal East\",\n" + "            \"Imphal West\",\n" + "            \"Jiribam\",\n" + "            \"Kakching\",\n" + "            \"Kamjong\",\n" + "            \"Kangpokpi\",\n" + "            \"Noney\",\n" + "            \"Pherzawl\",\n" + "            \"Senapati\",\n" + "            \"Tamenglong\",\n" + "            \"Tengnoupal\",\n" + "            \"Thoubal\",\n" + "            \"Ukhrul\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Meghalaya\",\n" + "         \"districts\":[  \n" + "            \"East Garo Hills\",\n" + "            \"East Jaintia Hills\",\n" + "            \"East Khasi Hills\",\n" + "            \"North Garo Hills\",\n" + "            \"Ri Bhoi\",\n" + "            \"South Garo Hills\",\n" + "            \"South West Garo Hills \",\n" + "            \"South West Khasi Hills\",\n" + "            \"West Garo Hills\",\n" + "            \"West Jaintia Hills\",\n" + "            \"West Khasi Hills\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Mizoram\",\n" + "         \"districts\":[  \n" + "            \"Aizawl\",\n" + "            \"Champhai\",\n" + "            \"Kolasib\",\n" + "            \"Lawngtlai\",\n" + "            \"Lunglei\",\n" + "            \"Mamit\",\n" + "            \"Saiha\",\n" + "            \"Serchhip\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Nagaland\",\n" + "         \"districts\":[  \n" + "            \"Dimapur\",\n" + "            \"Kiphire\",\n" + "            \"Kohima\",\n" + "            \"Longleng\",\n" + "            \"Mokokchung\",\n" + "            \"Mon\",\n" + "            \"Peren\",\n" + "            \"Phek\",\n" + "            \"Tuensang\",\n" + "            \"Wokha\",\n" + "            \"Zunheboto\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Odisha\",\n" + "         \"districts\":[  \n" + "            \"Angul\",\n" + "            \"Balangir\",\n" + "            \"Balasore\",\n" + "            \"Bargarh\",\n" + "            \"Bhadrak\",\n" + "            \"Boudh\",\n" + "            \"Cuttack\",\n" + "            \"Deogarh\",\n" + "            \"Dhenkanal\",\n" + "            \"Gajapati\",\n" + "            \"Ganjam\",\n" + "            \"Jagatsinghapur\",\n" + "            \"Jajpur\",\n" + "            \"Jharsuguda\",\n" + "            \"Kalahandi\",\n" + "            \"Kandhamal\",\n" + "            \"Kendrapara\",\n" + "            \"Kendujhar (Keonjhar)\",\n" + "            \"Khordha\",\n" + "            \"Koraput\",\n" + "            \"Malkangiri\",\n" + "            \"Mayurbhanj\",\n" + "            \"Nabarangpur\",\n" + "            \"Nayagarh\",\n" + "            \"Nuapada\",\n" + "            \"Puri\",\n" + "            \"Rayagada\",\n" + "            \"Sambalpur\",\n" + "            \"Sonepur\",\n" + "            \"Sundargarh\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Puducherry (UT)\",\n" + "         \"districts\":[  \n" + "            \"Karaikal\",\n" + "            \"Mahe\",\n" + "            \"Pondicherry\",\n" + "            \"Yanam\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Punjab\",\n" + "         \"districts\":[  \n" + "            \"Amritsar\",\n" + "            \"Barnala\",\n" + "            \"Bathinda\",\n" + "            \"Faridkot\",\n" + "            \"Fatehgarh Sahib\",\n" + "            \"Fazilka\",\n" + "            \"Ferozepur\",\n" + "            \"Gurdaspur\",\n" + "            \"Hoshiarpur\",\n" + "            \"Jalandhar\",\n" + "            \"Kapurthala\",\n" + "            \"Ludhiana\",\n" + "            \"Mansa\",\n" + "            \"Moga\",\n" + "            \"Muktsar\",\n" + "            \"Nawanshahr (Shahid Bhagat Singh Nagar)\",\n" + "            \"Pathankot\",\n" + "            \"Patiala\",\n" + "            \"Rupnagar\",\n" + "            \"Sahibzada Ajit Singh Nagar (Mohali)\",\n" + "            \"Sangrur\",\n" + "            \"Tarn Taran\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Rajasthan\",\n" + "         \"districts\":[  \n" + "            \"Ajmer\",\n" + "            \"Alwar\",\n" + "            \"Banswara\",\n" + "            \"Baran\",\n" + "            \"Barmer\",\n" + "            \"Bharatpur\",\n" + "            \"Bhilwara\",\n" + "            \"Bikaner\",\n" + "            \"Bundi\",\n" + "            \"Chittorgarh\",\n" + "            \"Churu\",\n" + "            \"Dausa\",\n" + "            \"Dholpur\",\n" + "            \"Dungarpur\",\n" + "            \"Hanumangarh\",\n" + "            \"Jaipur\",\n" + "            \"Jaisalmer\",\n" + "            \"Jalore\",\n" + "            \"Jhalawar\",\n" + "            \"Jhunjhunu\",\n" + "            \"Jodhpur\",\n" + "            \"Karauli\",\n" + "            \"Kota\",\n" + "            \"Nagaur\",\n" + "            \"Pali\",\n" + "            \"Pratapgarh\",\n" + "            \"Rajsamand\",\n" + "            \"Sawai Madhopur\",\n" + "            \"Sikar\",\n" + "            \"Sirohi\",\n" + "            \"Sri Ganganagar\",\n" + "            \"Tonk\",\n" + "            \"Udaipur\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Sikkim\",\n" + "         \"districts\":[  \n" + "            \"East Sikkim\",\n" + "            \"North Sikkim\",\n" + "            \"South Sikkim\",\n" + "            \"West Sikkim\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Tamil Nadu\",\n" + "         \"districts\":[  \n" + "            \"Ariyalur\",\n" + "            \"Chennai\",\n" + "            \"Coimbatore\",\n" + "            \"Cuddalore\",\n" + "            \"Dharmapuri\",\n" + "            \"Dindigul\",\n" + "            \"Erode\",\n" + "            \"Kanchipuram\",\n" + "            \"Kanyakumari\",\n" + "            \"Karur\",\n" + "            \"Krishnagiri\",\n" + "            \"Madurai\",\n" + "            \"Nagapattinam\",\n" + "            \"Namakkal\",\n" + "            \"Nilgiris\",\n" + "            \"Perambalur\",\n" + "            \"Pudukkottai\",\n" + "            \"Ramanathapuram\",\n" + "            \"Salem\",\n" + "            \"Sivaganga\",\n" + "            \"Thanjavur\",\n" + "            \"Theni\",\n" + "            \"Thoothukudi (Tuticorin)\",\n" + "            \"Tiruchirappalli\",\n" + "            \"Tirunelveli\",\n" + "            \"Tiruppur\",\n" + "            \"Tiruvallur\",\n" + "            \"Tiruvannamalai\",\n" + "            \"Tiruvarur\",\n" + "            \"Vellore\",\n" + "            \"Viluppuram\",\n" + "            \"Virudhunagar\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Telangana\",\n" + "         \"districts\":[  \n" + "            \"Adilabad\",\n" + "            \"Bhadradri Kothagudem\",\n" + "            \"Hyderabad\",\n" + "            \"Jagtial\",\n" + "            \"Jangaon\",\n" + "            \"Jayashankar Bhoopalpally\",\n" + "            \"Jogulamba Gadwal\",\n" + "            \"Kamareddy\",\n" + "            \"Karimnagar\",\n" + "            \"Khammam\",\n" + "            \"Komaram Bheem Asifabad\",\n" + "            \"Mahabubabad\",\n" + "            \"Mahabubnagar\",\n" + "            \"Mancherial\",\n" + "            \"Medak\",\n" + "            \"Medchal\",\n" + "            \"Nagarkurnool\",\n" + "            \"Nalgonda\",\n" + "            \"Nirmal\",\n" + "            \"Nizamabad\",\n" + "            \"Peddapalli\",\n" + "            \"Rajanna Sircilla\",\n" + "            \"Rangareddy\",\n" + "            \"Sangareddy\",\n" + "            \"Siddipet\",\n" + "            \"Suryapet\",\n" + "            \"Vikarabad\",\n" + "            \"Wanaparthy\",\n" + "            \"Warangal (Rural)\",\n" + "            \"Warangal (Urban)\",\n" + "            \"Yadadri Bhuvanagiri\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Tripura\",\n" + "         \"districts\":[  \n" + "            \"Dhalai\",\n" + "            \"Gomati\",\n" + "            \"Khowai\",\n" + "            \"North Tripura\",\n" + "            \"Sepahijala\",\n" + "            \"South Tripura\",\n" + "            \"Unakoti\",\n" + "            \"West Tripura\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Uttarakhand\",\n" + "         \"districts\":[  \n" + "            \"Almora\",\n" + "            \"Bageshwar\",\n" + "            \"Chamoli\",\n" + "            \"Champawat\",\n" + "            \"Dehradun\",\n" + "            \"Haridwar\",\n" + "            \"Nainital\",\n" + "            \"Pauri Garhwal\",\n" + "            \"Pithoragarh\",\n" + "            \"Rudraprayag\",\n" + "            \"Tehri Garhwal\",\n" + "            \"Udham Singh Nagar\",\n" + "            \"Uttarkashi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"Uttar Pradesh\",\n" + "         \"districts\":[  \n" + "            \"Agra\",\n" + "            \"Aligarh\",\n" + "            \"Allahabad\",\n" + "            \"Ambedkar Nagar\",\n" + "            \"Amethi (Chatrapati Sahuji Mahraj Nagar)\",\n" + "            \"Amroha (J.P. Nagar)\",\n" + "            \"Auraiya\",\n" + "            \"Azamgarh\",\n" + "            \"Baghpat\",\n" + "            \"Bahraich\",\n" + "            \"Ballia\",\n" + "            \"Balrampur\",\n" + "            \"Banda\",\n" + "            \"Barabanki\",\n" + "            \"Bareilly\",\n" + "            \"Basti\",\n" + "            \"Bhadohi\",\n" + "            \"Bijnor\",\n" + "            \"Budaun\",\n" + "            \"Bulandshahr\",\n" + "            \"Chandauli\",\n" + "            \"Chitrakoot\",\n" + "            \"Deoria\",\n" + "            \"Etah\",\n" + "            \"Etawah\",\n" + "            \"Faizabad\",\n" + "            \"Farrukhabad\",\n" + "            \"Fatehpur\",\n" + "            \"Firozabad\",\n" + "            \"Gautam Buddha Nagar\",\n" + "            \"Ghaziabad\",\n" + "            \"Ghazipur\",\n" + "            \"Gonda\",\n" + "            \"Gorakhpur\",\n" + "            \"Hamirpur\",\n" + "            \"Hapur (Panchsheel Nagar)\",\n" + "            \"Hardoi\",\n" + "            \"Hathras\",\n" + "            \"Jalaun\",\n" + "            \"Jaunpur\",\n" + "            \"Jhansi\",\n" + "            \"Kannauj\",\n" + "            \"Kanpur Dehat\",\n" + "            \"Kanpur Nagar\",\n" + "            \"Kanshiram Nagar (Kasganj)\",\n" + "            \"Kaushambi\",\n" + "            \"Kushinagar (Padrauna)\",\n" + "            \"Lakhimpur - Kheri\",\n" + "            \"Lalitpur\",\n" + "            \"Lucknow\",\n" + "            \"Maharajganj\",\n" + "            \"Mahoba\",\n" + "            \"Mainpuri\",\n" + "            \"Mathura\",\n" + "            \"Mau\",\n" + "            \"Meerut\",\n" + "            \"Mirzapur\",\n" + "            \"Moradabad\",\n" + "            \"Muzaffarnagar\",\n" + "            \"Pilibhit\",\n" + "            \"Pratapgarh\",\n" + "            \"RaeBareli\",\n" + "            \"Rampur\",\n" + "            \"Saharanpur\",\n" + "            \"Sambhal (Bhim Nagar)\",\n" + "            \"Sant Kabir Nagar\",\n" + "            \"Shahjahanpur\",\n" + "            \"Shamali (Prabuddh Nagar)\",\n" + "            \"Shravasti\",\n" + "            \"Siddharth Nagar\",\n" + "            \"Sitapur\",\n" + "            \"Sonbhadra\",\n" + "            \"Sultanpur\",\n" + "            \"Unnao\",\n" + "            \"Varanasi\"\n" + "         ]\n" + "      },\n" + "      {  \n" + "         \"state\":\"West Bengal\",\n" + "         \"districts\":[  \n" + "            \"Alipurduar\",\n" + "            \"Bankura\",\n" + "            \"Birbhum\",\n" + "            \"Burdwan (Bardhaman)\",\n" + "            \"Cooch Behar\",\n" + "            \"Dakshin Dinajpur (South Dinajpur)\",\n" + "            \"Darjeeling\",\n" + "            \"Hooghly\",\n" + "            \"Howrah\",\n" + "            \"Jalpaiguri\",\n" + "            \"Kalimpong\",\n" + "            \"Kolkata\",\n" + "            \"Malda\",\n" + "            \"Murshidabad\",\n" + "            \"Nadia\",\n" + "            \"North 24 Parganas\",\n" + "            \"Paschim Medinipur (West Medinipur)\",\n" + "            \"Purba Medinipur (East Medinipur)\",\n" + "            \"Purulia\",\n" + "            \"South 24 Parganas\",\n" + "            \"Uttar Dinajpur (North Dinajpur)\"\n" + "         ]\n" + "      }\n" + "   ]\n" + "}";
    ArrayList<String> stateArrayList = new ArrayList<>();
    ArrayList<String> districtArrayList = new ArrayList<>();
    private String Selected_State = "", Select_City = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_user_registration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton_Black("");
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

    @OnClick(R.id.btnregister)
    public void onViewClicked() {

        if (UserAccount.isEmpty(etname, etmobile, etaddress, etpassword)) {
            if (UserAccount.isPhoneNumberLength(etmobile)) {
                if (UserAccount.checkPasswordStats(etpassword.getText().toString())) {
                    if (etpassword.getText().toString().equalsIgnoreCase(etconfirmpassword.getText().toString())) {

                        if (termsConditionCheckReg.isChecked()) {
                            Intent intent = new Intent(UserRegistrationActivity.this, OtpActivity.class);
                            intent.putExtra("Contact", etmobile.getText().toString());
                            intent.putExtra("invitaion_from", "user");
                            startActivityForResult(intent, 11);
                        } else {
                            ErrorMessage.T(this, "Please accept terms and conditions");

                        }
                    } else {
                        ErrorMessage.T(this, "Password mismatch!!");

                    }
                }
                else {
                   ErrorMessage.T(UserRegistrationActivity.this,"please create your password which contain one uppercase one lower case and one special character and must be 8 character long");

                }
            } else {
                UserAccount.EditTextPointer.setError("Enter a vaild mobile number!");
                UserAccount.EditTextPointer.requestFocus();
            }
        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                etmobile.setEnabled(false);
                register();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
        /*if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
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
        }*/
    }

    private void register() {
        if (NetworkUtil.isNetworkAvailable(UserRegistrationActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(UserRegistrationActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);/*SavedData.getFCM_ID()*/
            Call<ResponseBody> call = apiService.user_register(etname.getText().toString(), etmobile.getText().toString(), etaddress.getText().toString(),Select_City,Selected_State,etcity.getText().toString(),etpincode.getText().toString(), etpassword.getText().toString(), SavedData.getFCM_ID());

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
                                ErrorMessage.T(UserRegistrationActivity.this, object.getString("message"));

                                JSONObject jsonObject1 = object.getJSONObject("user_info");
                                UserProfileModel userProfileModel = new UserProfileModel();
                                userProfileModel.setDisplayName(jsonObject1.getString("full_name"));
                                userProfileModel.setUser_id(jsonObject1.getString("user_id"));
                                userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                userProfileModel.setDistrict(jsonObject1.getString("district"));
                              //  userProfileModel.setCity(jsonObject1.getString("city"));
                                userProfileModel.setEmaiiId(jsonObject1.getString("address"));

                                UserProfileHelper.getInstance().delete();
                                userProfileModel.setProvider("user");
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "User");
                                ErrorMessage.I_clear(UserRegistrationActivity.this, UserHomeActivity.class, bundle);

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(UserRegistrationActivity.this, object.getString("message"));
                                materialDialog.dismiss();
                            }
                        }  catch (Exception e) {
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
            ErrorMessage.T(UserRegistrationActivity.this, "No Internet");
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


    @OnClick({R.id.etaddress, R.id.tvterms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.etaddress:
                googleAutoCompleat();
                break;
            case R.id.tvterms:
                Bundle bundle=new Bundle();
                bundle.putString("from","user");
                ErrorMessage.I(UserRegistrationActivity.this, TermsAndConditionActivity.class,bundle);
                break;
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
