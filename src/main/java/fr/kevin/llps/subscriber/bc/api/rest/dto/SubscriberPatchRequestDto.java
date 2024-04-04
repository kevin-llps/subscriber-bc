package fr.kevin.llps.subscriber.bc.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SubscriberPatchRequestDto(@Size(max = 100, message = "Field 'firstname' must not exceed 100 characters")
                                        @JsonProperty("firstname") String firstname,

                                        @Size(max = 200, message = "Field 'lastname' must not exceed 200 characters")
                                        @JsonProperty("lastname") String lastname,

                                        @Email(regexp = ".+[@].+[\\.].+", message = "Field 'email' must be a valid email")
                                        @JsonProperty("email") String email,

                                        @Size(max = 10, message = "Field 'phone' must have 10 digits")
                                        @Digits(integer = 10, message = "Field 'phone' must have 10 digits", fraction = 0)
                                        @JsonProperty("phone") String phone,
                                        @JsonProperty("enabled") boolean enabled) {
}
