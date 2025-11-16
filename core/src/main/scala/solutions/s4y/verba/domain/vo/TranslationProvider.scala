package solutions.s4y.verba.domain.vo

import cats.Order
import cats.data.NonEmptySet

case class TranslationProvider(
    name: String,
    qualities: NonEmptySet[TranslationQuality]
)

object TranslationProvider:
  given Order[TranslationProvider] = Order.by(_.name)
