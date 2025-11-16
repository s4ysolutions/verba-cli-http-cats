package solutions.s4y.verba.http.endpoints.translation

import cats.data.NonEmptyVector
import solutions.s4y.verba.domain.vo.TranslationProvider
import solutions.s4y.verba.http.dto.TranslationProviderDto

case class TranslationStateDto(providers: List[TranslationProviderDto])
