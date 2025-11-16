package solutions.s4y.verba.domain.vo

import cats.data.{NonEmptySet, ValidatedNec}
import cats.syntax.all.*
import solutions.s4y.verba.domain.errors.RequestValidationError

enum TranslationProviders(val provider: TranslationProvider):
  case OpenAI
      extends TranslationProviders(
        TranslationProvider(
          "OpenAI",
          NonEmptySet.of(TranslationQuality.Fast, TranslationQuality.Optimal)
        )
      )
  case Gemini
      extends TranslationProviders(
        TranslationProvider(
          "Gemini",
          NonEmptySet.of(
            TranslationQuality.Fast,
            TranslationQuality.Optimal,
            TranslationQuality.Thinking
          )
        )
      )

object TranslationProviders:
  def fromString(
      raw: String
  ): ValidatedNec[RequestValidationError, TranslationProviders] =
    val normalized = Option(raw).getOrElse("").trim.toLowerCase
    normalized match
      case "openai"            => OpenAI.validNec
      case "google" | "gemini" => Gemini.validNec
      case _ => RequestValidationError.InvalidProvider(raw).invalidNec
