package fr.kevin.llps.subscriber.bc.service;

import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import fr.kevin.llps.subscriber.bc.exception.SubscriberAlreadyExistsException;
import fr.kevin.llps.subscriber.bc.exception.SubscriberEntityNotFoundException;
import fr.kevin.llps.subscriber.bc.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static fr.kevin.llps.subscriber.bc.utils.Constants.SUBSCRIBER_NOT_FOUND_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberEntity save(SubscriberEntity subscriberEntity) {
        return subscriberRepository.save(subscriberEntity);
    }

    public boolean subscriberExists(SubscriberEntity subscriberEntity) {
        return subscriberRepository.findByEmailOrPhone(subscriberEntity.getEmail(), subscriberEntity.getPhone()).isPresent();
    }

    public Optional<SubscriberEntity> getSubscriberEntity(UUID id) {
        return subscriberRepository.findById(id);
    }

    public void disable(SubscriberEntity subscriberEntity) {
        subscriberEntity.setEnabled(false);

        subscriberRepository.save(subscriberEntity);
    }

    public SubscriberEntity patch(UUID uuid, SubscriberEntity newSubscriber) {
        SubscriberEntity subscriberToPatch = subscriberRepository.findById(uuid)
                .orElseThrow(() -> new SubscriberEntityNotFoundException(SUBSCRIBER_NOT_FOUND_ERROR_MESSAGE));

        if (subscriberRepository.findByEmailOrPhone(newSubscriber.getEmail(), newSubscriber.getPhone()).isPresent()) {
            throw new SubscriberAlreadyExistsException("Impossible to patch already existing email or phone from subscriber");
        }

        subscriberToPatch.update(newSubscriber);

        return subscriberRepository.save(subscriberToPatch);
    }
}
