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
            existingTilEntity.tilKeyword.toMutableList()
        }


        val updatedKeywords = (myKeywords + newKeywords).toSet().toList()

        tilRepository.save(
            TilEntity(
                memberId = user.memberId,
                tilKeyword = updatedKeywords,
            )
        )

        return parseJsonKeywords(cleanedJson)
    }

    private fun parseJsonKeywords(json: String): List<String> {

        return Json.decodeFromString<List<String>>(json)
    }
}
