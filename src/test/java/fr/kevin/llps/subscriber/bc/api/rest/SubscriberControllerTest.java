package fr.kevin.llps.subscriber.bc.api.rest;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberPatchRequestDto;
import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberRequestDto;
import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberResponseDto;
import fr.kevin.llps.subscriber.bc.api.rest.mapper.SubscriberDtoMapper;
import fr.kevin.llps.subscriber.bc.api.rest.mapper.SubscriberEntityMapper;
import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import fr.kevin.llps.subscriber.bc.service.SubscriberService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static fr.kevin.llps.subscriber.bc.sample.SubscriberDtoSample.*;
import static fr.kevin.llps.subscriber.bc.sample.SubscriberEntitySample.oneSubscriberEntity;
import static fr.kevin.llps.subscriber.bc.utils.TestUtils.readResource;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriberController.class)
class SubscriberControllerTest {

    @Value("classpath:/json/subscriber-request-ok.json")
    private Resource subscriberRequest;

    @Value("classpath:/json/subscriber-response-ok.json")
    private Resource subscriberResponse;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriberService subscriberService;

    @MockBean
    private SubscriberEntityMapper subscriberEntityMapper;

    @MockBean
    private SubscriberDtoMapper subscriberDtoMapper;

    @Nested
    class Create {

