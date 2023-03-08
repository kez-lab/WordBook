# WordBook
![030517033957585](https://user-images.githubusercontent.com/93872496/222953825-7a7db4c3-7e97-40d5-a137-cd72c10bc20a.jpg)


## 앱 다운 링크
[Google Play](https://play.google.com/store/apps/details?id=com.kej.wordbook)
<br><br>

## 📚사용 기술 및 라이브러리
- Clean Architecture, Hilt, StateFlow, Flow
- Room DataBase, Coroutine
<br><br>

## 📱 담당한 기능 (Android)
- Room DB를 활용하여 단어를 저장, 삭제, 수정 하는 기능 구현
- StateFlow와 ViewModel을 활용한 StatePattern을 활용하여 현재의 상태 값을 선언하는 방식의 구조 구현
- 모든 리스트를 소비(UI에서 사용)할 때 Flow를 활용하여 비동기적으로 데이터를 처리
<br><br>

## ✒ 배운 점
- `Room DB`와 `Hilt, StateFlow`를 사용하여 `Clean Architecture`기반의 앱을 구축하는 경험이 되었다.
- Flow는 비동기적으로 데이터를 처리하면서 빠른 생산자와 느린 소비자 간의 데이터 전달에서 발생할 수 있는 문제를 해결하기 위해 백프레셔(`backpressure`)를 지원 한다는 것을 배움
- Kotlin의 코루틴 플로우는 핫 스트림(`Hot Stream`)과 콜드 스트림(`Cold Stream`) 두 가지 유형 구분된다는 것을 학습
    - 차이점은 핫 스트림(`Hot Stream`)은 데이터 생성과 함께 항상 활성화되어 있다. (StateFlow)
    - 반면에 콜드 스트림(`Cold Stream`)은 데이터 생성과 함께 활성화되지 않으며, 구독자가 있을 때만 활성화된다. (Flow)
- StateFlow와 LiveData 의 차이점에 대해 학습
    - 안드로이드 플랫폼에 종속적이었던 `LiveData` 와는 달리, `StateFlow` 는 순수 kotlin 라이브러리이기 때문에 `Domain Layer` 에서 사용할 수 있다.
    - StateFlow의 경우 초기 상태를 생성자에 전달하고, 코틀린 언어의 널 안전성(null-safety) 기능을 활용하여 데이터를 처리한다.
    → 즉 NullSafe한 코드를 구축할 수 있다.
<br><br>

## 🔔 추가적인 목표
- 학습을 위해 앱을 구축하여 기능적인 부분들인 많이 미흡
→ 추가적으로 단어장 API등을 받아와서 직접 입력 뿐 아니라 단어를 자유자재로 외울 수 있는 기능 추가 예정
→ 각 품사마다, 알파벳 순서마다 볼 수 있는 단어의 정렬을 구현할 예정
- 디자인 부족 → 툴바, 바텀 네비게이션 등을 통해서 UI/UX 적으로 조금 더 나은 기능을 구현하고자 함
<br><br>
