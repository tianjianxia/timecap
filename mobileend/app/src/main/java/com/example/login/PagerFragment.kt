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
import com.example.login.api.api
import com.example.login.config.Config
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
    val api : api = api()

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
            api.getMailInfo(mailId!!){
                val js = JSONObject(it)
                val textId = js.get("text").toString()
                api.getText(textId){
                    val js = JSONObject(it)
                    val text_ = js.get("text")
                    ThreadUtils.runOnUiThread {
                        val text = view.findViewById<TextView>(R.id.textView)
                        text.text = text_.toString()
                    }
                }
            }
        } else {
            api.getMailInfo(mailId!!){
                val js = JSONObject(it)
                val imageId = js.get("image").toString()
                if(imageId.toInt() != 0){
                    api.getImage(imageId){
                        val js = JSONObject(it)
                        val filename = js.get("file")
                        ThreadUtils.runOnUiThread {
                            val text = view.findViewById<TextView>(R.id.textView)
                            text.layoutParams.height = 0
                            val img = view.findViewById<ImageView>(R.id.detailImage)
                            val path : String = Config.awsS3Endpoint + filename.toString()
                            Picasso.get().load(path).into(img)
                        }
                    }
                }
            }
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