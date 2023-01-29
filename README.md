# Naver-Movie-App
- minSdk: 26
- targetSdk: 33
- compileSdk: 33

# 구조
## 1. 검색 페이지
![KakaoTalk_20230129_212653322_01](https://user-images.githubusercontent.com/92709137/215326085-2aa7c20f-2343-4841-bbe6-33a9fb86b09b.jpg)

- 페이지 당 아이템 로드 개수: 15
- 마지막 아이템에서 추가 로드 요청 시: Toast 메세지 발생 (마지막 페이지입니다.)
- 검색 시 아이템이 없을 시: Layout 교체 및 TextView 메세지 발생 (검색 결과가 없습니다.)
- 링크 클릭 시: 해당 Url로 브라우저 실행

## 2. 최근 검색 목록 페이지
![KakaoTalk_20230129_212653322](https://user-images.githubusercontent.com/92709137/215326084-81043ff3-e387-4df2-9ee8-8c5d9a94de00.jpg)

- Room 최대 저장 개수: 10
- 해당 검색어 클릭 시: 검색 페이지로 이동 후 해당 검색어로 검색 및 해당 검색어가 최상단으로 이동, 중복 시 제거 후 이동
- 최근 검색 아이템이 없을 시: Layout 교체 및 TextView 메세지 발생 (최근 검색 결과가 없습니다.)
