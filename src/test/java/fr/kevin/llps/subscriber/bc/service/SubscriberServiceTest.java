package fr.kevin.llps.subscriber.bc.service;

import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import fr.kevin.llps.subscriber.bc.repository.SubscriberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static fr.kevin.llps.subscriber.bc.sample.SubscriberEntitySample.oneSubscriberEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberService subscriberService;

    @Test
    void shouldSave() {
        SubscriberEntity subscriberEntity = oneSubscriberEntity();
        SubscriberEntity expectedSavedSubscriber = oneSubscriberEntity();
        expectedSavedSubscriber.setId(UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003"));

        when(subscriberRepository.save(subscriberEntity)).thenReturn(expectedSavedSubscriber);

        SubscriberEntity savedSubscriber = subscriberService.save(subscriberEntity);

        assertThat(savedSubscriber).isEqualTo(expectedSavedSubscriber);

        verify(subscriberRepository).save(subscriberEntity);
        verifyNoMoreInteractions(subscriberRepository);
    }

}
