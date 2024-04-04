package fr.kevin.llps.subscriber.bc.api.rest.mapper;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberPatchRequestDto;
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

    public SubscriberEntity map(SubscriberPatchRequestDto subscriberPatchRequestDto) {
        return new SubscriberEntity(subscriberPatchRequestDto.firstname(),
                subscriberPatchRequestDto.lastname(),
                subscriberPatchRequestDto.email(),
                subscriberPatchRequestDto.phone(),
                subscriberPatchRequestDto.enabled());
    }

}
