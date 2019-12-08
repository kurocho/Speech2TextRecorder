package com.kurocho.speech2textrecorder


import com.kurocho.speech2textrecorder.ratioFragment.Ratio
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface SpeechTextService {
    companion object {
        fun getURL(): String = "http://192.168.1.15" //IP KOMPA Z API
    }

    @POST("/transcription")
    @Multipart
    fun sendAudio(@Header("userId") userId: String?,
                  @Header("transcriptionName") name: String?,
                  @Part audio: MultipartBody.Part
    ): Call<List<Ratio>>

}