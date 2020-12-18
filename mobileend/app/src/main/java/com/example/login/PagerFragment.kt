package com.example.login

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PagerFragment : Fragment() {
    var mailId: String? = null
    var type: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mailId = it.getString(ARG_PARAM1)
            type = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(type == 1){
            val okHttpClient = OkHttpClient()
            val request_mail: Request = Request.Builder().url("http://10.0.2.2:8088/mail/" + mailId).build()
            okHttpClient.newCall(request_mail).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call, response: Response) {
                    val c = response.code()
                    val body = response.body()?.string()
                    val js = JSONObject(body)
                    val textId = js.get("text")

                    val okHttpClient = OkHttpClient()
                    val request_text: Request = Request.Builder().url("http://10.0.2.2:8088/text/" + textId).build()
                    okHttpClient.newCall(request_text).enqueue(object:Callback{
                        override fun onFailure(call: Call, e: IOException) {

                        }

                        override fun onResponse(call: Call, response: Response) {
                            val c = response.code()
                            val body = response.body()?.string()
                            val js = JSONObject(body)
                            val text_ = js.get("text")
                            ThreadUtils.runOnUiThread {
                                val text = view.findViewById<TextView>(R.id.textView)
                                text.text = text_.toString()
                            }
                        }

                    })
                }

            })


        } else {
            val okHttpClient = OkHttpClient()
            val request_mail: Request = Request.Builder().url("http://10.0.2.2:8088/mail/" + mailId).build()
            okHttpClient.newCall(request_mail).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call, response: Response) {
                    val c = response.code()
                    val body = response.body()?.string()
                    val js = JSONObject(body)
                    val imageId = js.get("image")
                    if(imageId.toString().toInt() == 0){
                        return
                    }

                    val okHttpClient = OkHttpClient()
                    val request_text: Request = Request.Builder().url("http://10.0.2.2:8088/image/" + imageId).build()
                    okHttpClient.newCall(request_text).enqueue(object:Callback{
                        override fun onFailure(call: Call, e: IOException) {

                        }

                        override fun onResponse(call: Call, response: Response) {
                            val c = response.code()
                            val body = response.body()?.string()
                            val js = JSONObject(body)
                            val filename = js.get("file")
                            ThreadUtils.runOnUiThread {
                                val text = view.findViewById<TextView>(R.id.textView)
                                text.layoutParams.height = 0
                                val img = view.findViewById<ImageView>(R.id.detailImage)
                                val path : String = "https://timecapsuleforandroid.s3.us-east-2.amazonaws.com/" + filename.toString()
                                Picasso.get().load(path).into(img)
                            }
                        }

                    })
                }

            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(mailId: String, type: Int) =
            PagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mailId)
                    putInt(ARG_PARAM2, type)
                }
            }
    }
}