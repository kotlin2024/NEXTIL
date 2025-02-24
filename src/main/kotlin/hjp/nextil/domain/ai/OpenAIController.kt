package hjp.nextil.domain.ai

import hjp.nextil.security.jwt.UserPrincipal
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/openai")
class OpenAIController(
    private val openAIService: OpenAIService,
) {

    @PostMapping("/extract")
    fun extract(@RequestBody text: UserText, @AuthenticationPrincipal user: UserPrincipal): ResponseEntity<List<String>> {
        return ResponseEntity.ok().body(openAIService.extractKeywordsFromText(text = text.tilText, user= user))
    }



}