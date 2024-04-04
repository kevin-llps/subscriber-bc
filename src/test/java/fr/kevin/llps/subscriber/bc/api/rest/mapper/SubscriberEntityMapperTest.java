package fr.kevin.llps.subscriber.bc.api.rest.mapper;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberPatchRequestDto;
import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberRequestDto;
import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import org.junit.jupiter.api.Test;

import static fr.kevin.llps.subscriber.bc.sample.SubscriberDtoSample.oneSubscriberPatchRequestDto;
import static fr.kevin.llps.subscriber.bc.sample.SubscriberDtoSample.oneSubscriberRequestDto;
import static fr.kevin.llps.subscriber.bc.sample.SubscriberEntitySample.oneSubscriberEntity;
import static org.assertj.core.api.Assertions.assertThat;

class SubscriberEntityMapperTest {

    @Test
    void shouldMapFromRequestDto() {
        SubscriberEntity expectedSubscriberEntity = oneSubscriberEntity();
        SubscriberRequestDto subscriberRequestDto = oneSubscriberRequestDto();

        SubscriberEntityMapper subscriberEntityMapper = new SubscriberEntityMapper();

        SubscriberEntity subscriberEntity = subscriberEntityMapper.map(subscriberRequestDto);

        assertThat(subscriberEntity).isEqualTo(expectedSubscriberEntity);
    }

    @Test
    void shouldMapFromPatchRequestDto() {
        SubscriberEntity expectedSubscriberEntity = oneSubscriberEntity();
        SubscriberPatchRequestDto subscriberPatchRequestDto = oneSubscriberPatchRequestDto();

        SubscriberEntityMapper subscriberEntityMapper = new SubscriberEntityMapper();

        SubscriberEntity subscriberEntity = subscriberEntityMapper.map(subscriberPatchRequestDto);

        assertThat(subscriberEntity).isEqualTo(expectedSubscriberEntity);
    }

}
