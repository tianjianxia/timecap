package com.example.login

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import okhttp3.*
import java.io.IOException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class PurchaseDialog : DialogFragment() {
    var usermail : String? = null
    var type : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usermail = it.getString(ARG_PARAM1)
            type = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val gContext = if(type == 1) "gallery" else "camera"
            builder.setMessage("Are you sure to buy " + gContext +" access?")
                .setPositiveButton("Buy",
                    DialogInterface.OnClickListener { dialog, id ->
                        val gc = if(type == 1) "a" else "b"
                        val okHttpClient = OkHttpClient()
                        val request_a: Request = Request.Builder()
                            .url("http://10.0.2.2:8088/user/"+ gc +"/" + usermail!!)
                            .build()

                        okHttpClient.newCall(request_a).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {

                            }

                            override fun onResponse(call: Call, response: Response) {

                            }

                        })
                        val uri: Uri = Uri.parse("https://www.paypal.com/c2/home?locale.x=en_C2")
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        @JvmStatic
        fun newInstance(mail: String, type: Int) =
            PurchaseDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mail)
                    putInt(ARG_PARAM2, type)
                }
            }
    }
}