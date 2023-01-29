package com.hyunjine.flow_task.presenter.search_movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.hyunjine.flow_task.R
import com.hyunjine.flow_task.common.loggerD
import com.hyunjine.flow_task.databinding.ItemSearchMovieBinding
import com.hyunjine.flow_task.presenter.search_movie.vo.MovieItemDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchMovieListAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    ListAdapter<MovieItemDTO, SearchMovieListAdapter.SearchMovieViewHolder>(SearchMovieDiff()) {
    private lateinit var listener: (MovieItemDTO, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder =
        SearchMovieViewHolder(ItemSearchMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: (MovieItemDTO, Int) -> Unit) {
        this.listener = listener
    }

    inner class SearchMovieViewHolder(private val binding: ItemSearchMovieBinding) :
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
        fun bind(movie: MovieItemDTO) {
            binding.run {
                tvMovieTitle.text = movie.title
                tvMovieYear.text = movie.pubDate
                tvMovieUserRating.text = movie.userRating
                Glide.with(context).load(movie.image)
                    .transform(FitCenter())
                    .placeholder(R.drawable.ic_movie)
                    .error(R.drawable.ic_movie)
                    .fallback(R.drawable.ic_movie)
                    .into(imgMoviePoster)
            }
        }
    }

    class SearchMovieDiff : DiffUtil.ItemCallback<MovieItemDTO>() {
        override fun areItemsTheSame(oldItem: MovieItemDTO, newItem: MovieItemDTO): Boolean =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: MovieItemDTO, newItem: MovieItemDTO): Boolean =
            oldItem == newItem
    }
}