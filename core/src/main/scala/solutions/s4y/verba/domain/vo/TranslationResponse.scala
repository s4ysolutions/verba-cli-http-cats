package solutions.s4y.verba.domain.vo

final case class TranslationResponse(
    translated: String,
    inputTokenCount: Int,
    outputTokenCount: Int
)
