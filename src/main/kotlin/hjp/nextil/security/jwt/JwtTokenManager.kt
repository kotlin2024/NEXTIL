package hjp.nextil.security.jwt

import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtTokenManager(
    @Value("\${auth.jwt.issuer}") private var issuer: String,
    @Value("\${auth.jwt.secret}") private var secret: String,
) {
    private val accessTokenValidity = 60 * 1000
    private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    private fun generateToken(subject: String, expirationTime: Int): String {
        val claims: Claims =
            Jwts.claims().build()

        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .claims(claims)
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        }
    }
}