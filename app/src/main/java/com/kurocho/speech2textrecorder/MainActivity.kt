package com.kurocho.speech2textrecorder

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder
import cafe.adriel.androidaudiorecorder.model.AudioChannel
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate
import cafe.adriel.androidaudiorecorder.model.AudioSource
import com.kurocho.speech2textrecorder.ratioFragment.Ratio
import com.kurocho.speech2textrecorder.ratioFragment.RatioFragment
import com.kurocho.speech2textrecorder.recordingFragment.RecordingFragment
import java.io.File


class MainActivity : FragmentActivity(), RatioFragment.OnListFragmentInteractionListener, RecordingFragment.OnFragmentInteractionListener {

    private val userId: String = "admin"
    private val transcriptionName: String = "test"
    private val REQUEST_RECORD_AUDIO = 0
    private val AUDIO_FILE_PATH =
        Environment.getExternalStorageDirectory().path + "/recorded_audio.wav"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Util.requestPermission(this, Manifest.permission.RECORD_AUDIO)
        Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD_AUDIO) {
            val apiConnector = ApiConnector(userId, this)
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Speech recorded successfully!", Toast.LENGTH_SHORT).show()
                displayTranscriptionResults(apiConnector.sendAudio(File(AUDIO_FILE_PATH),transcriptionName)!!)
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Speech was not recorded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayTranscriptionResults(ratios: List<Ratio>) {
        val nextFrag =
            RatioFragment.newInstance(ratios as ArrayList<Ratio>)
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, nextFrag, "ratioFragment")
            .addToBackStack(null)
            .commit()
    }


    fun recordAudio(v: View?) {
        AndroidAudioRecorder.with(this) // Required
            .setFilePath(AUDIO_FILE_PATH)
            .setColor(ContextCompat.getColor(this, R.color.recorder_bg))
            .setRequestCode(REQUEST_RECORD_AUDIO) // Optional
            .setSource(AudioSource.MIC)
            .setChannel(AudioChannel.STEREO)
            .setSampleRate(AudioSampleRate.HZ_48000)
            .setAutoStart(false)
            .setKeepDisplayOn(true) // Start recording
            .record()
    }

    override fun onListFragmentInteraction(item: Ratio?) {

    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
