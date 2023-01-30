package com.example.matriculadevehiculo.util

import android.app.ProgressDialog
import android.content.Context
import com.example.matriculadevehiculo.R


object Initializer {

    // Progress dialog
    lateinit var progressDialog: ProgressDialog


    fun initProgressDialog(context: Context) {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage(context.getString(R.string.loading_msg))
        progressDialog.setCancelable(true)
    }

}