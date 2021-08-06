package com.indialone.indievoicerecorder.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.indialone.indievoicerecorder.OnSelectListener
import com.indialone.indievoicerecorder.adapters.RecordingsAdapter
import com.indialone.indievoicerecorder.databinding.FragmentRecordingsBinding
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class RecordingsFragment : Fragment(), OnSelectListener {

    private lateinit var mBinding: FragmentRecordingsBinding
    private lateinit var fileList: ArrayList<File>
    private lateinit var mRecordingsAdapter: RecordingsAdapter
    private var path: File =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath + "/IndieVoiceRecorder")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRecordingsBinding.inflate(inflater, container, false)
        displayRecordings()
        return mBinding.root
    }

    private fun findRecordings(file: File): ArrayList<File> {
        val files = file.listFiles()
        val list = ArrayList<File>()
        if (files != null) {
            for (singleFile in files) {
                if (singleFile.name.lowercase(Locale.getDefault()).endsWith(".amr")) {
                    list.add(file)
                }
            }
        }
        return list
    }

    private fun displayRecordings() {
        mBinding.rvRecordings.layoutManager = LinearLayoutManager(context)
        fileList = ArrayList()
        fileList.addAll(findRecordings(path))
        mRecordingsAdapter = RecordingsAdapter(requireContext(), fileList, this)
        mBinding.rvRecordings.adapter = mRecordingsAdapter
        mBinding.rvRecordings.setHasFixedSize(true)

    }

    override fun onSelected(file: File) {
        val uri: Uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            file
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "audio/x-wav")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        requireContext().startActivity(intent)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            displayRecordings()
        }
    }


}