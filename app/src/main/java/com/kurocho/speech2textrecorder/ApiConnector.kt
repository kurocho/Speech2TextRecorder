package com.kurocho.speech2textrecorder

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.util.logging.Logger


class ApiConnector(val userId: String?, val activityContext: Activity) {

    fun speechTextService() : SpeechTextService{
        val retrofit = Retrofit.Builder()
            .baseUrl(SpeechTextService.getURL())
            .build()
        return retrofit.create<SpeechTextService>(SpeechTextService::class.java)
    }

    fun sendAudio(fileToUpload : File, transcriptionName : String){
        val service = speechTextService()
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("audio"), fileToUpload)
        val body =
            MultipartBody.Part.createFormData("file", fileToUpload.name, requestFile)
        service.sendAudio(userId,transcriptionName,body).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activityContext, "Failed to send speech to server", Toast.LENGTH_LONG).show()
                println(t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(activityContext, "Successfully sent speech", Toast.LENGTH_LONG).show()            }
        })
    }
}