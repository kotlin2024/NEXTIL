package hjp.nextil.domain.member.service

import hjp.nextil.domain.member.dto.SignUpDto
import hjp.nextil.domain.member.dto.TokenResponseDto
import hjp.nextil.domain.member.entity.MemberEntity
import hjp.nextil.domain.member.repository.MemberRepository
import hjp.nextil.security.jwt.JwtTokenManager
import org.springframework.stereotype.Service


@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtTokenManager: JwtTokenManager,
) {


    fun signUp(signUpDto: SignUpDto) {

        val check = memberRepository.existsByUserName(signUpDto.userName)
        if(!check){
            memberRepository.save(
                MemberEntity(
                    userName = signUpDto.userName,
                    password = signUpDto.password,
                )
            )
        }
        else{
            throw IllegalArgumentException("userName already exists")
        }

    }

    fun login(signUpDto: SignUpDto): TokenResponseDto {

        val user = memberRepository.findByUserNameAndPassword(signUpDto.userName, signUpDto.password)
            ?: throw IllegalArgumentException("wrong id or password!")

        return TokenResponseDto(accessToken = jwtTokenManager.generateTokenResponse(memberId = user.id!!))
    }
}