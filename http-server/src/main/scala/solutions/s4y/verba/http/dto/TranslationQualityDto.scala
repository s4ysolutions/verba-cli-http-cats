package solutions.s4y.verba.http.dto

import solutions.s4y.verba.domain.vo.TranslationQuality

case class TranslationQualityDto(value: String)

object TranslationQualityDto:
  def fromDomain(quality: TranslationQuality): TranslationQualityDto =
    TranslationQualityDto(quality.toString)
