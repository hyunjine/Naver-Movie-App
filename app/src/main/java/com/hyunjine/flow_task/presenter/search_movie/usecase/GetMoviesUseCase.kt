package com.hyunjine.flow_task.presenter.search_movie.usecase

import android.content.Context
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
    operator fun invoke(query: String, display: Int): Single<MoviesDTO> =
        dataRepository.getMovies(query, display).map { entity ->
            val itemDto = entity.items.map {
                val title = it.title
                    .replace("<b>", "")
                    .replace("</b>", "")
                MovieItemDTO(it.image, it.link, it.pubDate, title, it.userRating)
            }
            MoviesDTO(entity.display, entity.start, entity.total, itemDto)
        }
}