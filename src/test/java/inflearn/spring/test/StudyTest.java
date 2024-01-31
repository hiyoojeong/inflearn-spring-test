package inflearn.spring.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

  @FastTest
  @DisplayName("스터디 만들기 fast")
  void create1() {
    Study actual = new Study(10);
    assertThat(actual.getLimit()).isGreaterThan(0);
  }

  @SlowTest
  @DisplayName("스터디 만들기 slow")
  void create2() {
    System.out.println("second create");
  }

  @DisplayName("스터디 만들기")
  @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
  void repeatTest(RepetitionInfo repetitionInfo) {
    System.out.println("test" + repetitionInfo.getCurrentRepetition() + "/"
        + repetitionInfo.getTotalRepetitions());
  }

  @DisplayName("스터디 만들기")
  @ParameterizedTest(name = "{index} {displayName} message={0}")
  @ValueSource(ints = {10, 20, 40})
  void parameterizedTest(@ConvertWith(StudyConvertor.class) Study study) {
    System.out.println(study.getLimit());
  }

  static class StudyConvertor extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType)
        throws ArgumentConversionException {
      assertEquals(Study.class, targetType, "Can only convert to Study");
      return new Study(Integer.parseInt(source.toString()));
    }
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