package hjp.nextil.domain.ai

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/openai")
class OpenAIController(
    private val openAIService: OpenAIService,
) {

    @GetMapping("/extract")
    suspend fun extract(@RequestParam text: String): List<String> {
        return openAIService.extractKeywordsFromText(text = text)
    }
}