package fr.kevin.llps.subscriber.bc.api.rest.mapper;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberResponseDto;
import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static fr.kevin.llps.subscriber.bc.sample.SubscriberDtoSample.oneSubscriberResponseDto;
import static fr.kevin.llps.subscriber.bc.sample.SubscriberEntitySample.oneSubscriberEntity;
import static org.assertj.core.api.Assertions.assertThat;

class SubscriberDtoMapperTest {

    @Test
    void shouldMap() {
        SubscriberEntity subscriberEntity = oneSubscriberEntity();
        subscriberEntity.setId(UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003"));

        SubscriberResponseDto expectedSubscriberResponseDto = oneSubscriberResponseDto();

        SubscriberDtoMapper subscriberDtoMapper = new SubscriberDtoMapper();

        SubscriberResponseDto subscriberResponseDto = subscriberDtoMapper.map(subscriberEntity);

        assertThat(subscriberResponseDto).isEqualTo(expectedSubscriberResponseDto);
    }

}
