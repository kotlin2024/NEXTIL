package hjp.nextil.domain.member.controller

import hjp.nextil.domain.member.dto.SignUpDto
import hjp.nextil.domain.member.dto.TokenResponseDto
import hjp.nextil.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/oauth")
class MemberController(
    private val memberService: MemberService,
){

    @PostMapping("/sign-up")
    fun signUp(@RequestBody  signUpDto: SignUpDto): ResponseEntity<Unit>{
        return ResponseEntity.ok().body(memberService.signUp(signUpDto))
    }

    @PostMapping("/login")
    fun login(@RequestBody signUpDto: SignUpDto): ResponseEntity<TokenResponseDto>{
        return ResponseEntity.ok().body(memberService.login(signUpDto))
    }
}