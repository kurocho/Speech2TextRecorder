package com.kurocho.speech2textrecorder


import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface SpeechTextService {
    companion object {
        fun getURL(): String = "192.168.1.15" //IP KOMPA Z API
    }


    @POST("/transcription")
    @Multipart
    fun sendAudio(@Header("userId") userId: String?,
                  @Header("transcriptionName") name: String?,
                  @Part audio: MultipartBody.Part
    ): Call<ResponseBody>


}