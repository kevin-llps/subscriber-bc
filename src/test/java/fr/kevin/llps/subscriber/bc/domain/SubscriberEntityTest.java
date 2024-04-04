package fr.kevin.llps.subscriber.bc.domain;

import org.junit.jupiter.api.Test;

import static fr.kevin.llps.subscriber.bc.sample.SubscriberEntitySample.oneSubscriberEntity;
import static org.assertj.core.api.Assertions.assertThat;

class SubscriberEntityTest {

    @Test
    void shouldUpdate() {
        SubscriberEntity subscriberEntity = oneSubscriberEntity();

        SubscriberEntity newSubscriber = new SubscriberEntity("kevin", "llps", "kevin.llps@gmail.com", "0678909875", false);

        subscriberEntity.update(newSubscriber);

        assertThat(subscriberEntity).isEqualTo(newSubscriber);
    }

}
