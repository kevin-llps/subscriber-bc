package fr.kevin.llps.subscriber.bc.api.rest.mapper;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberRequestDto;
import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import org.springframework.stereotype.Component;

@Component
public class SubscriberEntityMapper {

    public SubscriberEntity map(SubscriberRequestDto subscriberRequestDto) {
        return new SubscriberEntity(subscriberRequestDto.firstname(),
                subscriberRequestDto.lastname(),
                subscriberRequestDto.email(),
                subscriberRequestDto.phone(),
                subscriberRequestDto.enabled());
    }

}
