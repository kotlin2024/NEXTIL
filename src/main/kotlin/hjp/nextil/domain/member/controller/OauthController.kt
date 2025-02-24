package hjp.nextil.domain.member.controller

import hjp.nextil.domain.member.entity.MemberEntity
import hjp.nextil.domain.member.repository.MemberRepository
import hjp.nextil.security.jwt.JwtTokenManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/api/auth")
class OAuth2LoginController(
    private val jwtTokenManager: JwtTokenManager,
    private val memberRepository: MemberRepository,
    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}") private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}") private val clientSecret: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}") private val redirectUri: String,
    @Value("\${spring.security.oauth2.client.provider.kakao.token-uri}") private val tokenUri: String,
    @Value("\${spring.security.oauth2.client.provider.kakao.user-info-uri}") private val userInfoUri: String
) {

    @GetMapping("/kakao/login")
    fun kakaoLogin(): String {
        val kakaoAuthUrl = UriComponentsBuilder.fromHttpUrl("https://kauth.kakao.com/oauth/authorize")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("response_type", "code")
            .toUriString()
        return kakaoAuthUrl
    }

    @GetMapping("/kakao/callback")
    fun kakaoCallback(@RequestParam("code") code: String): Map<String, String> {
        val restTemplate = RestTemplate()

        // 1️⃣ 카카오에 토큰 요청 (POST)
        val tokenHeaders = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val tokenBody = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", clientId)
            add("client_secret", clientSecret)
            add("redirect_uri", redirectUri)
            add("code", code)
        }

        val tokenRequest = HttpEntity(tokenBody, tokenHeaders)
        val tokenResponse = restTemplate.exchange(tokenUri, HttpMethod.POST, tokenRequest, Map::class.java).body
        val accessToken = tokenResponse?.get("access_token") as? String ?: throw RuntimeException("Access token not found")

        // 2️⃣ 카카오에 사용자 정보 요청 (GET)
        val userHeaders = HttpHeaders().apply {
            add("Authorization", "Bearer $accessToken")
            contentType = MediaType.APPLICATION_JSON
        }

        val userRequest = HttpEntity(null, userHeaders)
        val userResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, userRequest, Map::class.java).body

        val kakaoAccount = userResponse?.get("kakao_account") as Map<*, *>
        val profile = kakaoAccount["profile"] as Map<*, *>
        val nickname = profile["nickname"] as? String ?: "unknown"

        // 3️⃣ 사용자 저장 또는 조회
        val existingUser = memberRepository.findByUserName(nickname)
        var userId: Long? = existingUser?.id

        if (existingUser == null) {
            val newUser = memberRepository.save(
                MemberEntity(
                    userName = nickname,
                    password = UUID.randomUUID().toString(),
                )
            )
            userId = newUser.id
        }

        // 4️⃣ JWT 발급
        val token = jwtTokenManager.generateTokenResponse(memberId = userId!!)

        return mapOf("token" to token)
    }
}
