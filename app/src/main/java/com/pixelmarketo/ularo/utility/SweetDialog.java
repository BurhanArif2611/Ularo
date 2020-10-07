package com.pixelmarketo.ularo.utility;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.pixelmarketo.ularo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetDialog {
    public static void messageDialog(Context context, String msg){
        SweetAlertDialog pDialog= new SweetAlertDialog(context);
        pDialog.setTitleText(msg);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    public static void toastDialog(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
    public static void edittextDialogedittextDialog(Context context, final callBackEdit callback){
        final EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        final SweetAlertDialog dialog=  new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialog.setTitleText("Enter OTP");
        dialog.setConfirmText("Ok");
        dialog.setCustomView(editText);
        dialog.setCancelText("CANCEL");
        dialog.setCancelable(false);
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                callback.callBackData(editText.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });


    }
    public static void chooseDialog(Context context, final DialogCallBack callBack){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_chooser);
        final TextView camera = (TextView) dialog.findViewById(R.id.camera);
        final TextView gallery = (TextView) dialog.findViewById(R.id.gallery);
        final TextView cancel = (TextView) dialog.findViewById(R.id.cancel);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                callBack.ok();
                dialog.dismiss();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                callBack.pic();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                callBack.cancel();
                dialog.dismiss();

            }
        });

        /* WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
         lp.copyFrom(dialog.getWindow().getAttributes());
         lp.width = WindowManager.LayoutParams.MATCH_PARENT;
         lp.height = WindowManager.LayoutParams.WRAP_CONTENT;*/
        dialog.show();
//         dialog.getWindow().setAttributes(lp);


    }
    public interface DialogCallBack{
        public void ok();
        public void pic();
        public void cancel();
    }

    public interface callBackEdit{
        public void callBackData(String value);
    }
}
