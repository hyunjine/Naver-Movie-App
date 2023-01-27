package com.hyunjine.flow_task.presenter.search_movie.usecase

import com.hyunjine.flow_task.data.repository.DataRepository
import com.hyunjine.flow_task.presenter.search_movie.vo.MovieItemDTO
import com.hyunjine.flow_task.presenter.search_movie.vo.MoviesDTO
import io.reactivex.Single
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(query: String, display: Int): Single<MoviesDTO> =
        dataRepository.getMovies(query, display).map { entity ->
            val itemDto = entity.items.map {
                MovieItemDTO(it.image, it.link, it.pubDate, it.title, it.userRating)
            }
            MoviesDTO(entity.display, entity.start, entity.total, itemDto)
        }
}