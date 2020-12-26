package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.login.api.api
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var usermail : String? = null
    val api : api = api()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val mail = intent.getStringExtra("mail")
        usermail = mail

        toolbar.setTitle("Time Capsule")
        setSupportActionBar(toolbar)

        api.userCheck(usermail!!){
            if(!it){
                api.newUser(usermail!!)
            }
        }

        /*val okHttpClient = OkHttpClient()
        val request_check: Request = Request.Builder()
            .url("http://10.0.2.2:8088/user/check/" + usermail!!)
            .build()

        okHttpClient.newCall(request_check).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val code = response.code()
                val body = response.body()?.string()
                println("home   " + body)
                val check = body.toString().toBoolean()
                println("home tf  " + check)
                if(!check){
                    val okHttpClient = OkHttpClient()
                    val json_body = JSONObject()
                    json_body.put("userGmail", usermail)
                    json_body.put("purchaseda", false)
                    json_body.put("purchasedb", false)
                    val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json_body.toString())
                    val request_user: Request = Request.Builder()
                        .url("http://10.0.2.2:8088/user")
                        .post(body)
                        .build()

                    okHttpClient.newCall(request_user).enqueue(object:Callback{
                        override fun onFailure(call: Call, e: IOException) {

                        }

                        override fun onResponse(call: Call, response: Response) {
                            println("home final  " + check)
                        }
                    })
                }
            }
        })*/

        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        mainAct.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.meContainer, HomeFragment.newInstance(usermail!!), "Home").commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> {
                supportFragmentManager.beginTransaction().replace(R.id.meContainer, HomeFragment.newInstance(usermail!!), "Home").commit()
            }
            R.id.write -> {
                //supportFragmentManager.beginTransaction().replace(R.id.meContainer, WriteFragment.newInstance(usermail!!), "Write").commit()
                val intent = Intent(this, WriteActivity::class.java)
                intent.putExtra("mail", usermail)
                startActivity(intent)
            }
            R.id.profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.meContainer, ProfileFragment.newInstance(usermail!!), "Profile").commit()
            }
        }
        mainAct.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("Detail") != null){
            supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentByTag("Detail")!!).commit()
            mailList.visibility = View.VISIBLE
        } else if(supportFragmentManager.findFragmentByTag("Profile") != null){
            supportFragmentManager.beginTransaction().replace(R.id.meContainer, HomeFragment.newInstance(usermail!!), "Home").commit()
        } else {
            super.onBackPressed()
        }
    }


}