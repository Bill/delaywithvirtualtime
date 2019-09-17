import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class DelayTest {

  @Test
  public void nondeterministicDelayTest() {
    StepVerifier
        .create(Flux.just(1, 2).delayElements(Duration.ofSeconds(2)))
        .expectSubscription()
        .then(() -> System.out.println("got subscription"))
        .expectNoEvent(Duration.ofSeconds(1))
        .then(() -> System.out.println("got event"))
        .expectNext(1)
        .then(() -> System.out.println("got 1"))
        .expectNoEvent(Duration.ofSeconds(1))
        .expectNext(2)
        .then(() -> System.out.println("got 2"))
        .verifyComplete();
  }

  @Test
  public void deterministicTest() {
    StepVerifier
        .withVirtualTime(() -> Flux.just(1, 2).delayElements(Duration.ofSeconds(2)))
        .expectSubscription()
        .then(() -> System.out.println("got subscription"))
        .expectNoEvent(Duration.ofSeconds(1))
        .then(() -> System.out.println("got event"))
        .expectNext(1)
        .then(() -> System.out.println("got 1"))
        .expectNoEvent(Duration.ofSeconds(1))
        .expectNext(2)
        .then(() -> System.out.println("got 2"))
        .verifyComplete();
  }
}
