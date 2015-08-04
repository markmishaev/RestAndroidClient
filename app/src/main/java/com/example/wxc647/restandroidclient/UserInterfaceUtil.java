package com.example.wxc647.restandroidclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by wxc647 on 8/4/2015.
 */
public class UserInterfaceUtil
{
    public void showAlert(Context context, String message, String title)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(R.string.dialog_message_ok, null);


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
