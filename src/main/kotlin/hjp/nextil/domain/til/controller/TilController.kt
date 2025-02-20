package hjp.nextil.domain.til.controller

import hjp.nextil.domain.ai.OpenAIService
import hjp.nextil.domain.til.service.TilService
import hjp.nextil.security.jwt.UserPrincipal
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/til")
class TilController(
    private val tilService: TilService,
    private val openAIService: OpenAIService,
) {

    @PostMapping("/openai")
    fun extractKeyWordFromOpenAi(
        @RequestBody url: String,
        @AuthenticationPrincipal user: UserPrincipal
        ): ResponseEntity<List<String>>
    {
        val tilFullText: String = tilService.getBodyText(url = url)
        return ResponseEntity.ok().body(openAIService.extractKeywordsFromText(text = tilFullText, user = user))
    }

    @PostMapping("/imsi")
    fun imsi(@RequestBody url: String): String{

        return tilService.getBodyText(url = url)
    }

    @GetMapping("/imsi_chek_my_Id")
    fun imsiWhoAmI(
        @AuthenticationPrincipal user: UserPrincipal,
    ): String{
        return "내 memberid = ${user.memberId}"
    }






}