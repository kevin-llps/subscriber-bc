package fr.kevin.llps.subscriber.bc.sample;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberRequestDto;
import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriberDtoSample {

    public static SubscriberResponseDto oneSubscriberResponseDto() {
        return SubscriberResponseDto.builder()
                .id(UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003"))
                .firstname("jean")
                .lastname("dupont")
                .email("jean.dupont@gmail.com")
                .phone("0654323456")
                .enabled(true)
                .build();
    }

    public static SubscriberRequestDto oneSubscriberRequestDto() {
        return SubscriberRequestDto.builder()
                .firstname("jean")
                .lastname("dupont")
                .email("jean.dupont@gmail.com")
                .phone("0654323456")
                .enabled(true)
                .build();
    }

}
