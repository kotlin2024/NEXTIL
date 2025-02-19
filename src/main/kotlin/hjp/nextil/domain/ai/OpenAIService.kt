package hjp.nextil.domain.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Service
import kotlinx.serialization.json.Json

@Service
class OpenAIService(private val chatClient: ChatClient) {

    // ✅ HTML 본문에서 키워드 추출
    fun extractKeywordsFromText(text: String): List<String> {
        val systemMessage = SystemMessage("너는 키워드 추출 전문가야. 다음 텍스트의 핵심 키워드를 1~3개 추출해서 JSON 배열로 반환해줘.")
        val userMessage = UserMessage("본문:\n$text")

        // OpenAI API 호출
        val response = chatClient.prompt(Prompt(listOf(systemMessage, userMessage))).call()

        // 결과를 리스트로 변환
        return parseJsonKeywords(response.content()?: "")
    }

    // ✅ JSON 응답을 리스트로 변환
    private fun parseJsonKeywords(json: String): List<String> {
        return try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
