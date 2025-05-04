## Java/OOP 과제
### [과제 개요]
- **과제 명** : Java/OOP 과제 - 카드게임을 객체지향적으로 설계하고 코드로 작성하시오
- **상세 내용 :** [과제 RFP 노션 링크](https://www.notion.so/Java-OOP-1b69047c353d8183b2d4fbb4db79cae2?pvs=4)
- **수행 및 결과물 제출 기한** : 4/28 (월) 15:00 ~ 5/6 (화) 23:59

### 🎮 Java Poker Game
- Java 객체지향 프로그래밍 기반의 텍스트 기반 포커 카드 게임입니다.  
- 플레이어는 닉네임을 입력하고 게임에 참가하여 딜러로부터 5장의 카드를 받아 승부를 겨루게 됩니다.

### 📁 프로젝트 구조
CardGame.pocker/
├── controller/ # 게임 흐름 제어
│ └── GameController : 게임의 시작부터 종료까지 전체 흐름 담당
├── model/ # 핵심 도메인 모델
│ ├── card/ : 카드 관련 클래스
│ │ ├── Card : 카드 한 장의 정보 (숫자 + 무늬)
│ │ ├── CardNumber : 카드 숫자(enum)
│ │ ├── CardRank : 족보(enum) 및 점수 정의
│ │ ├── CardShape : 카드 무늬(enum)
│ │ └── Deck : 덱(Deck) 객체, 52장 생성 및 제공
│ ├── dealer/
│ │ └── Dealer : 카드 셔플, 분배, 족보 평가, 승자 판별
│ ├── game/
│ │ └── Game : 전체 게임 상태 관리 (플레이어 목록 등)
│ └── player/
│ └── Player : 플레이어 정보 (닉네임, 보유 금액, 승패 수, 카드)
├── view/ # 입출력 담당 뷰
│ ├── IntroView : 초기 안내 메시지 출력
│ ├── InputView : 사용자 입력 처리
│ └── OutputView : 결과 출력
└── Application.java : 메인 실행 진입점


### 🚀 실행 방법
#### 1. Gradle 빌드 (IntelliJ 기준)
- 의존성을 관리하기 위해 `build.gradle`파일을 추가했습니다. 
- `Gradle` 탭에서 `Tasks > build > build` 실행
#### 2. 실행
- `Application.java`를 실행하면 콘솔 게임이 시작됩니다.



### 🧩 ERD
+---------------------+
|       Game          |
+---------------------+
| - players: List<Player>  
| - winner: Player
| - dealer: Dealer
+---------------------+
| +startGame()        
| +endGame()          
| +addPlayer(nickname)
+---------------------+
|
| 1
|------------------+
|                  |
v                  v
+-------------+     +-------------+
|   Dealer    |     |   Player    |
+-------------+     +-------------+
| - deckCards             | - nickname
| - players               | - gameMoney
|                         | - winCount
+-------------+           | - loseCount
| +shuffle()              | - playerCards: List<Card>
| +giveCardsToPlayers()   +-------------+
| +getWinner()            | +addCard()  
| +getCardRank()          | +removeAllPlayerCards()
+-------------+           +-------------+
|
| uses
v
+-------------+
|   Deck      |
+-------------+
| - cards: List<Card>
+-------------+
| +getCards()       
+-------------+
|
| contains
v
+-------------+
|   Card      |
+-------------+
| - number: CardNumber
| - shape: CardShape
+-------------+


+----------------+     +----------------+
|  CardNumber    |     |  CardShape     |
+----------------+     +----------------+
| (Enum)         |     | (Enum)         |
+----------------+     +----------------+

+----------------+
|  CardRank      |
+----------------+
| (Enum + score) |
+----------------+



### ✅ 진행사항
- MVC 패턴 적용
  - Controller가 Model과 View를 연결하고, 전체 게임의 흐름을 관리
- 동시성 문제 해결(현재는 예방 수준)
  - Dealer 클래스에서 멀티 쓰레딩 시 발생할 수 있는 동시성 문제를 고려해, 공유 상태(Map, Deck)를 리팩토링
  - 하지만 매번 Map을 새로 생성하는 구조가 비효율적이라고 생각해 병렬 처리의 효율성에 대해서 학습할 예정
- 멀티 쓰레딩, 병렬 처리 고려
  - 현재는 동시성 문제 예방 수준, 추후 멀티 쓰레딩과 병렬 처리에 대해서 학습하고 적용해보면 좋을듯
- 단위 테스트 진행 예정
  - Junit 기반의 테스트를 도입하고자 하나, 현재 구조에 추상화가 부족하여 테스트 작성이 어려움