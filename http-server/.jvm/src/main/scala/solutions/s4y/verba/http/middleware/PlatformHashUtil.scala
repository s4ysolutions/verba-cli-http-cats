package solutions.s4y.verba.http.middleware

import java.security.MessageDigest
import java.util.Base64

private[middleware] object PlatformHashUtil {
  def apply(): HashUtil = (input: String) => {
    Base64.getEncoder.encodeToString(
      MessageDigest
        .getInstance("SHA-256")
        .digest(input.getBytes("UTF-8"))
    )
  }
}
