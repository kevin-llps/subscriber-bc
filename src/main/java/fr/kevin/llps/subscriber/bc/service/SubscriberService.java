package fr.kevin.llps.subscriber.bc.service;

import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import fr.kevin.llps.subscriber.bc.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
