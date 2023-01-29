package com.hyunjine.flow_task.presenter.search_movie.usecase

import android.content.Context
import android.content.res.Resources.NotFoundException
import com.hyunjine.flow_task.R
import com.hyunjine.flow_task.data.repository.DataRepository
import com.hyunjine.flow_task.presenter.search_movie.vo.MovieItemDTO
import com.hyunjine.flow_task.presenter.search_movie.vo.MoviesDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(query: String, display: Int, start: Int): Single<MoviesDTO> =
        dataRepository.getMovies(query, display, start).map { entity ->
            if (!isDataExist(entity.display)) throw NotFoundException("No longer data exist")
            val itemDto = entity.items.map {
                val title = context.getString(
                    R.string.search_movie_title_format,
                    it.title
                        .replace("<b>", "")
                        .replace("</b>", "")
                )
                val pubDate = context.getString(R.string.search_movie_pub_date_format, it.pubDate)
                val userRating = context.getString(R.string.search_movie_user_rating_format, it.userRating)
                it.link
                MovieItemDTO(it.image, it.link, pubDate, title, userRating)
            }
            MoviesDTO(entity.display, entity.start, entity.total, itemDto)
        }

    private fun isDataExist(display: Int): Boolean = display > 0
}