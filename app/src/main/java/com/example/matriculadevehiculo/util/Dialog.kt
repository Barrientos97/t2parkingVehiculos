package com.example.matriculadevehiculo.util

import com.example.matriculadevehiculo.util.Initializer.progressDialog

object Dialog {

    fun showProgressDialog() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
            progressDialog.setCanceledOnTouchOutside(false)
        }
    }

    fun dismissProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
            progressDialog.setCanceledOnTouchOutside(true)
        }
    }

}