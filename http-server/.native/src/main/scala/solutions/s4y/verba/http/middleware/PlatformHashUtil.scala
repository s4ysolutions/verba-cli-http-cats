package solutions.s4y.verba.http.middleware

import java.util.Base64

private[middleware] object PlatformHashUtil {
  def apply(): HashUtil = (input: String) => {
    Base64.getEncoder.encodeToString(Sha256.digest(input.getBytes("UTF-8")))
  }
}
