package fr.kevin.llps.subscriber.bc.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SubscriberRequestDto(@JsonProperty("firstname") String firstname,
                                   @JsonProperty("lastname") String lastname,
                                   @JsonProperty("email") String email,
                                   @JsonProperty("phone") String phone,
                                   @JsonProperty("enabled") boolean enabled) {
}