        @Test
        void shouldCreate() throws Exception {
            SubscriberRequestDto subscriberRequestDto = oneSubscriberRequestDto();
            SubscriberResponseDto subscriberResponseDto = oneSubscriberResponseDto();
            SubscriberEntity subscriberEntity = oneSubscriberEntity();
            SubscriberEntity savedSubscriber = oneSubscriberEntity();
            savedSubscriber.setId(UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003"));

            when(subscriberEntityMapper.map(subscriberRequestDto)).thenReturn(subscriberEntity);
            when(subscriberService.subscriberExists(subscriberEntity)).thenReturn(false);
            when(subscriberService.save(subscriberEntity)).thenReturn(savedSubscriber);
            when(subscriberDtoMapper.map(savedSubscriber)).thenReturn(subscriberResponseDto);

            mockMvc.perform(post("/subscribers")
                            .content(readResource(subscriberRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(readResource(subscriberResponse), true));

            verify(subscriberEntityMapper).map(subscriberRequestDto);
            verify(subscriberService).subscriberExists(subscriberEntity);
            verify(subscriberService).save(subscriberEntity);
            verify(subscriberDtoMapper).map(savedSubscriber);
            verifyNoMoreInteractions(subscriberEntityMapper, subscriberService, subscriberDtoMapper);
        }

        @Test
        void givenAlreadyExistingSubscriber_whenCallCreate_shouldReturnBadRequestAsStatusCode() throws Exception {
            SubscriberRequestDto subscriberRequestDto = oneSubscriberRequestDto();
            SubscriberEntity subscriberEntity = oneSubscriberEntity();

            when(subscriberEntityMapper.map(subscriberRequestDto)).thenReturn(subscriberEntity);
            when(subscriberService.subscriberExists(subscriberEntity)).thenReturn(true);

            mockMvc.perform(post("/subscribers")
                            .content(readResource(subscriberRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message", is("Subscriber already exist (email : jean.dupont@gmail.com, phone : 0654323456)")));

            verify(subscriberEntityMapper).map(subscriberRequestDto);
            verify(subscriberService).subscriberExists(subscriberEntity);
            verifyNoMoreInteractions(subscriberEntityMapper, subscriberService);
        }

        @ParameterizedTest
        @MethodSource("provideErrorMessageByLocation")
        void givenInvalidBodyRequest_whenCallCreate_shouldReturnExpectedMessage(String location, String errorMessage) throws Exception {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);

            mockMvc.perform(post("/subscribers")
                            .content(readResource(resource))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message", is(errorMessage)));

            verifyNoInteractions(subscriberEntityMapper, subscriberService, subscriberDtoMapper);
        }

        static Stream<Arguments> provideErrorMessageByLocation() {
            return Stream.of(
                    Arguments.of("classpath:/json/subscriber-blank-firstname-request.json", "Field 'firstname' must be non-blank"),
                    Arguments.of("classpath:/json/subscriber-exceeded-chars-firstname-request.json", "Field 'firstname' must not exceed 100 characters"),
                    Arguments.of("classpath:/json/subscriber-blank-lastname-request.json", "Field 'lastname' must be non-blank"),
                    Arguments.of("classpath:/json/subscriber-exceeded-chars-lastname-request.json", "Field 'lastname' must not exceed 200 characters"),
                    Arguments.of("classpath:/json/subscriber-blank-email-request.json", "Field 'email' must be a valid email"),
                    Arguments.of("classpath:/json/subscriber-exceeded-chars-email-request.json", "Field 'email' must be a valid email"),
                    Arguments.of("classpath:/json/subscriber-invalid-email-request.json", "Field 'email' must be a valid email"),
                    Arguments.of("classpath:/json/subscriber-blank-phone-request.json", "Field 'phone' must have 10 digits"),
                    Arguments.of("classpath:/json/subscriber-exceeded-chars-phone-request.json", "Field 'phone' must have 10 digits"),
                    Arguments.of("classpath:/json/subscriber-less-than-required-chars-phone-request.json", "Field 'phone' must have 10 digits"),
                    Arguments.of("classpath:/json/subscriber-no-digits-phone-request.json", "Field 'phone' must have 10 digits")
            );
        }

    }

    @Nested
    class GetById {

        @Test
        void shouldGetById() throws Exception {
            String id = "dde8bfa2-4922-11ec-81d3-0242ac130003";
            UUID uuid = UUID.fromString(id);

            SubscriberEntity subscriberEntity = oneSubscriberEntity();
            SubscriberResponseDto subscriberResponseDto = oneSubscriberResponseDto();

            when(subscriberService.getSubscriberEntity(uuid)).thenReturn(Optional.of(subscriberEntity));
            when(subscriberDtoMapper.map(subscriberEntity)).thenReturn(subscriberResponseDto);

            mockMvc.perform(get("/subscribers/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(readResource(subscriberResponse), true));

            verify(subscriberService).getSubscriberEntity(uuid);
            verify(subscriberDtoMapper).map(subscriberEntity);
            verifyNoMoreInteractions(subscriberService, subscriberDtoMapper);
        }

        @Test
        void givenNoExistingSubscriberId_shouldReturnNotFoundAsStatusCode() throws Exception {
            String id = "dde8bfa2-4922-11ec-81d3-0242ac130003";
            UUID uuid = UUID.fromString(id);

            when(subscriberService.getSubscriberEntity(uuid)).thenReturn(Optional.empty());

            mockMvc.perform(get("/subscribers/{id}", id))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message", is("Subscriber not found")));

            verify(subscriberService).getSubscriberEntity(uuid);
            verifyNoMoreInteractions(subscriberService, subscriberDtoMapper);
        }

    }

    @Nested
    class Disable {

        @Test
        void shouldDisable() throws Exception {
            String id = "dde8bfa2-4922-11ec-81d3-0242ac130003";
            UUID uuid = UUID.fromString(id);

            SubscriberEntity subscriberEntity = oneSubscriberEntity();

            when(subscriberService.getSubscriberEntity(uuid)).thenReturn(Optional.of(subscriberEntity));
            doNothing().when(subscriberService).disable(subscriberEntity);

            mockMvc.perform(post("/subscribers/{id}/disable", id))
                    .andExpect(status().isNoContent());

            verify(subscriberService).getSubscriberEntity(uuid);
            verify(subscriberService).disable(subscriberEntity);
            verifyNoMoreInteractions(subscriberService);
        }

        @Test
        void givenNoExistingSubscriberId_shouldReturnNotFoundAsStatusCode() throws Exception {
            String id = "dde8bfa2-4922-11ec-81d3-0242ac130003";
            UUID uuid = UUID.fromString(id);

            when(subscriberService.getSubscriberEntity(uuid)).thenReturn(Optional.empty());

            mockMvc.perform(post("/subscribers/{id}/disable", id))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message", is("Subscriber not found")));

            verify(subscriberService).getSubscriberEntity(uuid);
            verifyNoMoreInteractions(subscriberService);
        }

    }

    @Nested
    class Patch {

        @Test
        void shouldPatch() throws Exception {
            String id = "dde8bfa2-4922-11ec-81d3-0242ac130003";
            UUID uuid = UUID.fromString(id);

            SubscriberPatchRequestDto subscriberPatchRequestDto = oneSubscriberPatchRequestDto();
            SubscriberResponseDto subscriberResponseDto = oneSubscriberResponseDto();
            SubscriberEntity subscriberEntity = oneSubscriberEntity();
            SubscriberEntity savedSubscriber = oneSubscriberEntity();

            when(subscriberEntityMapper.map(subscriberPatchRequestDto)).thenReturn(subscriberEntity);
            when(subscriberService.patch(uuid, subscriberEntity)).thenReturn(savedSubscriber);
            when(subscriberDtoMapper.map(savedSubscriber)).thenReturn(subscriberResponseDto);

            mockMvc.perform(patch("/subscribers/{id}", id)
                            .content(readResource(subscriberRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(readResource(subscriberResponse), true));

            verify(subscriberEntityMapper).map(subscriberPatchRequestDto);
            verify(subscriberService).patch(uuid, subscriberEntity);
            verify(subscriberDtoMapper).map(savedSubscriber);
            verifyNoMoreInteractions(subscriberEntityMapper, subscriberService, subscriberDtoMapper);
        }

        @ParameterizedTest
        @MethodSource("provideErrorMessageByLocation")
        void givenInvalidBodyRequest_whenCallPatch_shouldReturnExpectedMessage(String location, String errorMessage) throws Exception {
            String id = "dde8bfa2-4922-11ec-81d3-0242ac130003";

            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);

            mockMvc.perform(patch("/subscribers/{id}", id)
                            .content(readResource(resource))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message", is(errorMessage)));

            verifyNoInteractions(subscriberEntityMapper, subscriberService, subscriberDtoMapper);
        }

        static Stream<Arguments> provideErrorMessageByLocation() {
            return Stream.of(
                    Arguments.of("classpath:/json/subscriber-exceeded-chars-firstname-request.json", "Field 'firstname' must not exceed 100 characters"),
                    Arguments.of("classpath:/json/subscriber-exceeded-chars-lastname-request.json", "Field 'lastname' must not exceed 200 characters"),
                    Arguments.of("classpath:/json/subscriber-exceeded-chars-email-request.json", "Field 'email' must be a valid email"),
                    Arguments.of("classpath:/json/subscriber-invalid-email-request.json", "Field 'email' must be a valid email"),
                    Arguments.of("classpath:/json/subscriber-exceeded-chars-phone-request.json", "Field 'phone' must have 10 digits"),
                    Arguments.of("classpath:/json/subscriber-less-than-required-chars-phone-request.json", "Field 'phone' must have 10 digits"),
                    Arguments.of("classpath:/json/subscriber-no-digits-phone-request.json", "Field 'phone' must have 10 digits")
            );
        }

    }

}
