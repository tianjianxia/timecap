package com.example.login

import android.annotation.SuppressLint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.example.login.api.api
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.*


private const val ARG_PARAM1 = "param1"
private val okHttpClient = OkHttpClient()
class HomeFragment : Fragment() {
    var usermail : String? = null
    val api : api = api()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usermail = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println(usermail)

        api.getUser(usermail!!){
            val gson = GsonBuilder().create()
            val Json = gson.fromJson(it, UserDo::class.java)

            //Switch to the UI Thread
            runOnUiThread {
                val username = view.findViewById<TextView>(R.id.username)
                val token = view.findViewById<TextView>(R.id.token)
                val token2 = view.findViewById<TextView>(R.id.token2)

                username.text = usermail!!
                token.text = "Gallery Access:" + if(Json.purchaseda) "Token access activated." else "No token access."
                token2.text = "Camera Access:" + if(Json.purchasedb) "Token access activated." else "No token access."
            }
        }

        /*val Uri_1 = "http://10.0.2.2:8088/user/" + usermail!!
        val request_1 : Request = Request.Builder()
            .url(Uri_1)
            .build()
        okHttpClient.newCall(request_1).enqueue(object : Callback {
            @SuppressLint("ClickableViewAccessibility")
            override fun onResponse(call: Call, response: Response) {
                val c = response.code()
                val body = response.body()?.string()
                //val js = JSONObject(body)
                val gson = GsonBuilder().create()
                val Json = gson.fromJson(body, UserDo::class.java)

                //Switch to the UI Thread
                runOnUiThread {
                    val username = view.findViewById<TextView>(R.id.username)
                    val token = view.findViewById<TextView>(R.id.token)
                    val token2 = view.findViewById<TextView>(R.id.token2)

                    username.text = usermail!!
                    token.text = "Gallery Access:" + if(Json.purchaseda) "Token access activated." else "No token access."
                    token2.text = "Camera Access:" + if(Json.purchasedb) "Token access activated." else "No token access."
                }
            }

            //Http request exception handling
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    e.printStackTrace()
                    Toast.makeText(
                        activity!!.applicationContext,
                        "network failure :( Cannot retrieve JSON data from backend.",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
        })*/

        api.mailCount(usermail!!){
            runOnUiThread {
                val write = view.findViewById<TextView>(R.id.write)

                write.text = it.toString()
            }
        }



        /*val Uri_2 = "http://10.0.2.2:8088/user/count/" + usermail!!
        val request_2 : Request = Request.Builder()
            .url(Uri_2)
            .build()
        okHttpClient.newCall(request_2).enqueue(object : Callback {
            @SuppressLint("ClickableViewAccessibility")
            override fun onResponse(call: Call, response: Response) {
                val code = response.code()
                val body = response.body()?.string()

                //Switch to the UI Thread
                runOnUiThread {
                    val write = view.findViewById<TextView>(R.id.write)

                    write.text = body.toString()
                }
            }

            //Http request exception handling
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "network failure :( Cannot retrieve JSON data from backend.",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
        })*/

    }



    companion object {
        @JvmStatic
        fun newInstance(mail: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mail)
                }
            }
    }


}

data class UserDo(val purchaseda : Boolean, val purchasedb : Boolean);