package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


private const val ARG_PARAM1 = "param1"

class ProfileFragment : Fragment(), mailAdapter.AdapterListener  {
    var mailAdapter : mailAdapter? = null
    var usermail : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
        arguments?.let {
            usermail = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mailListener: mailAdapter.AdapterListener = this
        val okHttpClient = OkHttpClient()
        val request_mailIds: Request = Request.Builder()
            .url("http://10.0.2.2:8088/user/mail/" + usermail!!)
            .build()
        okHttpClient.newCall(request_mailIds).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call, response: Response) {
                val code = response.code()
                val body_mailIds = response.body()?.string()
                val json = JSONArray(body_mailIds)
                val arr = ArrayList<Mail>()
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    val id = item.get("id").toString()
                    val from = item.get("fromUser").toString()
                    val to = item.get("toUser").toString()
                    val senddate = LocalDate.parse(
                        item.get("sendDate").toString(),
                        DateTimeFormatter.ISO_DATE
                    )
                    val opendate = LocalDate.parse(
                        item.get("openDate").toString(),
                        DateTimeFormatter.ISO_DATE
                    )

                    val m = Mail(id, from, to, senddate, opendate)
                    arr.add(m)
                }

                ThreadUtils.runOnUiThread {
                    mailAdapter = mailAdapter(arr)
                    mailAdapter!!.setAdapterListener(mailListener)
                    mailList.apply {
                        layoutManager = LinearLayoutManager(view.context)
                        adapter = AlphaInAnimationAdapter(mailAdapter!!).apply {
                            setDuration(1000)
                            setInterpolator(OvershootInterpolator())
                            setFirstOnly(false)
                        }
                    }
                }
            }

        })


        donate.setOnClickListener {
            val popup = PopupMenu(context!!, view)
            val menuInflater = popup.menuInflater
            menuInflater.inflate(R.menu.menu_pop, popup.menu)

            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_img -> {
                        val Dialog = PurchaseDialog.newInstance(usermail!!, 1)
                        Dialog.show(activity!!.supportFragmentManager, "Dialog1")

                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_cam -> {
                        val Dialog = PurchaseDialog.newInstance(usermail!!, 2)
                        Dialog.show(activity!!.supportFragmentManager, "Dialog2")

                        return@setOnMenuItemClickListener true
                    }
                    else ->{
                        return@setOnMenuItemClickListener false
                    }
                }
            }
            // show icon on the popup menu!!
            val menuHelper = MenuPopupHelper(this.context!!, popup.menu as MenuBuilder, view.findViewById(R.id.donate))
            menuHelper.setForceShowIcon(true)
            menuHelper.gravity = Gravity.START
            menuHelper.show()
        }

        back.setOnClickListener {
            val fm = (activity as AppCompatActivity).supportFragmentManager

            if (fm.findFragmentByTag("Detail") != null){
                fm.beginTransaction().remove(fm.findFragmentByTag("Detail")!!).commit()
                mailList.visibility = View.VISIBLE
            } else {
                activity!!.onBackPressed()
            }
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(mail: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mail)
                }
            }
    }

    override fun itemOnClick(id: String) {
        var fm = (activity as AppCompatActivity).supportFragmentManager
        mailList.visibility = View.INVISIBLE
        fm.beginTransaction().replace(R.id.side, DetailFragment.newInstance(id), "Detail").commit()

    }
}