package com.example.login.api

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.example.login.R
import com.example.login.config.Config
import kotlinx.android.synthetic.main.activity_write.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class api {
    val okHttpClient = OkHttpClient()

    // check user exist
    fun userCheck(usermail : String, then: ((Boolean) -> Unit)){
        val url = Config.apiEndpoint + "/user/check/" + usermail
        val request: Request = Request.Builder()
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val check = body.toString().toBoolean()
                then(check)
            }
        })
    }

    // new user
    fun newUser(usermail : String){
        val json_body = JSONObject()
        json_body.put("userGmail", usermail)
        json_body.put("purchaseda", false)
        json_body.put("purchasedb", false)

        val url = Config.apiEndpoint + "/user"
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json_body.toString())
        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        okHttpClient.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }
        })
    }

    // count users mails
    fun mailCount(usermail : String, then: ((String) -> Unit)){
        val url = Config.apiEndpoint + "/user/count/" + usermail
        val request : Request = Request.Builder()
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            @SuppressLint("ClickableViewAccessibility")
            override fun onResponse(call: Call, response: Response) {
                val code = response.code()
                val body = response.body()?.string()
                then(body.toString())
            }

            //Http request exception handling
            override fun onFailure(call: Call, e: IOException) {

            }
        })
    }

    // get user
    fun getUser(usermail : String, then: ((String) -> Unit)){
        val request: Request = Request.Builder().url("http://10.0.2.2:8088/user/" + usermail!!).build()
        okHttpClient.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val code = response.code()
                val body = response.body()?.string()
                then(body.toString())
            }

        })
    }

    // put mail
    fun putMail(body : RequestBody, then: ((String) -> Unit)){
        val url = Config.apiEndpoint + "/mail"
        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                then(res.toString())
            }

        })
    }

    // get current mail
    fun getCurrentMail(then : ((String) -> Unit)){
        val url = Config.apiEndpoint + "/mail/current"
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object:Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                then(body.toString())
            }
        })
    }

    // put text
    fun putText(body : RequestBody, then: ((String) -> Unit)){
        val url = Config.apiEndpoint
        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        okHttpClient.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                then(res.toString())
            }

        })
    }

    // update text id
    fun updateTextId(mailId : String, id : String){
        val url = Config.apiEndpoint + "/mail/text/" + mailId + "/" + id
        val request : Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }

        })
    }

    // update image id null
    fun updateImageIdNull(mailId : String){
        val url = Config.apiEndpoint + "/mail/image/" + mailId + "/0"
        val request : Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }

        })
    }

    // put image
    fun putImage(body : RequestBody, then: ((String) -> Unit)){
        val url = Config.apiEndpoint + "/image"
        val request_image: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        okHttpClient.newCall(request_image).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                then(res.toString())
            }

        })
    }

    // update image id
    fun updateImageId(mailId : String, id : String){
        val url = Config.apiEndpoint + "/mail/image/" + mailId + "/" + id
        val request : Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }

        })
    }

    // send mail
    fun sendMail(body : RequestBody){
        val url = Config.apiEndpoint + "/sendnew"
        val request : Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }

        })

    }

    // get mail list
    fun getMailList(usermail : String, then: ((String) -> Unit)){
        val url = Config.apiEndpoint + "/user/mail/" + usermail
        val request: Request = Request.Builder()
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                then(res.toString())
            }

        })
    }

    // get mail info
    fun getMailInfo(mailId : String, then : ((String) -> Unit)){
        val url = Config.apiEndpoint + "/mail/" + mailId
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                then(res.toString())
            }

        })

    }

    // get text
    fun getText(textId : String, then : ((String) -> Unit)){
        val url = Config.apiEndpoint + "/text/" + textId
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                then(res.toString())
            }

        })
    }

    // get image
    fun getImage(imageId : String, then : ((String) -> Unit)){
        val url = Config.apiEndpoint + "/image/" + imageId
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                then(res.toString())
            }

        })
    }




}




