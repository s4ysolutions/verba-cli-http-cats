package solutions.s4y.verba.http.endpoints.translation

case class TranslationResponseDto(
    translated: String,
    inputTokenCount: Int,
    outputTokenCount: Int,
    time: Long,
    state: TranslationStateDto
)
