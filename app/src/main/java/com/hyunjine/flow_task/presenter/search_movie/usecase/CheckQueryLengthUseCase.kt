package com.hyunjine.flow_task.presenter.search_movie.usecase

import javax.inject.Inject

class CheckQueryLengthUseCase @Inject constructor() {
    operator fun invoke(query: String?, minLength: Int): Boolean =
        if (query.isNullOrBlank()) false
        else query.length >= minLength
}