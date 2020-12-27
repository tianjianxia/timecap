package com.example.login

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.api.api
import com.example.login.config.Config
import kotlinx.android.synthetic.main.activity_write.*
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.util.*


class WriteActivity : AppCompatActivity() {
    var usermail : String? = null
    var file : File? = null
    var y: Int? = null
    var m: Int? = null
    var d: Int? = null
    var cy : Int? = null
    var cm : Int? = null
    var cd : Int? = null
    val api : api = api()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        toolbar2.setTitle("New Capsule")
        toolbar2.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        setSupportActionBar(toolbar2)
        toolbar2.setNavigationOnClickListener {
            onBackPressed()
        }
        usermail = intent.getStringExtra("mail")

        api.getUser(usermail!!){
            println(usermail)
            val json = JSONObject(it)
            val purchasea = json.get("purchaseda").toString().toBoolean()
            val purchaseb = json.get("purchasedb").toString().toBoolean()
            runOnUiThread {
                gallery.visibility = if(purchasea) View.VISIBLE else View.INVISIBLE
                camera.visibility = if(purchaseb) View.VISIBLE else View.INVISIBLE
                if(!purchasea && !purchaseb){
                    imageView.layoutParams.height = 0
                }
            }
        }
        
        val c = Calendar.getInstance()
        val cal_y = c.get(Calendar.YEAR)
        val cal_m = c.get(Calendar.MONTH)
        val cal_d = c.get(Calendar.DAY_OF_MONTH)
        y = cal_y
        m = cal_m
        d = cal_d
        cy = cal_y
        cm = cal_m
        cd = cal_d

        if(savedInstanceState != null){
            usermail = savedInstanceState.getString("usermail")
            if(savedInstanceState.getString("file")!!.length == 0){
                file = null
            } else {
                file = File(savedInstanceState.getString("file"))
            }
            if(savedInstanceState.getInt("y")!! == 0){
                y = null
            } else {
                y = savedInstanceState.getInt("y")
            }
            if(savedInstanceState.getInt("m")!! == 0){
                m = null
            } else {
                m = savedInstanceState.getInt("m")
            }
            if(savedInstanceState.getInt("d")!! == 0){
                d = null
            } else {
                d = savedInstanceState.getInt("d")
            }

        }

        gallery.setOnClickListener {
            if(file != null){
                Toast.makeText(applicationContext, "Already attach a photo!", Toast.LENGTH_LONG).show()
            } else {
                pickImageFromGallery();
            }
        }
        camera.setOnClickListener {
            if(file != null){
                Toast.makeText(applicationContext, "Already attach a photo!", Toast.LENGTH_LONG).show()
            } else {
                takePhoto();
            }
        }
        dateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val cal_y = c.get(Calendar.YEAR)
            val cal_m = c.get(Calendar.MONTH)
            val cal_d = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this@WriteActivity,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    y = year
                    m = month
                    d = day
                },
                cal_y,
                cal_m,
                cal_d
            )

            dpd.show()

        }

        button.setOnClickListener {
            if(input_content.text == null || input_recieve.text == null || input_content.text!!.length < 20 || input_recieve.text!!.length < 10){
                if(input_content.text == null || input_recieve.text == null){
                    Toast.makeText(applicationContext, "Reciever and content section must not be null!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Reciver must longer than 5, Content must longer than 20.", Toast.LENGTH_LONG).show()
                }
            } else {
                val okHttpClient = OkHttpClient()
                val json_body = JSONObject()
                json_body.put("textId", "1")
                json_body.put("imageId", "-1")
                json_body.put("fromUser", usermail!!)
                json_body.put("toUser", input_recieve.text!!.toString())
                json_body.put("sendDate", cy!!.toString() + "-" + cm!!.toString() + "-" + cd!!.toString())
                json_body.put("openDate", y!!.toString() + "-" + m!!.toString() + "-" + d!!.toString())
                val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json_body.toString())
                api.putMail(body){
                    api.getCurrentMail {
                        val mailId = it
                        json_body.put("text", input_content.text!!.toString())
                        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json_body.toString())
                        api.putText(body){
                            val textId = it
                            api.updateTextId(mailId, textId)
                        }
                        api.updateImageIdNull(mailId)
                        if(file != null){
                            val imageName: String = file!!.getName()
                            Upload(file!!).execute(
                                imageName,
                                Config.awsKey,
                                Config.awsKeyPair
                            )
                            val json = JSONObject()
                            json.put("file", file!!.getName())
                            val body_image = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())
                            api.putImage(body_image){
                                val imageId = it
                                api.updateImageId(mailId, imageId)
                            }
                        }
                    }
                }

                val json_send = JSONObject()
                val path = if(file == null) "" else "https://timecapsuleforandroid.s3.us-east-2.amazonaws.com/" + file!!.name.toString()
                json_send.put("to", input_recieve.text!!.toString())
                json_send.put("content", input_content.text!!.toString())
                json_send.put("path", path)
                json_send.put("date", m!!.toString() + "-" + d!!.toString() + "-" + y!!.toString())
                val body_send = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json_send.toString())

                api.sendMail(body_send)

                Toast.makeText(applicationContext, "Capsule sent!", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("usermail", usermail)
        val path = if(file == null) "" else file!!.absolutePath
        outState.putString("file", path)
        val put_y = if(y == null) 0 else y!!
        val put_m = if(m == null) 0 else m!!
        val put_d = if(d == null) 0 else d!!
        val put_cy = if(cy == null) 0 else cy!!
        val put_cm = if(cm == null) 0 else cm!!
        val put_cd = if(cd == null) 0 else cd!!
        outState.putInt("y", put_y)
        outState.putInt("m", put_m)
        outState.putInt("d", put_d)
        outState.putInt("cy", put_cy)
        outState.putInt("cm", put_cm)
        outState.putInt("cd", put_cd)


    }

    private fun takePhoto(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000;
        private val REQUEST_IMAGE_CAPTURE = 3000;
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageView.setImageURI(data?.data)
            val selectedImage: Uri = data?.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = contentResolver.query(
                selectedImage,
                filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
            val picturePath: String = cursor.getString(columnIndex)
            file = File(picturePath)
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            File(applicationContext.getCacheDir(), "new.png").writeBitmap(
                imageBitmap,
                Bitmap.CompressFormat.PNG,
                85
            )
            file = File(applicationContext.getCacheDir(), "new.png")
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }

}
