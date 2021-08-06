package com.indialone.indievoicerecorder.fragments

import android.Manifest
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.indialone.indievoicerecorder.R
import com.indialone.indievoicerecorder.databinding.FragmentRecorderBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class RecorderFragment : Fragment() {

    private lateinit var mBinding: FragmentRecorderBinding
    private var fileName: String = ""
    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording: Boolean = false
    private var path: File =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath + "/IndieVoiceRecorder")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRecorderBinding.inflate(inflater, container, false)
        askRuntimePermission()

        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val date = dateFormat.format(Date())

        fileName = "$path/recording_$date.amr"
        Log.e("filename", fileName)
        if (!path.exists()) {
            path.mkdirs()
        }

        mBinding.btnRec.setOnClickListener {
            if (!isRecording) {
                try {
                    startRecording()
                    mBinding.gifView.visibility = View.VISIBLE
                    mBinding.timeRec.base = SystemClock.elapsedRealtime()
                    mBinding.timeRec.start()
                    mBinding.tvRecordingStatus.setText("Recording...")
                    mBinding.btnRec.setImageResource(R.drawable.ic_stop)
                    isRecording = true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Could not record!", Toast.LENGTH_SHORT).show()
                }
            } else {
                try {
                    stopRecording()
                    mBinding.gifView.visibility = View.GONE
                    mBinding.timeRec.base = SystemClock.elapsedRealtime()
                    mBinding.timeRec.stop()
                    mBinding.tvRecordingStatus.setText("")
                    mBinding.btnRec.setImageResource(R.drawable.ic_record)
                    isRecording = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }


        return mBinding.root
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder.apply {
            this.setAudioSource(MediaRecorder.AudioSource.MIC)
            this.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
            this.setOutputFile(fileName)
            this.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        }
        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.release()
    }


    private fun askRuntimePermission() {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    request: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).check()
    }

}