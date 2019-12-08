package com.kurocho.speech2textrecorder

import android.app.Activity
import android.widget.Toast
import com.kurocho.speech2textrecorder.ratioFragment.Ratio
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class ApiConnector(val userId: String?, val activityContext: Activity) {

    fun speechTextService() : SpeechTextService{
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(SpeechTextService.getURL())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create<SpeechTextService>(SpeechTextService::class.java)
    }

    fun sendAudio(fileToUpload : File, transcriptionName : String): List<Ratio>? {
        var ratios : List<Ratio>? = ArrayList()
        val service = speechTextService()
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("audio"), fileToUpload)
        val body =
            MultipartBody.Part.createFormData("file", fileToUpload.name, requestFile)
        service.sendAudio(userId,transcriptionName,body).enqueue(object : Callback<List<Ratio>>{
            override fun onFailure(call: Call<List<Ratio>>, t: Throwable) {
                Toast.makeText(activityContext, "Failed to send speech to server", Toast.LENGTH_LONG).show()
                println("\n---------------ERROR---------------")
                println(t.message+"\n")
            }

            override fun onResponse(call: Call<List<Ratio>>, response: Response<List<Ratio>>) {
                Toast.makeText(activityContext, "Successfully sent speech", Toast.LENGTH_LONG).show()
                if(response.isSuccessful && response.body() != null){
                   ratios  = response.body()
;                }
            }
        })
        return ratios
    }
}