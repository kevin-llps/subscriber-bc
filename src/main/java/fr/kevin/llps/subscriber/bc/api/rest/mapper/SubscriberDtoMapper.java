package fr.kevin.llps.subscriber.bc.api.rest.mapper;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberResponseDto;
import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import org.springframework.stereotype.Component;

@Component
public class SubscriberDtoMapper {

    public SubscriberResponseDto map(SubscriberEntity subscriberEntity) {
        return SubscriberResponseDto.builder()
                .id(subscriberEntity.getId())
                .firstname(subscriberEntity.getFirstname())
                .lastname(subscriberEntity.getLastname())
                .email(subscriberEntity.getEmail())
                .phone(subscriberEntity.getPhone())
                .enabled(subscriberEntity.isEnabled())
                .build();
    }

}
