package hjp.nextil.security.jwt


import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter



@Component
class JwtAuthenticationFilter(
    private val jwtTokenManager: JwtTokenManager,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)
        if (token != null) {
            jwtTokenManager.validateToken(token).onSuccess { claimsJws ->
                val claims: Claims = claimsJws.body
                val memberId = claims.subject.toLong()

                val userPrincipal = UserPrincipal(memberId = memberId, memberRole = setOf("ROLE_USER"))
                val authentication = JwtAuthenticationToken(
                    userPrincipal = userPrincipal, details = WebAuthenticationDetailsSource().buildDetails(request)
                )

                SecurityContextHolder.getContext().authentication = authentication

            }
        }
        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}
