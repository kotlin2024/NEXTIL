package hjp.nextil.domain.member.service

import org.springframework.stereotype.Service

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

@Service
class OAuth2Service : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        // 카카오 응답에서 닉네임만 추출
        val attributes = oAuth2User.attributes
        val kakaoAccount = attributes["kakao_account"] as Map<*, *>
        val profile = kakaoAccount["profile"] as Map<*, *>

        val nickname = profile["nickname"] as? String ?: "Unknown"

        println("카카오 로그인 성공: 닉네임 - $nickname")

        return oAuth2User
    }
}