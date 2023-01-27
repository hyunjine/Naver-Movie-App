package com.hyunjine.flow_task.presenter.search_movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hyunjine.domain.model.MovieInfo
import com.hyunjine.flow_task.R
import com.hyunjine.flow_task.databinding.SearchMovieItemBinding
import com.hyunjine.grip_android_yanghyunjin.R
import com.hyunjine.grip_android_yanghyunjin.databinding.ItemMainSearchBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchMovieListAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    ListAdapter<MovieInfo, SearchMovieListAdapter.SearchMovieViewHolder>(MainDiaryListDiff()) {
    private lateinit var listener: (MovieInfo, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder =
        SearchMovieViewHolder(SearchMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: (MovieInfo, Int) -> Unit) {
        this.listener = listener
    }

    inner class SearchMovieViewHolder(private val binding: SearchMovieItemBinding) :
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
        fun bind(movie: MovieInfo) {
            binding.run {
                tvMovieTitle.text = movie.title
                tvMovieYear.text = movie.year
                tvMovieType.text = movie.type
                Glide.with(context).load(movie.poster)
                    .transform(FitCenter(), RoundedCorners(50))
                    .placeholder(R.drawable.ic_movie)
                    .error(R.drawable.ic_movie)
                    .fallback(R.drawable.ic_movie)
                    .into(imgMoviePoster)
            }
        }
    }

    class MainDiaryListDiff : DiffUtil.ItemCallback<MovieInfo>() {
        override fun areItemsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean =
            oldItem.imdbID == newItem.imdbID

        override fun areContentsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean =
            oldItem == newItem
    }
}