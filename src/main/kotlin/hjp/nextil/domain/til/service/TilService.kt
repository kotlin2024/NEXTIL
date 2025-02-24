package hjp.nextil.domain.til.service

import hjp.nextil.domain.ai.OpenAIService
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service


@Service
class TilService(
    private val openAIService: OpenAIService,
) {

    fun getBodyText(url: String): String{

        return extractMainContent(fetchHtmlFromUrl(url))

    }

    fun fetchHtmlFromUrl(url: String): Document {

        val cleanUrl = url.replace("\"", "").replace("'", "").trim()
        return Jsoup.connect(cleanUrl)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36") // 크롤링 방지 우회
            .timeout(5000) // 5초 타임아웃 설정
            .get()
    }


    fun extractMainContent(document: Document): String {
        val content = document.select("div.contents_style").text()

        // 제목 추출
        val title = document.select(("div.info_text strong.title_post")).text()

        // 본문과 제목을 함께 반환 (필요에 따라 조합 방식 조정 가능)
        return "Title: $title\nContent: $content"
    }

    fun extractAllContent(document: Document): String {
        return document.select("body").text() // 기본적으로 본문 전체 텍스트 가져오기
    }

}