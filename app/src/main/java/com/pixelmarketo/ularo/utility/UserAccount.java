package com.pixelmarketo.ularo.utility;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAccount {
    //for EditText Refrance
    public static EditText EditTextPointer;
    public static String errorMessage;
    private EditText userName, password;
    private Context mCont;

    public UserAccount(Context mCont, EditText un, EditText pw) {
        this.userName = un;
        this.password = pw;
        this.mCont = mCont;
        isLoginInit(userName, password);
    }

    private static void isLoginInit(EditText userName, EditText password) {
        int maxLength = 10;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        //this is for userName
        userName.setHint("Enter Email / Contact No");
        userName.setSingleLine(true);
        userName.setMaxLines(1);

        password.setHint("Enter Passwrod");
        password.setSingleLine(true);
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setMaxLines(1);
        password.setFilters(fArray);

    }

    public static boolean isEmailValid(EditText tv) {
        if (TextUtils.isEmpty(tv.getText())) {
            EditTextPointer = tv;
            errorMessage = "This field can't be empty.!";
            return false;
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(tv.getText()).matches()) {
                return true;
            } else {
                EditTextPointer = tv;
                errorMessage = "Invalid Email Id";
                return false;
            }
        }
    }

    public static boolean isPasswordValid(EditText tv) {

        if (tv.getText().toString().length() >= 6) {
            return true;
        } else {
            EditTextPointer = tv;
            errorMessage = "Greater than 6 char";
            return false;
        }
    }

    public static boolean isPhoneNumberLength(EditText tv) {

        if (tv.getText().toString().length() == 10) {
            return true;
        } else {
            EditTextPointer = tv;
            errorMessage = "Enter 10 digits number";
            return false;
        }
    }

    public static boolean isPasswordLength(EditText tv) {
        //add your own logic
        if (tv.getText().toString().length() >= 8) {
            return true;
        } else {
            EditTextPointer = tv;
            errorMessage = "Enter 6 digits number";
            return false;
        }
    }

    public static boolean isPIN(EditText tv) {
        //add your own logic
        if (tv.getText().toString().length() == 16) {
            return true;
        } else {
            EditTextPointer = tv;
            errorMessage = "Enter 16 digits number";
            return false;
        }
    }

    public static final boolean isValidPhoneNumber(EditText tv) {
        if (tv.getText() == null || TextUtils.isEmpty(tv.getText())) {
            return false;
        } else {
            if (android.util.Patterns.PHONE.matcher(tv.getText()).matches()) {
                return true;
            } else {
                EditTextPointer = tv;
                errorMessage = "Invalid Mobile No.";
                return false;
            }
        }
    }

    public static boolean isEmpty(EditText... arg) {
        for (int i = 0; i < arg.length; i++) {
            if (arg[i].getText().length() <= 0) {
                EditTextPointer = arg[i];
                EditTextPointer.requestFocus();
                return false;
            }

        }
        return true;
    }

    public static boolean isBidValid(EditText tv) {

        if (Integer.parseInt(tv.getText().toString()) <= 100) {
            return true;
        } else {
            EditTextPointer = tv;
            errorMessage = "Less than or equal 100%";
            return false;
        }
    }

    /* public static boolean validateAadharNumber(String aadharNumber) {
         Pattern aadharPattern = Pattern.compile("\\d{11}");
         boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
         if (isValidAadhar) {
             isValidAadhar = Verhoeff.validateVerhoeff(aadharNumber);
         }
         return isValidAadhar;
     }*/
    public static boolean checkPasswordStats(String str) {
        if (str.length()>=8){
            char ch;
            boolean capitalFlag = false;
            boolean lowerCaseFlag = false;
            boolean numberFlag = false;
            boolean numOfSpecial = false;
            for (int i = 0; i < str.length(); i++) {
                ch = str.charAt(i);
                if (Character.isDigit(ch)) {
                    numberFlag = true;
                } else if (Character.isUpperCase(ch)) {
                    capitalFlag = true;
                } else if (Character.isLowerCase(ch)) {
                    lowerCaseFlag = true;
                } else  {
                    Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
                    Matcher match = pattern.matcher(str);
                    boolean val = match.find();
                    numOfSpecial = val;
                }
                if (numberFlag && capitalFlag && lowerCaseFlag && numOfSpecial) return true;
            }
        }else {
            return false;
        }
        return false;

    }

    public static boolean checkString(String str) {
        if (str.length()>=8){
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean numOfSpecial = false;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            } else  {
                Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
                Matcher match = pattern.matcher(str);
                boolean val = match.find();
                numOfSpecial = val;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag && numOfSpecial) return true;
        }
        }else {
            return false;
        }
        return false;
    }
}