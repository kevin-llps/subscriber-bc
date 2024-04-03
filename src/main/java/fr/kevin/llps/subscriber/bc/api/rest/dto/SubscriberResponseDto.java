package fr.kevin.llps.subscriber.bc.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SubscriberResponseDto(@JsonProperty("id") UUID id,
                                    @JsonProperty("firstname") String firstname,
                                    @JsonProperty("lastname") String lastname,
                                    @JsonProperty("email") String email,
                                    @JsonProperty("phone") String phone,
                                    @JsonProperty("enabled") boolean enabled) {
}
