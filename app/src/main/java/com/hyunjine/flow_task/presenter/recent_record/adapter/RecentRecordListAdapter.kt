package com.hyunjine.flow_task.presenter.recent_record.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyunjine.flow_task.databinding.ItemRecentRecordBinding
import com.hyunjine.flow_task.presenter.recent_record.vo.SearchRecordDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RecentRecordListAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    ListAdapter<SearchRecordDTO, RecentRecordListAdapter.RecentRecordViewHolder>(RecentRecordDiff()) {
    private lateinit var listener: (SearchRecordDTO, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentRecordViewHolder =
        RecentRecordViewHolder(ItemRecentRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecentRecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: (SearchRecordDTO, Int) -> Unit) {
        this.listener = listener
    }

    inner class RecentRecordViewHolder(private val binding: ItemRecentRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                if (::listener.isInitialized) {
                    val currentPosition = adapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        listener(getItem(currentPosition), currentPosition)
                    }
                }
            }
        }
        fun bind(data: SearchRecordDTO) {
            binding.run {
                tvWord.text = data.word
            }
        }
    }

    class RecentRecordDiff : DiffUtil.ItemCallback<SearchRecordDTO>() {
        override fun areItemsTheSame(oldItem: SearchRecordDTO, newItem: SearchRecordDTO): Boolean =
            oldItem.generateTimestamp == newItem.generateTimestamp

        override fun areContentsTheSame(oldItem: SearchRecordDTO, newItem: SearchRecordDTO): Boolean =
            oldItem == newItem
    }
}