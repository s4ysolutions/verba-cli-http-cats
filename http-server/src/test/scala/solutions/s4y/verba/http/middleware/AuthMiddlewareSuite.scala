package solutions.s4y.verba.http.middleware

import cats.effect.IO
import munit.CatsEffectSuite
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.headers.Authorization
import org.http4s.implicits.*

class AuthMiddlewareSuite extends CatsEffectSuite {

  private val testSecret = "my-secret-token"

  private val testRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "test" => Ok("Success")
  }

  private val protectedRoutes = AuthMiddleware(testSecret)(testRoutes)

  test("allows request with valid Bearer token") {
    val request = Request[IO](
      method = Method.GET,
      uri = uri"/test"
    ).putHeaders(Authorization(Credentials.Token(AuthScheme.Bearer, testSecret)))

    protectedRoutes.orNotFound.run(request).flatMap { response =>
      IO {
        assertEquals(response.status, Status.Ok)
      }
    }
  }

  test("rejects request with invalid Bearer token") {
    val request = Request[IO](
      method = Method.GET,
      uri = uri"/test"
    ).putHeaders(Authorization(Credentials.Token(AuthScheme.Bearer, "wrong-token")))

    protectedRoutes.orNotFound.run(request).flatMap { response =>
      IO {
        assertEquals(response.status, Status.Forbidden)
      }
    }
  }

  test("rejects request without Authorization header") {
    val request = Request[IO](
      method = Method.GET,
      uri = uri"/test"
    )

    protectedRoutes.orNotFound.run(request).flatMap { response =>
      IO {
        assertEquals(response.status, Status.Forbidden)
      }
    }
  }

  test("rejects request with non-Bearer authorization") {
    val request = Request[IO](
      method = Method.GET,
      uri = uri"/test"
    ).putHeaders(Authorization(Credentials.Token(AuthScheme.Basic, testSecret)))

    protectedRoutes.orNotFound.run(request).flatMap { response =>
      IO {
        assertEquals(response.status, Status.Forbidden)
      }
    }
  }

  test("returns forbidden body with message for invalid token") {
    val request = Request[IO](
      method = Method.GET,
      uri = uri"/test"
    ).putHeaders(Authorization(Credentials.Token(AuthScheme.Bearer, "wrong-token")))

    protectedRoutes.orNotFound.run(request).flatMap { response =>
      response.bodyText.compile.string.flatMap { body =>
        IO {
          assertEquals(response.status, Status.Forbidden)
          assert(body.contains("Invalid authentication token"))
        }
      }
    }
  }

  test("returns forbidden body with message for missing header") {
    val request = Request[IO](
      method = Method.GET,
      uri = uri"/test"
    )

    protectedRoutes.orNotFound.run(request).flatMap { response =>
      response.bodyText.compile.string.flatMap { body =>
        IO {
          assertEquals(response.status, Status.Forbidden)
          assert(body.contains("Missing authentication header"))
        }
      }
    }
  }
}

