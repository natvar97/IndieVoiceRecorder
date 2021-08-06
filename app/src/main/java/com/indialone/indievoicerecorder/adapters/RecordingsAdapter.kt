package com.indialone.indievoicerecorder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indialone.indievoicerecorder.OnSelectListener
import com.indialone.indievoicerecorder.databinding.ItemRecordLayoutBinding
import java.io.File

class RecordingsAdapter(
    private val context: Context,
    private val fileList: ArrayList<File>,
    private val listener: OnSelectListener
) : RecyclerView.Adapter<RecordingsAdapter.RecordingsViewHolder>() {
    class RecordingsViewHolder(itemView: ItemRecordLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val fileName = itemView.tvName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordingsViewHolder {
        val view =
            ItemRecordLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordingsViewHolder, position: Int) {
        holder.fileName.setText(fileList[position].name)
        holder.fileName.isSelected = true
        holder.itemView.setOnClickListener {
            listener.onSelected(fileList[position])
        }
    }

    override fun getItemCount(): Int {
        return fileList.size
    }
}