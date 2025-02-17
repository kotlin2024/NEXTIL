package hjp.nextil.domain.member.controller

import hjp.nextil.domain.member.service.MemberService
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService,
){
}