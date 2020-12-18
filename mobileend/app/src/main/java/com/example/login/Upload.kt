package com.example.login

import android.os.AsyncTask
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.S3ClientOptions
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import java.io.File

class Upload(f : File) : AsyncTask<String, String, String>() {
    var ff : File? = null

    init {
        ff = f
    }

    override fun doInBackground(vararg params: String?): String {
        val filepath = params[0]
        val accessKey = params[1]
        val secretKey = params[2]
        val s3Client = AmazonS3Client(BasicAWSCredentials(accessKey, secretKey)).apply {
            setEndpoint(endpoint).apply {
                println("S3 endpoint is ${endpoint}")
            }
            setS3ClientOptions(
                S3ClientOptions.builder()
                    .setPathStyleAccess(true).build()
            )
        }
        s3Client.setRegion(Region.getRegion(Regions.US_EAST_2))

        val por = PutObjectRequest("timecapsuleforandroid", filepath, ff)
        s3Client.putObject(por)
        s3Client.setObjectAcl("timecapsuleforandroid", filepath, CannedAccessControlList.PublicRead)

        return "";
    }

}