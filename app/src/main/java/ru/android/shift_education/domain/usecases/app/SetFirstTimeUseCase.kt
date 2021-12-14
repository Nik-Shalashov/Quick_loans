package ru.android.shift_education.domain.usecases.app

import ru.android.shift_education.domain.repository.AppRepository
import javax.inject.Inject

class SetFirstTimeUseCase @Inject constructor(
    private val userInfoRepository: AppRepository
) {

    operator fun invoke() {
        userInfoRepository.setFirstTime()
    }

}