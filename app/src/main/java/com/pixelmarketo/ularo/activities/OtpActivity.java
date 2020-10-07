package com.pixelmarketo.ularo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderRegistrationActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
import com.pixelmarketo.ularo.userContent.UserProfileActivity;
import com.pixelmarketo.ularo.userContent.UserRegistrationActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.UserAccount;


import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.continue_btn)
    Button continueBtn;
    @BindView(R.id.editTextone)
    EditText editTextone;
    @BindView(R.id.editTexttwo)
    EditText editTexttwo;
    @BindView(R.id.editTextthree)
    EditText editTextthree;
    @BindView(R.id.editTextfour)
    EditText editTextfour;
    @BindView(R.id.editTextfifth)
    EditText editTextfifth;
    @BindView(R.id.editTextsixth)
    EditText editTextsixth;
    @BindView(R.id.tvotpmobile)
    TextView tvotpmobile;
    private String mVerificationId = "";
    private FirebaseAuth mAuth;
    String AllResponse = "",from="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("fr");
        mAuth.useAppLanguage();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ErrorMessage.E("OTP "+bundle.getString("Contact"));
            sendVerificationCode(bundle.getString("Contact"));
            tvotpmobile.setText("Please type verification code send to "+bundle.getString("Contact"));
            from = bundle.getString("invitaion_from");
        }
        editTextone.addTextChangedListener(this);
        editTexttwo.addTextChangedListener(this);
        editTextthree.addTextChangedListener(this);
        editTextfour.addTextChangedListener(this);
        editTextfifth.addTextChangedListener(this);
        editTextsixth.addTextChangedListener(this);
    }

    @OnClick(R.id.continue_btn)
    public void onClick() {
        if (UserAccount.isEmpty(editTextone, editTexttwo, editTextthree, editTextfour, editTextfifth, editTextsixth)) {
            verifyVerificationCode(editTextone.getText().toString() + editTexttwo.getText().toString() + editTextthree.getText().toString() + editTextfour.getText().toString() + editTextfifth.getText().toString() + editTextsixth.getText().toString());
        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }

    }


    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobile, 60, TimeUnit.SECONDS, OtpActivity.this, mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            try {
                if (code != null) {
                    String[] part = code.split("");
                    editTextone.setText(part[1]);
                    editTexttwo.setText(part[2]);
                    editTextthree.setText(part[3]);
                    editTextfour.setText(part[4]);
                    editTextfifth.setText(part[5]);
                    editTextsixth.setText(part[6]);
                    //verifying the code
                    verifyVerificationCode(code);
                }
            } catch (Exception e) {
                try {
                    String[] part = code.split("");
                    editTextone.setText(part[0]);
                    editTexttwo.setText(part[1]);
                    editTextthree.setText(part[2]);
                    editTextfour.setText(part[3]);
                    editTextfifth.setText(part[4]);
                    editTextsixth.setText(part[5]);
                    //verifying the code
                    verifyVerificationCode(code);
                }catch (Exception e1){}
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("FirebaseException", "" + e.getMessage());

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            try{
            mVerificationId = s;}catch (Exception e){}
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            ErrorMessage.E(e.getMessage());
            ErrorMessage.T(OtpActivity.this, "Verification Code is wrong");

        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    try {
                       /* if (from.equalsIgnoreCase("forgot password")) {
                            Intent returnIntent = new Intent(OtpActivity.this, ForgotPasswordActivity.class);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                        else if (from.equalsIgnoreCase("user")){
                            Intent returnIntent = new Intent(OtpActivity.this, UserRegistrationActivity.class);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }

                        else {
                            Intent returnIntent = new Intent(OtpActivity.this, BidderRegistrationActivity.class);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }*/
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();

                    } catch (Exception e) {
                    }
                } else {
                    //verification unsuccessful.. display an error message
                    String message = "Somthing is wrong, we will fix it soon...";
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }
                    Toast.makeText(OtpActivity.this, message, Toast.LENGTH_LONG).show();
                    Log.e("task", "" + message);
                           /* Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();*/
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        {
            if (editable.length() == 1) {
                if (editTextone.length() == 1) {
                    editTexttwo.requestFocus();
                }
                if (editTexttwo.length() == 1) {
                    editTextthree.requestFocus();
                }
                if (editTextthree.length() == 1) {
                    editTextfour.requestFocus();
                }
                if (editTextfour.length() == 1) {
                    editTextfifth.requestFocus();
                }
                if (editTextfifth.length() == 1) {
                    editTextsixth.requestFocus();
                }
            } else if (editable.length() == 0) {
                if (editTextsixth.length() == 0) {
                    editTextfifth.requestFocus();
                }
                if (editTextfifth.length() == 0) {
                    editTextfour.requestFocus();
                }
                if (editTextfour.length() == 0) {
                    editTextthree.requestFocus();
                }
                if (editTextthree.length() == 0) {
                    editTexttwo.requestFocus();
                }
                if (editTexttwo.length() == 0) {
                    editTextone.requestFocus();
                }
            }
        }
    }


}
