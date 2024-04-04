package fr.kevin.llps.subscriber.bc.service;

import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import fr.kevin.llps.subscriber.bc.exception.SubscriberAlreadyExistsException;
import fr.kevin.llps.subscriber.bc.exception.SubscriberEntityNotFoundException;
import fr.kevin.llps.subscriber.bc.repository.SubscriberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static fr.kevin.llps.subscriber.bc.sample.SubscriberEntitySample.oneSubscriberEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberService subscriberService;

    @Captor
    private ArgumentCaptor<SubscriberEntity> subscriberEntityArgumentCaptor;

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

    @Test
    void givenSubscriber_whenCallSubscriberExists_shouldReturnTrue() {
        SubscriberEntity subscriberEntity = oneSubscriberEntity();

        when(subscriberRepository.findByEmailOrPhone(subscriberEntity.getEmail(), subscriberEntity.getPhone())).thenReturn(Optional.of(subscriberEntity));

        boolean subscriberExists = subscriberService.subscriberExists(subscriberEntity);

        assertThat(subscriberExists).isTrue();

        verify(subscriberRepository).findByEmailOrPhone(subscriberEntity.getEmail(), subscriberEntity.getPhone());
        verifyNoMoreInteractions(subscriberRepository);
    }

    @Test
    void shouldGetSubscriberEntity() {
        SubscriberEntity expectedSubscriberEntity = oneSubscriberEntity();

        UUID uuid = UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003");

        when(subscriberRepository.findById(uuid)).thenReturn(Optional.of(expectedSubscriberEntity));

        Optional<SubscriberEntity> subscriberEntity = subscriberService.getSubscriberEntity(uuid);

        assertThat(subscriberEntity).contains(expectedSubscriberEntity);

        verify(subscriberRepository).findById(uuid);
        verifyNoMoreInteractions(subscriberRepository);
    }

    @Test
    void shouldDisable() {
        SubscriberEntity subscriberEntity = oneSubscriberEntity();

        when(subscriberRepository.save(any())).thenReturn(subscriberEntity);

        subscriberService.disable(subscriberEntity);

        verify(subscriberRepository).save(subscriberEntityArgumentCaptor.capture());

        SubscriberEntity captorValue = subscriberEntityArgumentCaptor.getValue();
        assertThat(captorValue.isEnabled()).isFalse();

        verifyNoMoreInteractions(subscriberRepository);
    }

    @Test
    void shouldPatch() {
        UUID uuid = UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003");
        SubscriberEntity expectedSubscriberEntity = oneSubscriberEntity();
        SubscriberEntity subscriberToPatch = mock(SubscriberEntity.class);

        when(subscriberRepository.findById(uuid)).thenReturn(Optional.of(subscriberToPatch));
        when(subscriberRepository.findByEmailOrPhone(expectedSubscriberEntity.getEmail(), expectedSubscriberEntity.getPhone())).thenReturn(Optional.empty());
        doNothing().when(subscriberToPatch).update(expectedSubscriberEntity);
        when(subscriberRepository.save(subscriberToPatch)).thenReturn(expectedSubscriberEntity);

        SubscriberEntity subscriberEntity = subscriberService.patch(uuid, expectedSubscriberEntity);

        assertThat(subscriberEntity).isEqualTo(expectedSubscriberEntity);

        verify(subscriberRepository).findById(uuid);
        verify(subscriberRepository).findByEmailOrPhone(expectedSubscriberEntity.getEmail(), expectedSubscriberEntity.getPhone());
        verify(subscriberToPatch).update(expectedSubscriberEntity);
        verify(subscriberRepository).save(subscriberToPatch);
        verifyNoMoreInteractions(subscriberRepository, subscriberToPatch);
    }

    @Test
    void givenNoExistingSubscriberId_whenCallPatch_shouldThrowSubscriberEntityNotFoundException() {
        UUID uuid = UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003");
        SubscriberEntity subscriberEntity = oneSubscriberEntity();

        when(subscriberRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThatExceptionOfType(SubscriberEntityNotFoundException.class)
                .isThrownBy(() -> subscriberService.patch(uuid, subscriberEntity))
                .satisfies(e -> assertThat(e.getMessage()).isEqualTo("Subscriber not found"));

        verify(subscriberRepository).findById(uuid);
        verifyNoMoreInteractions(subscriberRepository);
    }

    @Test
    void givenAlreadyExistingEmailOrPhoneToPatch_whenCallPatch_shouldThrowSubscriberAlreadyExistsException() {
        UUID uuid = UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003");
        SubscriberEntity subscriberEntity = oneSubscriberEntity();

        when(subscriberRepository.findById(uuid)).thenReturn(Optional.of(subscriberEntity));
        when(subscriberRepository.findByEmailOrPhone(subscriberEntity.getEmail(), subscriberEntity.getPhone())).thenReturn(Optional.of(subscriberEntity));

        assertThatExceptionOfType(SubscriberAlreadyExistsException.class)
                .isThrownBy(() -> subscriberService.patch(uuid, subscriberEntity))
                .satisfies(e -> assertThat(e.getMessage()).isEqualTo("Impossible to patch already existing email or phone from subscriber"));

        verify(subscriberRepository).findById(uuid);
        verify(subscriberRepository).findByEmailOrPhone(subscriberEntity.getEmail(), subscriberEntity.getPhone());
        verifyNoMoreInteractions(subscriberRepository);
    }

}
