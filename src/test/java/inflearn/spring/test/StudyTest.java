package inflearn.spring.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

  int value = 1;

  @RegisterExtension
  static FindSlowExtension findSlowExtension = new FindSlowExtension(1000L);

  @Order(2)
  @FastTest
  void create_new_study() {
    System.out.println(this);
    System.out.println(value++);
    Study actual = new Study(10);
    assertThat(actual.getLimit()).isGreaterThan(0);
  }

  @Order(1)
  @Test
  @Disabled
  void create_new_study_again() throws InterruptedException {
    Thread.sleep(1500L);
    System.out.println(this);
    System.out.println(value++);
    System.out.println("second create");
  }

  @Order(3)
  @DisplayName("스터디 만들기")
  @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
  void repeatTest(RepetitionInfo repetitionInfo) {
    System.out.println("test" + repetitionInfo.getCurrentRepetition() + "/"
        + repetitionInfo.getTotalRepetitions());
  }

  @Order(4)
  @DisplayName("스터디 만들기")
  @ParameterizedTest(name = "{index} {displayName} message={0}")
  @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
  void parameterizedTest(@AggregateWith(StudyAggregator.class) Study study) {
    System.out.println(study);
  }

  static class StudyAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
        throws ArgumentsAggregationException {
      return new Study(accessor.getInteger(0), accessor.getString(1));
    }
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
  void beforeAll() {
    System.out.println("Before All");
  }

  // 모든 테스트를 실행한 이후에 딱 한번만 호출된다.
  @AfterAll
  void afterAll() {
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