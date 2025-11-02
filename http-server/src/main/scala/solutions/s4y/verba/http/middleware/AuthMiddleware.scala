package solutions.s4y.verba.http.middleware

import cats.data.{Kleisli, OptionT}
import cats.effect.IO
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.headers.Authorization
import scribe.cats.LoggerExtras

object AuthMiddleware {
  private val logger = scribe.Logger("verba.http.auth").f[IO]

  def apply(secret: String)(routes: HttpRoutes[IO]): HttpRoutes[IO] = Kleisli { (req: Request[IO])  =>
      val authHeader = req.headers.get[Authorization].map(_.credentials.renderString)

      authHeader match {
        case Some(headerValue) if headerValue == s"Bearer $secret" =>
          routes(req)
        case Some(_) =>
          OptionT.liftF(
            logger.warn(s"Unauthorized request to ${req.method} ${req.uri.path}") *>
              Forbidden("Invalid authentication token")
          )
        case None =>
          OptionT.liftF(
            logger.warn(s"Missing authentication header for ${req.method} ${req.uri.path}") *>
              Forbidden("Missing authentication header")
          )
      }
    }
}

