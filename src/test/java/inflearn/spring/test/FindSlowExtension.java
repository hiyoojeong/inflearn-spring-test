package inflearn.spring.test;

import java.lang.reflect.Method;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

public class FindSlowExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

  private long THRESHOLD;

  public FindSlowExtension(long THRESHOLD) {
    this.THRESHOLD = THRESHOLD;
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    ExtensionContext.Store store = getStore(context);
    store.put("START_TIME", System.currentTimeMillis());
  }

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {
    Method requiredTestMethod = context.getRequiredTestMethod();
    SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);

    String testMethodName = requiredTestMethod.getName();
    ExtensionContext.Store store = getStore(context);
    long startTime = store.remove("START_TIME", long.class);
    long duration = System.currentTimeMillis() - startTime;
    if(duration > THRESHOLD && annotation == null) {
      System.out.printf("Please consider mark method [%s] with @SlowTest\n", testMethodName);
    }
  }

  private static Store getStore(ExtensionContext context) {
    String testClassName = context.getRequiredTestClass().getName();
    String testMethodName = context.getRequiredTestMethod().getName();
    return context.getStore(Namespace.create(testClassName, testMethodName));
  }
}
