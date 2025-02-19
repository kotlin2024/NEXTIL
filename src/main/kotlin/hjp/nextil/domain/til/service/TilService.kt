package hjp.nextil.domain.til.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service


@Service
class TilService {


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
        return document.select("body").text() // 기본적으로 본문 전체 텍스트 가져오기
    }

}