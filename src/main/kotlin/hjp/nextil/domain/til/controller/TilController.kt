package hjp.nextil.domain.til.controller

import hjp.nextil.domain.til.service.TilService
import hjp.nextil.security.jwt.UserPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/til")
class TilController(
    private val tilService: TilService,
) {

    @PostMapping("/imsi")
    fun imsi(@RequestBody url: String): String{

        return tilService.getBodyText(url = url)
    }

    @GetMapping("/imsiimsiimsi")
    fun imsiWhoAmI(
        @AuthenticationPrincipal user: UserPrincipal,
    ): String{
        return "내 memberid = ${user.memberId}"
    }






}