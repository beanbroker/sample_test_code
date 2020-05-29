
# 테스트

## 테스트는 왜 필요한가?

- sonarqube 테스트 커버리지를 높이기 위해서인가?
- 사람들이 다 하니가 하는건가?
- 이런 부분을 토의방식으로 다양한 사람들과 얘기를 하면 좋을 것이다.

> 왜?? 필요해?

1. __기존에 존재하던 기능은 그대로 유지되어야 한다.__ 코드를 바꾸어서 다양한 곳에 사이드 이펙트가 나면 안되지만 그럴 가능성이 없다고는 할 수 없다. 코드의 변경으로 인해 기능이 유지가 안된다면 이 부분이 문제이다. 개발자가 직접 테스트 해도 되지만 테스트 코드를 잘 구성해두었다면 빠르게 검사가 가능하다.

2. 위의 내용과 동일하지만 코드 리펙토링을 진행할 때 필요하다. 마틴파울러 형님께서 리펙토링 테스트 관련 된 부분에 적은 내용을 보자

3. 여러 예외 케이스 검증을 할 수 있다.

4. 정말 개인적인 생각이지만 코드리뷰가 없는 테스크 코드는 필요하지 않다. 개인만족과 커버리지를 위해서 짜지말자! 혹 코드리뷰를 받지 못했더라면 졸라서라도 받자(테스트코드를 위한 코드를 짤 경우가 있어서 여러 개발자들의 의견을 통해 더 발전한 코드를 볼수 있다.)


## JUNIT의 탄생

켄트 벡님과 에림 감마님 2명이서 함께 비행기를 타고 가다가 JUNIT을 만들었다고 한다. 오픈소스 테스트 프레임워크다! 허허 참 대단 하지 않은가?
우리는 너무나도 쉽고 간단하진 않지만 짤수있는 환경을 제공받음을 감사해야 한다.

## 테스트를 해보자

어차피 너무나도 많은 블로그에서 테스트 코드의 대해 더 자세히 설명하고 있다. 구글에서 테스트 코드만 치더라도 끝없이 나온다.
직접 해보자


> https://github.com/beanbroker/sample_test_code 코드를 clone받자

게시판이 있다는 가정하에 진행을 해보자

### API테스트

> 게시판 조회 api

1. 게시판 번호로 조회
```json
{
   "boardId":1,
   "content":"긴급상황",
   "date":"2020-05-30T00:07:52.725"
}
```

2. 게시판 리스트 조회
```json
{
   "page":0,
   "boardList":[
      {
         "boardId":1,
         "content":"first board",
         "date":"2020-05-30T00:27:31.082"
      },
      {
         "boardId":2,
         "content":"second board",
         "date":"2020-05-30T00:27:31.082"
      },
      {
         "boardId":3,
         "content":"third board",
         "date":"2020-05-30T00:27:31.082"
      }
   ]
}
```

게시판 조회와 게시판 리스트를 조회하는 api를 이미 코드에 작성해두었으며 위의 json데이터를 받는지 검증하는 코드를 작성하였다.


> ApiTest.java

```java
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class ApiTest {

  @Autowired protected MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper;

  protected ResultActions setResultActionWithUrl(String url) throws Exception {

    return mockMvc.perform(get(url)).andDo(print());
  }

  @Test
  public void doNothing() {}
}

```

@SpringBootTest - 통합 테스트, 전체, 구글링 하면 더자세히나옴 



> BoardApiTest.java 실제 검증용

```java

public class BoardApiTest extends ApiTest {

  private static final String BOARD_LIST_PATH = "/boards";
  private static final String BOARD_PATH = "/boards/";

  private ResultActions boardListAction() throws Exception {
    return mockMvc.perform(get(BOARD_LIST_PATH)).andDo(print());
  }

  private ResultActions boardAction(Long boardId) throws Exception {
    return mockMvc.perform(get(BOARD_PATH + boardId)).andDo(print());
  }

  @Test
  public void getBoard() throws Exception {
    // given
    Long boardId = 1L;

    // when
    ResultActions resultActions = boardAction(boardId);

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("boardId").exists())
        .andExpect(jsonPath("content").exists())
        .andExpect(jsonPath("date").exists());
  }

  @Test
  public void getBoardList() throws Exception {
    // given
    Long boardId = 1L;

    // when
    ResultActions resultActions = boardListAction();

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("page").exists())
        .andExpect(jsonPath("boardList").isArray());
  }
}

```


.andExpect(jsonPath("page").exists())
- 해당 jsonPath에 page가 있느냐?

.andExpect(jsonPath("boardList").isArray());
- 해당 jsonPath에 있는 boardList는 어레인것인가

.andExpect(status().isOk())
- HttpStatusCode가 200인가

ResultActions 참고 -> 
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/ResultActions.html


물론 이외에도 boardId를 잘못 넘겨 존재 하지 않을 경우 httpstatusCode를 400대로 던져 테스트할수 있다. 직접해보자 여기선 하지 않는다.


이외에도 WebMvcTest, DataJpaTest, 도메인 테스트 등 다양한 테스트 방법이 존재한다. 사실... 이렇게 1편을 만든이유는.... Spock로 테스트를 짜보기 위함이다.... 그래야 2편이 기대되니.. 허허허허


참고사항 작년에 작성한 Junit4->5로 변경된 사항 (2번 보자)

https://beanbroker.github.io/2019/10/05/Java/test_after_before/