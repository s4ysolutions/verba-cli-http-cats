package solutions.s4y.verba.http.middleware

trait HashUtil {
  def sha256Base64(input: String): String
}

object HashUtil {
  def apply(): HashUtil = PlatformHashUtil()
}
