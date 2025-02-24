package hjp.nextil.domain.ai


import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value

@Configuration
class ChatConfig {

    @Bean
    fun chatClient(
        @Value("\${spring.ai.openai.api-key}") apiKey: String
    ): ChatClient {
        val openAiApi = OpenAiApi(apiKey)

        // ✅ 빌더 패턴을 사용하여 모델을 지정
        val chatOptions = OpenAiChatOptions.builder()
            .model("gpt-4o-mini-2024-07-18")  // ✅ 모델 지정
            .temperature(0.7) // (선택) 모델의 응답 다양성 조절 가능
            .maxTokens(200)  // (선택) 최대 토큰 수 설정 가능
            .build()

        val chatModel = OpenAiChatModel(openAiApi, chatOptions)
        return ChatClient.create(chatModel)
    }
}

