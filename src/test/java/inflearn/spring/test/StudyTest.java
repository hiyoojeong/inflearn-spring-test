package inflearn.spring.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

  @Test
  @DisplayName("스터디 만들기 ~~")
  @EnabledOnOs({OS.MAC, OS.LINUX})
  void create1() {
    String test_env = System.getenv("TEST_ENV");
    System.out.println(test_env);
    assumeTrue("LOCAL".equalsIgnoreCase(test_env));

    Study actual = new Study(10);
    assertThat(actual.getLimit()).isGreaterThan(0);
  }

  @Test
  @DisplayName("스터디 만들기 !!")
  @DisabledOnOs(OS.MAC)
  void create2() {
    System.out.println("second create");
  }

  @Test
  @DisplayName("스터디 만들기 ㅠㅠ")
  @Disabled
  void create3() {
    System.out.println("third create");
  }

  // 모든 테스트를 실행하기 이전에 딱 한번만 호출된다.
  @BeforeAll
  static void beforeAll() {
    System.out.println("Before All");
  }

  // 모든 테스트를 실행한 이후에 딱 한번만 호출된다.
  @AfterAll
  static void afterAll() {
    System.out.println("After All");
  }

  // 각 테스트를 실행하기 이전에 한번 호출된다.
  @BeforeEach
  void beforeEach() {
    System.out.println("Before Each");
  }

  // 각 테스트를 실행한 이후에 딱 한번 호출된다.
  @AfterEach
  void afterEach() {
    System.out.println("After Each");
  }
}