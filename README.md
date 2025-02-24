# NEXTIL (Open AI를 이용한 TIL 추천서비스)
<img src="https://raw.githubusercontent.com/kotlin2024/NEXTIL/master/DALL%C2%B7E%202025-02-17%2015.02.37%20-%20A%20modern%20and%20sleek%20logo%20for%20'NEXTIL'.%20The%20logo%20should%20feature%20a%20futuristic%20and%20minimalistic%20design%2C%20incorporating%20elements%20that%20symbolize%20technology%2C%20.webp" alt="프로젝트 로고" width="200"/>

NEXTIL은 사용자가 TIL(오늘 배운 것)을 작성하고 관리하는 웹 애플리케이션 API입니다. 사용자가 작성하지 않은 TIL 주제를 추천하는 시스템을 제공합니다. 이 프로젝트는 Open AI를 이용하여 사용자가 작성한 TIL의 URL의 키워드를 추출하고 , 해당 분야에 맞는 키워드를 기반으로 TIL 주제를 추천하는 기능을 제공합니다.

## 기능

- **TIL 주제 추천**: 사용자가 관심 있는 분야에 대해 TIL을 작성하지 않았을 경우, 이전에 저장된 키워드를 기반으로 새로운 TIL 주제를 추천합니다.
- **TIL 크롤링**: 사용자가 제출한 TIL URL을 크롤링하여 핵심 키워드를 자동으로 추출하고 DB에 저장합니다.
- **소셜 로그인**: 카카오 소셜 로그인을 통해 사용자는 간편하게 로그인할 수 있습니다.


## 기술 스택

- **BACKEND**: KOTLIN, SPRING BOOT
- **DATABASE**: MYSQL
- **AUTH**: KAKAO API
- **AI**: OpenAI API (TIL 주제 추천)

## 시연 영상
프로젝트의 시연 영상은 아래 링크에서 확인하실 수 있습니다:

[시연 영상 보기](https://www.youtube.com/업로드예정)


### 사용한 버전

- **JDK**: 17
- **SPRING BOOT**: 3.4.2
- **OPEN AI VERSION**: 1.0.0-M6
- **GPT MODEL**: gpt-4o-mini-2024-07-18
