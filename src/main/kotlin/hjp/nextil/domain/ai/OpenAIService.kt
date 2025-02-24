package hjp.nextil.domain.ai

import hjp.nextil.domain.til.entity.TilEntity
import hjp.nextil.domain.til.repository.TilRepository
import hjp.nextil.security.jwt.UserPrincipal
import jakarta.transaction.Transactional
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Service
import kotlinx.serialization.json.Json


@Service
class OpenAIService(
    private val chatClient: ChatClient,
    private val tilRepository: TilRepository,
) {
    @Transactional
    fun extractKeywordsFromText(text: String, user: UserPrincipal): List<String> {

        val systemMessage = SystemMessage("너는 키워드 추출 전문가야. 다음 텍스트의 핵심 개발 키워드를 1~3개 추출해서 JSON 배열로 반환해줘.")
        val userMessage = UserMessage("본문:\n$text")

        // OpenAI API 호출
        val response = chatClient.prompt(Prompt(listOf(systemMessage, userMessage))).call()

        val cleanedJson = response.content()?.removeSurrounding("```json", "```")?.trim() ?: ""

        val newKeywords = parseJsonKeywords(cleanedJson)
        val existingTilEntity = tilRepository.findByMemberId(user.memberId)

        val myKeywords = if (existingTilEntity == null) {
            val newTilEntity = TilEntity(
                memberId = user.memberId,
                tilKeyword = newKeywords
            )
            tilRepository.save(newTilEntity)
            newKeywords.toMutableList()
        } else {
            existingTilEntity.flatMap { it.tilKeyword }.toMutableList()
        }
        tilRepository.deleteAllByMemberId(user.memberId)


        val updatedKeywords = (myKeywords + newKeywords).toSet().toList()

        tilRepository.save(
            TilEntity(
                memberId = user.memberId,
                tilKeyword = updatedKeywords,
            )
        )

        return parseJsonKeywords(cleanedJson)
    }


    @Transactional
    fun recommendTilSubjects(user: UserPrincipal): String {

        val member = tilRepository.findByMemberId(user.memberId) ?: throw IllegalArgumentException("해당 유저가 아직 til을 작성하지 않음")

        val tilList = member.flatMap { it.tilKeyword }.joinToString(separator = "\n")

        val systemMessage = SystemMessage("너는 TIL 주제 추천 전문가야. 내가 작성한 TIL 주제 목록을 알려줄테니 내가 쓸 TIL 주제를 1개에서 3개사이로 너가 추천해줘. 내가 이미 작성한 til 주제는 추천해서는 안돼. 참고로 나는 백엔드 개발자야 그리고 추천된 주제는 반드시 JSON 형식의 배열로 반환해줘. 예: [\"주제1\", \"주제2\", \"주제3\"] ")
        val userMessage = UserMessage("내가 작성한 TIL 목록:\n$tilList")

        val response = chatClient.prompt(Prompt(listOf(systemMessage, userMessage))).call()

        val responseContent = response.content() ?: ""
        val jsonPattern = "\\[.*?\\]".toRegex()  // JSON 배열을 찾아내는 정규 표현식
        val jsonMatch = jsonPattern.find(responseContent)

        val cleanedJson = jsonMatch?.value?.trim() ?: ""

        val recommendedSubjects = parseJsonKeywords(cleanedJson)


        return "오늘의 추천 TIL:  $recommendedSubjects"
    }

    private fun parseJsonKeywords(json: String): List<String> {

        return Json.decodeFromString<List<String>>(json)
    }
}
