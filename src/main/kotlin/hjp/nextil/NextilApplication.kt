package hjp.nextil

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NextilApplication

fun main(args: Array<String>) {
    runApplication<NextilApplication>(*args)
}
//fun main() = runBlocking {
//    val openAIService = OpenAIService(
//        apiKey = "sk-xxxxxxxxxxxxxxxxxxxx",
//        model = "gpt-4-turbo"
//    )
//
//    val keywords = openAIService.extractKeywords("오늘 나는 Kotlin과 Spring Boot를 사용하여 OpenAI API 연동을 했다.")
//    println("추출된 키워드: $keywords")
//}