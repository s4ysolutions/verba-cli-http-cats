package solutions.s4y.verba.http.dto

import cats.syntax.all.*
import solutions.s4y.verba.domain.vo.TranslationProvider

case class TranslationProviderDto(
    name: String,
    qualities: List[TranslationQualityDto]
)

object TranslationProviderDto:
  def fromDomain(
      provider: TranslationProvider
  ): TranslationProviderDto =
    TranslationProviderDto(
      name = provider.name,
      qualities =
        provider.qualities.toList.map(TranslationQualityDto.fromDomain)
    )
