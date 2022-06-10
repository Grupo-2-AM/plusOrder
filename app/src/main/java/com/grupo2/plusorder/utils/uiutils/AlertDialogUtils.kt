package com.grupo2.plusorder.utils.uiutils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object AlertDialogUtils {

    fun ShowOkAlertBox(title: String, message: String, context: Context){
        // Create alert dialog builder
        var alertBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            //.setIcon()
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->  })

        // Create & show alert dialog
        var alertDialog: AlertDialog = alertBuilder.create()
        alertDialog.show()
    }
}